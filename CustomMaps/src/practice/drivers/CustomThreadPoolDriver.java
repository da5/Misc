package practice.drivers;

import practice.product.CustomThreadPoolImpl;
import practice.runnable.RunnableImpl1;
import practice.runnable.RunnableImpl2;

/**
 * Created by arindam.das on 10/05/16.
 */
public class CustomThreadPoolDriver {
    public static void main(String[] args){
        CustomThreadPoolImpl customThreadPool = new CustomThreadPoolImpl(3);
        customThreadPool.submit("job1", new RunnableImpl1(2,"job1"));
        customThreadPool.submit("job2", new RunnableImpl1(9,"job2"));
        customThreadPool.submit("job3", new RunnableImpl2(2,3,"job3"));
        customThreadPool.submit("job4", new RunnableImpl1(2,"job4"));
        customThreadPool.submit("job5", new RunnableImpl2(20,30,"job5"));
        customThreadPool.submit("job6", new RunnableImpl1(2,"job6"));
        customThreadPool.submit("job7", new RunnableImpl2(5,6,"job7"));
        customThreadPool.submit("job8", new RunnableImpl2(45,56,"job8"));
        customThreadPool.submit("job9", new RunnableImpl1(2,"job9"));
        customThreadPool.submit("job11", new RunnableImpl1(2,"job11"));
        customThreadPool.submit("job12", new RunnableImpl2(2,1,"job12"));
        customThreadPool.submit("job13", new RunnableImpl2(100,2,"job13"));
        customThreadPool.submit("job14", new RunnableImpl2(200,2,"job14"));
        customThreadPool.submit("job15", new RunnableImpl2(301,2,"job15"));
        customThreadPool.submit("job16", new RunnableImpl2(23,2,"job16"));
        customThreadPool.submit("job17", new RunnableImpl1(2,"job17"));
        customThreadPool.submit("job18", new RunnableImpl1(2,"job18"));
        customThreadPool.shutdown();
        customThreadPool.submit("job19", new RunnableImpl2(23, 2,"job19"));
        customThreadPool.submit("job10", new RunnableImpl1(2,"job10"));
        System.out.println("Driver is exitting!");


    }
}
