package practice.runnable;

/**
 * Created by arindam.das on 10/05/16.
 */
public class RunnableImpl1 implements Runnable {
    private int n;
    private String jobId;

    public RunnableImpl1(int n, String jobId){
        this.n = n;
        this.jobId = jobId;
    }

    public void run(){
        System.out.println("Thread " + Thread.currentThread().getId() + " executing Job "+ jobId +" :: n^2 = " + n*n);
//        try {
//            Thread.sleep(1000);
//        }catch (InterruptedException e){
//            System.out.println("Exception caught while trying to sleep!");
//        }
    }
}
