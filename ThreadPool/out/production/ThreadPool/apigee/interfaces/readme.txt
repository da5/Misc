1) ThreadPoolWithJobAffinity : an interface for the thread-pool with an extra method added by me for the sake of test-cases
2) ThreadPoolWithJobAffinityImpl: implementation of the interface
    a)ThreadPoolWithJobAffinityImpl() : creates thread-pool with the default optimal(twice the number of CPU cores) number of threads
    b)ThreadPoolWithJobAffinityImpl(int size) : creates thread-pool with the specified number of threads
    c)int poolSize() : return the pool size
    d)void submit(String jobId, Runnable job) : submits a job to the pool with jobId as its identifier
    e)void shutdown() : exits all thread after completion of the jobs left in queue; also declines any further submit job requests
    f)boolean hasTasksLeft() : ckecks if any job left in queue for unit-test purpose
3)ThreadPoolWithJobAffinityDriver : class to demonstrate the working of the thread pool followed by the following 4 test cases
    a)Number of threads(executors) created in the thread-pool is exactly equal to the specified number or default optimal value and they exit after graceful shutdown
    b)Job affinity : Jobs with same jobId are executed by the same executor
    c)No jobs are permitted to submit after/during shutdown
    d)All jobs submitted before shutdown are executed before threads exit