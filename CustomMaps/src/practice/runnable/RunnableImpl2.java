package practice.runnable;

/**
 * Created by arindam.das on 10/05/16.
 */
public class RunnableImpl2 implements Runnable{
    private int n;
    private int m;
    private String jobId;

    public RunnableImpl2(int n, int m, String jobId){
        this.n = n;
        this.m = m;
        this.jobId = jobId;
    }

    public void run(){
        System.out.println("Thread " + Thread.currentThread().getId() + " executing Job "+ jobId +" :: n*m = " + n*m);
//        try {
//            Thread.sleep(1000);
//        }catch (InterruptedException e){
//            System.out.println("Exception caught while trying to sleep after execute!");
//        }
    }
}
