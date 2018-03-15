package com.example;

import com.example.model.MessageObject;
import com.google.common.annotations.VisibleForTesting;

import java.time.Clock;
import java.time.ZoneId;
import java.util.*;

public class InMemoryQueueService implements QueueService {
	/*
		Though the code has the capability to maintain visibility at message level,
		this default value is used for every message pushed.
		Unlike AWS SQS, I am not maintaining visibility at the queue level.
	 */
	final long visibility = 1000; //in milliseconds

	/*
		The thread for the purpose of periodically purging deleted messages and restoring ones with visibility timeout
	 */
	public class Disposer extends Thread{
		final long disposerPollingInterval = 500; //in milliseconds

		@Override
		public void run() {
			while(true){
				dispose();
				try {
					System.out.println("Sleeping before next polling.");
					Thread.sleep(disposerPollingInterval);
				}catch (InterruptedException e){

				}
			}
		}
	}

	/*
		A dedicated clock is used for implementing the visibility-timeout functionality; however, could have also be
		 achieved with a DelayQueue in Java but then had to depend on sleep/wait in test cases.
	 */
	private static Clock clock = Clock.systemDefaultZone();

	Map<String, Queue<MessageObject>> queueMap;
	Map<String, Set<MessageObject>> disposerMap;
	Map<String, Set<Object>> markedForDisposalMap;
	Thread disposer;

	public InMemoryQueueService() {
		queueMap = new HashMap<>();
		disposerMap = new HashMap<>();
		markedForDisposalMap = new HashMap<>();
		disposer = new Disposer();
		disposer.start();	//starting the thread and it will continue to monitor during the lifetime of the service
	}

	public void createQueue(String queue){
		synchronized (queueMap){
			if(!queueMap.containsKey(queue)){
				queueMap.put(queue, new LinkedList<>());
				disposerMap.put(queue, new HashSet<>());
				markedForDisposalMap.put(queue, new HashSet<>());
			}
		}
	}

	@Override
	public void push(Object message, String queue){
		if(!queueMap.containsKey(queue)){
			createQueue(queue);
		}
		pushSafe(new MessageObject(message, visibility), queue);
		System.out.println("Message "+ message + " sent to queue " + queue);
	}

	private void pushSafe(MessageObject messageObject, String queue){
		synchronized (queueMap.get(queue)){
			queueMap.get(queue).offer(messageObject);
		}
	}

	@Override
	public Object pull(String queue){
		Object returnObject = null;
		if(queueMap.containsKey(queue)){
			synchronized (queueMap.get(queue)){
				if(queueMap.get(queue).size()>0){
					MessageObject messageObject = queueMap.get(queue).poll();
					messageObject.setStartTime(clock.millis()+messageObject.getVisibility());
					dispose(queue, messageObject);
					System.out.println("Message "+ messageObject.getMessage() + " sent to disposer for deletion");
					returnObject = messageObject.getMessage();
				}
			}
		}
		return returnObject;
	}

	@Override
	public void delete(Object message, String queue){
		if(markedForDisposalMap.containsKey(queue)){
			synchronized (markedForDisposalMap.get(queue)){
				markedForDisposalMap.get(queue).add(message);
				System.out.println("Message " + message + " marked for deletion");
			}
		}
	}
	private void dispose(String queue, MessageObject messageObject){
		synchronized (disposerMap.get(queue)){
			disposerMap.get(queue).add(messageObject);
		}
	}

	public int size(String queue){
		return queueMap.get(queue).size();
	}

	/*
		Method to be only used by the test-class to simulate advancing the class clock
	 */
	@VisibleForTesting
	protected void addMillisecondsToClock(long millis){
		System.out.println("[Testing] Before adding millis : " + clock.millis());
		clock = Clock.fixed(clock.instant().plusMillis(millis), ZoneId.systemDefault());
		System.out.println("[Testing] After adding millis : " + clock.millis());
	}

	/*
		Method is internally called by disposer-thread which purges the ones marked-to-be-deleted and
		restoring the ones with visibility timeout
		Also, it is made available for test-class for simulating behavior
	 */
	@VisibleForTesting
	protected void dispose(){
		System.out.println("Disposing at " + clock.millis());
		for(String queue: disposerMap.keySet()){
			synchronized (disposerMap.get(queue)){
				synchronized (markedForDisposalMap.get(queue)){
					Set<MessageObject> toBeDisposed = disposerMap.get(queue);
					Set<MessageObject> expired = new HashSet<>();
					for(MessageObject messageObject: toBeDisposed){
						if(clock.millis() > messageObject.getStartTime()+messageObject.getVisibility()
								&& !markedForDisposalMap.get(queue).contains(messageObject.getMessage())){
							expired.add(messageObject);
						}
					}
					toBeDisposed.removeAll(expired);
					disposerMap.put(queue, toBeDisposed);
					markedForDisposalMap.get(queue).clear();
					for(MessageObject object: expired){
						pushSafe(object, queue);
					}
				}
			}
		}
	}
}
