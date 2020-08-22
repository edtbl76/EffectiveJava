# Item 84: Don't depend on the thread scheduler

## Thread Scheduler
- in an env w/ many runnable threads, the Thread Scheduler determines
    - which threads get executed
    - how long they get run
- OS tries to make this fair, BUT the policy can very
    - this is platform specific, so any code that relies on thread
    scheduler for CORRECTNESS and PERFORMANCE will likely be NON-PORTABLE
    
## Alternative (Best Practice)
- ensure that the average number of Runnable threads isn't significantly
greater than the number of processors. 
    - i.e. run runnable threads, until they are no longer runnable
    - NOTE: a runnable thread is one that is doing work. 
        - threads that are waiting are NOT runnable. 
        - Total # of threads will be (much?) higher than # of 
        runnable threads. 
- keep runnable threads low by
    - assigning each thread some useful work.
    - have them wait for more (waiting thread isn't runnable)
- threads shouldn't run if they aren't doing something useful.
- avoid busy-wait


### Executor Framework
- To keep no. of runnable threads low
    - size thread pools appropriate
        - tasks kept short
            - NOTE: (if tasks are TOO short, the overhead of dispatching 
            work can hurt performance)
    

### Don't Busy-Wait
- Busy-Wait is repeatedly checking a shared object waiting for state change.
    - increases load on CPU
        - limits useful work that can be performed by others 
    - negative impact on performance + portability

(See BusyWaitExample.CrappyCountDownLatch) for an example <br> 
     
### Starved Threads
- Thread starvation is when some threads aren't getting enough CPU
time relative to others.
    - impacts application performance

#### Thread.yield
- don't use this to "fix" the program
    - it might get the program to work, BUT it won't be portable
    - yield() uses OS/platform specific logic
        - what works on one platform might break another. 
    - no testable semantics
    
#### Thread priorities
- this isn't much better than Thread.yield
    - one of the "least portable features of Java"

    
    



    