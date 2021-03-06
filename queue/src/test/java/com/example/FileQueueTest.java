package com.example;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileQueueTest {
	final String queue1 = "queue1";
	final String queue2 = "queue2";
	FileQueueService fileQueueService;

	class Producer extends Thread{
		String id;
		String queue;

		public Producer(String id, String queue) {
			this.id = id;
			this.queue = queue;
		}

		void pushMessage(int msgId){
			String message = id + "_m" + msgId;
			fileQueueService.push(message, queue);
		}

		public void run(){
			for(int i = 1; i<=2; i++){
				pushMessage(i);
			}
		}
	}

	class Consumer extends Thread{
		String id;
		String queue;

		public Consumer(String id, String queue) {
			this.id = id;
			this.queue = queue;
		}

		Object pullMessage(String queue){
			Object message = fileQueueService.pull(queue);
			System.out.println("Message " + message + " pulled from "+queue);
			return message;
		}

		public void run(){
			for(int i = 1; i<=2; i++){
				Object message = pullMessage(queue);
				if(queue1.equals(queue)){ //to check the visibility timeout which would always happen in queue2
					fileQueueService.delete(message, queue);
				}
			}
		}
	}
	@Before
	public void setUp(){
		fileQueueService = new FileQueueService("/tmp/sqs/");
	}

	@Test
	public void testConcurrentProducerConsumer() throws InterruptedException{
		Producer p1 = new Producer("p1", queue1);
		Producer p2 = new Producer("p2", queue1);

		p1.start();
		p2.start();

		p1.join();
		p2.join();

		System.out.println("[Testing] Size of " + queue1 + " : " + fileQueueService.size(queue1));
		assert fileQueueService.size(queue1) == 4;

		Consumer c1 = new Consumer("c1", queue1);
		Consumer c2 = new Consumer("c2", queue1);

		c1.start();
		c2.start();

		c1.join();
		c2.join();

		fileQueueService.addMillisecondsToClock(50000);
		fileQueueService.dispose();
		System.out.println("[Testing] Size of " + queue1 + " : " + fileQueueService.size(queue1));
		assert fileQueueService.size(queue1)==0;

	}

	@Test
	public void testVisibilityTimeout() throws InterruptedException{
		Producer p1 = new Producer("p1", queue1);
		Producer p2 = new Producer("p2", queue2);
		Consumer c1 = new Consumer("c1", queue1);
		Consumer c2 = new Consumer("c2", queue2);
		p1.start();
		p2.start();

		p1.join();
		p2.join();

		c1.start();
		c2.start();

		c1.join();
		c2.join();

		fileQueueService.addMillisecondsToClock(50000);
		fileQueueService.dispose();
		System.out.println("[Testing] Size of " + queue1 + " : " + fileQueueService.size(queue1));
		System.out.println("[Testing] Size of " + queue2 + " : " + fileQueueService.size(queue2));
		assert fileQueueService.size(queue1)==0;
		assert fileQueueService.size(queue2)==2;

	}

	@After
	public void tearDown(){}

}
