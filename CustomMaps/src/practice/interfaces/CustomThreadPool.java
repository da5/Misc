package practice.interfaces;

/**
 * Created by arinda on 8/13/2015.
 */
public interface CustomThreadPool {
    int poolSize();
    void submit(String jobId, Runnable job);
    void shutdown();
}
