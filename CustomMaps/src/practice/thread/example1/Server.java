package practice.thread.example1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by arindam.das on 29/07/16.
 */
public class Server<T> extends Thread{

    BlockingQueue<ItemObject<T>> queue;
    boolean power;
    static Server instance;

    private Server(){
        queue = new LinkedBlockingQueue<>();
        power = true;
        this.start();
    }

    public synchronized static Server getInstance(){
        if(instance == null){
            instance = new Server();
        }
        return instance;
    }

    public synchronized void shutDown(){
        power = false;
        this.interrupt();
    }

    public void put(ItemObject itemObject) throws InterruptedException{
        queue.put(itemObject);
    }

    public void run() {
        try{
            while (power){
                ItemObject itemObject = queue.take();
                System.out.println("[Server] :: " + itemObject.getItem().toString());
//                Thread.sleep(5000);
                itemObject.getResultQueue().add(itemObject.getItem());
//                itemObject.getNotificationObject().notify();
            }
        }catch (InterruptedException e){
            System.out.print("Server interrupted!");
        }
        System.out.println("[Server] :: " + "shutting down....");
    }
}
