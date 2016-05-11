package practice.product;

import practice.interfaces.CustomThreadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by arindam.das on 10/05/16.
 */
public class CustomThreadPoolImpl implements CustomThreadPool {

    private volatile boolean power;
    private int size;
    private BlockingQueue<Runnable> taskQueue;
    private Executor[] executors;

    public CustomThreadPoolImpl(int size){
        this.size = size;
        taskQueue = new LinkedBlockingQueue<>();
        executors = new Executor[size];
        power = true;
        for(int i = 0; i< size; i++){
            executors[i] = new Executor();
            executors[i].start();
        }
    }

    public int poolSize(){
        return size;
    }

    public void submit(String jobId, Runnable job){
        if(!power){
            System.out.println("Job " + jobId + " rejected owing to pool shutdown!");
            return;
        }
        taskQueue.add(job);
        System.out.println("Job " + jobId + " submitted successfully!");

    }

    public void shutdown(){
        power = false;
        for(int i =0 ;i< size; i++){
            while(!taskQueue.isEmpty()){
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    System.out.println("Exception caught while trying to sleep!");
                }
            }
            executors[i].interrupt();
        }
    }

    public class Executor extends Thread{

        public Executor(){
            super();
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
}
