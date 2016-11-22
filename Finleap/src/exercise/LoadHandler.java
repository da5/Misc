package exercise;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LoadHandler extends Thread{
	
	private static final int MAX_PRICE_UPDATES = 3;
	private final Consumer consumer;

	private boolean power;
	private Map<String, PriceUpdate> priceUpdateMap;

	public LoadHandler (Consumer consumer) {
		this.power = true;
		this.consumer = consumer;
		this.priceUpdateMap = new HashMap<>();

	}

	@Override
	public void run() {
		sendToConsumer();
	}

	private void sendToConsumer(){
		List<PriceUpdate> priceUpdates = new ArrayList<>();
		try{
			while(power || priceUpdateMap.size()> 0){
				synchronized (priceUpdateMap){
					Iterator iterator = priceUpdateMap.entrySet().iterator();
					for(int count = 0; count < MAX_PRICE_UPDATES && iterator.hasNext(); count++){
						Map.Entry<String, PriceUpdate> entry = (Map.Entry<String, PriceUpdate>)iterator.next();
						priceUpdates.add(entry.getValue());
//						priceUpdateMap.remove(entry.getKey());
					}
				}
				if(priceUpdates.size()>0){
					consumer.send (priceUpdates);
					for(PriceUpdate priceUpdate: priceUpdates){
						priceUpdateMap.remove(priceUpdate.getCompanyName());
					}
					priceUpdates.clear();
				}
				Thread.sleep(1000);
			}
		}catch (InterruptedException e){
			System.out.println("Interrupted!");
		}
	}

	public synchronized void shutDown(){
		this.power = false;
	}

	public void receive(PriceUpdate priceUpdate) {
		if(power){
			synchronized (priceUpdateMap){
				priceUpdateMap.put(priceUpdate.getCompanyName(), priceUpdate);
			}
		}
	}
	
}
