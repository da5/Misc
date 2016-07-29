package practice.thread.example1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by arindam.das on 29/07/16.
 */
public class Client {
    public static void main(String[] args){
        Server<String> server = Server.getInstance();
        BlockingQueue<String> resultQueue = new LinkedBlockingQueue<>();
        Object notificationObject = new Object();
        try{
            server.put(new ItemObject<String>("Fizz", notificationObject, resultQueue));
            resultQueue.take();
            System.out.println("[Client] :: " + "Buzz");
            server.shutDown();
        }catch (InterruptedException e){
            System.out.println("Client Interrupted!");
        }


    }
}
