# Item 81: Prefer concurrency utilities to wait and notify
"given the difficulty of using 'wait' and 'notify' correctly, you should
use the higher0level concurrency utilities instead"


## Higher-level Concurrency Utilities
Provided by java.util.concurrent in 3 categories

1. Executor Framework
1. Concurrent Collections
1. Synchronizers

### Executor Framework
(Covered in Item 80)

### Concurrent Collections
- High performance concurrent impls of standard collection interfaces
    - i.e. List, Queue and Map
- manage synchronization internally (Item 79)

Since it manages Synchronization Internally
- it is impossible to exclude concurrent activity from a concurrent collection
    - locking it will only slow down the program
- you can't atomically compose method invocations on them

#### State-Dependent Modify Operations
- these are special operations that combine several primitives into a 
single atomic operation
- these are made available to concurrent collection interfaces to 
solve the challenges created by managing synchronization internally.


// Concurrent canonicalizing map atop ConcurrentMap - NOT Optimal

    EX: 
    
        
        private static final ConcurrentMap<String, String> map = 
            new ConcurrentHashMAp<>();
            
        public static String intern(String str) {
            String previousValue = map.putIfAbsent(str, str);
            return previousValue == null ? str : previousValue;
        }
        
// Concurrent canonicalizing map atop ConcurrentMap - faster!!! <br>
- ConcurrentHashMap is optimized for read (i.e. get()) operations
    - This is faster, because we opt to invoke get() first and only use putIfAbsent() if get returns null.

    
    EX:
        public static String intern(String str) {
            String result = map.get(str);
            if (result == null) {
                result = map.putIfAbsent(str, str);
                if (result == null) {
                    result = str;
                }
            }
            return result;
        }


#### Collections vs. Concurrent Collections
- Concurrent collections are very fast, and they offer excellent concurrency
    - they make synchronized collections 'largely OBSOLETE'
    - replace synchronized maps w/ concurrent maps when possible
        - DRAMATIC perf increase.
        
#### Blocking Operations
- special operations added to collection interfaces that wait/block until they can be successfully performed
    - (Example) Blocking Queue extends Queue and adds the take() method
        - take() removes + returns th ehead element from the queue, waitinf if the queue is empty. 
        
##### Producer-Consumer Queues
- Blocking Queues can be used as work queues
    - 1 or more PRODUCER THREADS enqueue work items on the queue
    - 1 or more CONSUMER THREADS dequeue and process items as they become available
- most ExecutorService impls use a BlockingQueue
    
### Synchronizers
- objects that enable threads to wait for one another allowing those threads to coordinate their actions

#### Synchronizer Types

##### CountDownLatch
- most common
- single-use barrier that allow 1 or more threads to wait for one or more OTHER threads to do something
    - constructor takes an int that is the "countdown"
    - (the no. of times that the countDown method must be invoked on the latch before all waiting threads
    are allowed to proceed)
    
(See TimingConcurrentExecExample.FrameworkForTimingConcurrentExecution)
- this is an example of using a CountDownLatch

THREAD STARVATION DEADLOCK
- executors passed in to methods that use CountDownLatches must allow for the creation of AT LEAST as many 
threads as the given concurrency level
    - (If they don't, then the test will never complete)
    
    
##### Other Types
- Semaphore
    - very common
- CyclicBarrier
- Exchanger
- Phaser
    - most powerful

## Managing 'wait'  and 'notify'
Even though we should always prefer to use high-level concurrency utilities, many legacy applications still use
wait and notify.

### wait() 

    EX: 
        // standard best practice for using wait()
        
        synchronized(object) {
            while (<condition does not hold>) {
            
                // (Releases lock and reacquires it when it wakes up)
                object.wait(); 
                
                ...// Perform action appropriate to while condition.
            }
        }
- Best Practices
    - always use "wait loop" to invoke wait() 
    - NEVER invoke it outside of the loop

#### Wait Loop
- The purpose of the loop is to test the condition before/after the wait()
    - (Before Wait)
        - testing before waiting and SKIPPING wait if condition holds
            - NECESSARY for ensuring LIVENESS.
        - if condition holds, and notify()/notifyAll() has been called before wait()
            - no guarantee that thread will EVER wake from wait
    - (After Wait)
        - testing after waiting (and waiting again) if condition doesn't hold
            - NECESSARY for ensuring SAFETY
        - if thread proceeds w/ actio nwhen the condition does NOT hold
            - it can destroy invariant guarded by the lock
            
##### If Condition Doesn't Hold, These are the reasons threads might not wake up
- a different thread has obtained the lock
    - this will change the guarded state in the time gap between:
        - the time a thread invoked notify()/notifyAll()
        - when the waiting thread woke up. 
- a different thread could have invoked notify()/notifyAll() accidentally/maliciously (when condition failed)
    - this happens when classes wait on publicly accessible objects
        - any wait() in a 'synchronized' method of a publicly accessible object is susceptible to this problem 
- notifying thread could be too lenient when waking waiting threads
    - This happens when notifying thread invokes notifyAll() even if only some of the waiting threads have
    their condition satisfied
- SPURIOUS WAKEUP
    - this is a rare occurrence when a waiting thread wakes up in the absence of a notify.
    
### notify() vs. notifyAll()
- conservative advice is to always use notifyAll()
    - guarantees the correct results, because you'll ALWAYS wake the threads that need to wake up.
        - You'll also wake OTHER threads, but this shouldn't impact CORRECTNESS of program. 
        - The "other" threads should continue waiting if they fail the check on the condition in which they are waiting.
- using notify() can be considered an optimization if:
    - all of the threads in the wait-set use the same condition, and only one thread at a time can be active if
    the condition evaluates to true. 
    
## Additional Notes
### Interval Timing
- When performing interval timing in concurrent applications:
    - prefer System.nanoTime > System.currentTimeMillis
        - more accurate
        - unaffected by adjustments to system's real-time clock