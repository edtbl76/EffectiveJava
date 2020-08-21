# Item 80: Prefer executors, tasks and streams to threads

## Executor Framework 
- flexible interface-based task execution facility that is part of java.util.concurrent


    EX:
    
        /*
            Instantiates an executor
            - creates an efficient work queue in a single line of code
        */
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        /*
            this is the code that submits a runnable for execution
        */
        executor.execute(runnable);
        
        /*
            request graceful termination of executor
            - if you don't do this, the VM may not exit
        */
        executor.shutdown();
        
### Other important features
- get() 
    - waits for a particular task to complete (Item 79)
- invokeAny(), invokeAll()
    - wait for any/all of a collection of tasks to complete. 
- awaitTermination();
    - wait for executor service itself to terminate
- ExecutorCompletionService()
    - retrieve results of tasks one by one as the complete
- ScheduledThreadPoolExecutor()
    - scheduled tasks to run at a particular time or to run periodically
- et al.
        
### Multiple Threads (Thread Pool)
If you need more than one thread to process requests from the queue, call a different static factory that
creates a Thread Pool

#### THREAD POOL
- different kind of executor service
    - for most scenarios 
        - java.util.concurrent.Executors has all of the static factories you need
    - for multiple threads or advanced scenarios 
        - ThreadPoolExecutor is necessary
        
##### Selection Thread Pool Executors

- Executors.newCachedThreadPool()
    - no configuration
    - generic operation (which is "generically" the right thing)
    - tasks aren't queued, the are handed off directly to threads for execution
        - if no threads are available a new one is created
            - keeps creating threads, even when CPU is resource bound
            - BAD FOR HEAVY LOADS
- Executors.newFixedThreadPool()
    - creates a fixed number of threads
    - queues tasks once thread count is reached
    - BETTER for heavy loads
- ThreadPoolExecutor
    - maximum control over thread count, management of threads
    - hardest to implmement
    - easiest to screw up
    - BEST for control over performance

## Don't work directly with threads

### Threads vs. Executor
- When operating directly with threads:
    - a Thread serves as both:
        - a unit of work
        - the mechanism for executing the unit of work
- When using Executor Framework
    - a Task is the key abstraction for a unit of work
        - Runnable
        - Callable
            - (a Runnable that can return a value or throw arbitrary exceptions)
    - The Executor Service is the mechanism that executes Runnables/Callables
        - it does for execution what Collections framework did for aggregation
        
## Fork-Join

### ForkJoinTask
- special kind of task executed by a ForkJoinPool
- can be split up into smaller sub-tasks

### ForkJoinPool
- special kind of executor service that executes ForkJoinTasks
- the threads that comprise a ForkJoinPool can "steal" threads from ForkJoinTasks (preferably idle ones)
to ensure that all of the threads remain busy. 

PROS:
- higher CPU utilization 
- higher throughput
- lower latency

CONS: 
- challenge to write and tune


#### Parallel Streams
- written on top of Fork Join Pools
- allow developer to take advantage of FJP performance benefits w/o much effort. 
    
 
            
    