package practice.core;

import java.util.concurrent.BlockingQueue;

/**
 * Created by arindam.das on 11/05/16.
 */
public class CustomExecutor extends Thread{

    private BlockingQueue<Runnable> taskQueue;

    public CustomExecutor(BlockingQueue taskQueue){
        super();
        this.taskQueue = taskQueue;
        System.out.println("Thread " + this.getId() + " spawned!");
    }

    public void run(){
        while (true){
            try{
                Runnable task = taskQueue.take();
                task.run();
            }catch (InterruptedException e){
                System.out.println("Thread " + this.getId() + " interrupted!");
                break;
            }
        }
    }
}
