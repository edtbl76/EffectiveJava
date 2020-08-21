# Item 78: Synchronize access to shared mutable data. 

## Synchronized
- keyword used to ensure that only a single thread can execute a method or block at one time.
- Synchronization is REQUIRED for reliable communication between threads as well as mutual exclusion
    - due to MEMORY MODEL which specifies how changes made by one thread become visible from another. 
        - Java Memory Model is a built-in specification of the language.
        
"FEATURES"
- MutualExclusion (atomicity) 
- Communication (visibility of changes of an object from the thread in which it is made to other threads that want to access it)


If you don't synchronize, the penalties are liveness/safety failures
- These are the HARDEST types of bugs to troubleshoot.

### Mutual Exclusion (Mutex)
- a way to prevent an object from being seen in an inconsistent state by one thread while
being modified by another

HOW IT WORKS
- object is created in a consistent state (Item 17)
- locked by methods that access it.
    - they observe object state and optionally cause a "State Transition" 
        - (transformation of object from one consistent state to another)


- proper use of MutEx as a form of synchronization guarantees that no method will ever observe
the object in an inconsistent state. 

### Beyond Mutex
- Mutex is only part of the story in terms of synchronization
- Other:
    - w/o sync as a whole, one thread's changes might not be VISIBLE to other threads
    - ensures that each thread entering a synchronized method/block will see the effects of all
    previous modifications that were guarded by the same lock.
    
### Atomicity of Read/Write
Java guarantees that reading/writing a variable is atomic (other than LONG or DOUBLE)
- i.e. guaranteed to return a value that was stored into that variable by some thread even if
multple threads modify the variable concurrently w/o synchronization
- this does NOT guarantee that a value written by one thread will be visible to another. 
    - IMPORTANT

### Synchronization and performance
- Do NOT ignore synchronization for the sake of performance
    
    
## Consequences of NOT Synchronizing. 
- these can be problematic EVEN IF data is atomically readable/writable

### Stopping threads
1. Thread.stop is DEPRECATED and UNSAFE. DO NOT USE IT
2. Bad Example of Stopping Threads (that might seem to work:
    - provide a boolean initialized to false that can be set to true BY the SECOND thread to indicate that the
    FIRST thread should stop itself
    - second thread polls the boolean
    - read/writing booleans is atomic (so some people don't synchronize it :( ))
        - this causes BREAKAGE (as example below shows. ) 
    
   
    
        EX: 
        
            public class StopThread {
                private static boolean stopRequested;
                
                public static void main(String[] args) throws InterruptedException {
                    Thread backgroundThread = new Thread(() -> {
                        int i = 0;
                        while (!stopRequested) {
                            i++;
                        }
                    });                
                    backgroundThread.start();
                    
                    TimeUnit.SECONDS.sleep(1);
                    stopRequested = true;
                }
            }
            
The reason that the example fails is that even though the boolean is atomic
    - WITHOUT synchronization there is no guarantee that the background thread will ever see the chang ein the value
    of "stopRequested" performed by the main thread. 
    
    
### Hoisting

    EX:
        W/o synchronization we can make the following change
        
        FROM
        
            while (!stopRequested) {
                i++;
            }
            
        TO
        
            if (!stopRequested) 
                while (true)
                    i++
                    
- The optimization above causes a LIVENESS FAILURE (program fails to make progress. )
    - but the program is still broken
    
    
### PROPERLY synchronized cooperative thread termination
- Synchronization is NOT guaranteed unless BOTH read and write operations are synchronized
    - NOTE: in the example below, atomicity (mutual exclusion) is already provided by the boolean data type, 
    however the communication effects are provided by the synchronization



        EX:
    
        public class StopThread {
            private static boolean stopRequested;
            
            private static synchronized void requestStop() {
                stopRequested = true;
            }
            
            private static synchronized boolean stopRequested() {
                return stopRequested;
            }
            
            public static void main(String[] args) throws InterruptedException {
                Thread backgroundThread = new Thread(() -> {
                    int i = 0;
                    while (!stopRequested())
                        i++;
                });      
                backgroundThread.start();
                
                TimeUnit.SECONDS.sleep(1);
                requestStop();
            }
        }
        
### Similar Solution using "volatile" keyword
- cost of synchronization on each loop in the above example is small (not prohibitive)
- however, use of VOLATILE keyword in the example below    
    - less verbose 
    - better performance
    

        EX:
        
            public class StopThread {
                private static volatile boolean stopRequested;
                
                public static void main(String[] args) throws InterruptedException {
                    Thread backgroundThread = new Thread(() -> {
                        int i = 0;
                        while (!stopRequested)
                            i++;
                    });
                    
                    backgroundThread.start();
                    
                    TimeUnit.SECONDS.sleep(1);
                    stopRequested = true;
                }
            }
            
            
## Volatile Keyword
- a modifier in java used in concurrency scenarios to guarantee that any thread trying to read the field on which
the modifier is used, will see the most recently written value.
- performs NO mutual exclusion

    
### Safety Failures - Due to misuse of 'volatile' 
- Safety failures are errors in concurrent applications due to the computation of wrong results based on thread
safety violations.
- Without my comments, this might appear to be ok. 
- however, ++ is not an atomic operation
    - increment reads the value and then writes back a new value. 
    - if a 2nd thread reads the field before it has been written back, it will return the wrong serialNumber back.
        
        EX:
        
            // field is atomic
            private static volatile int nextSerialNumber = 0;

            public static int generateSerialNumber() {
                // THIS OPERATION IS NOT ATOMIC!!!!            
                return nextSerialNumber++;
            }
            

### Solutions:
1. add synchronized modifier to generateSerialNumber() method declaration
    - ensures that multiple invocations won't be interleaved
    - each invocation of the method will see effects of all previous invocations
    - NOTE: 
        - REMOVE volatile from the nextSerialNumber field. 
        - The method that reads it is synchronized, so there is no need to duplicate atomicity
2. replace long instead of int
3. (alternative to 2) - throw an exception if nextSerialNumber is going to wrap around/overflow


## AtomicXXXX 
- use Atomic structures from java.util.concurrent.atomic
    - package that provides wrapped primitives for lock-free, thread-safe coding on SINGLE variables
- prefer Atomic structures over volatile. 
    - volatile provides ONLY communicability
    - Atomic structures provide both features of synchronization
    
    
    EX:
    
        // Lock free sync w/ Atomic data structure
        private static final AtomicLong nextSerialNumber = new AtomicLong();
        
        public static long generateSerialNumber() {
            return nexSerialNumber.getAndIncrement();
        }
        
## Save the best for last -- Immutability
The best way to avoid liveness and/or safety errors in NOT TO SHARE MUTABLE data.
1. if you are going to share data, make it immutable
1. if data needs to be mutable, don't share it.

This can be accomplished by confining mutable data to a single thread. 
- THIS IS A CHALLENGE
    - 1. this requires that you document this, and maintain that policy as the code evolves
    - 2. underlying frameworks/libs might introduce threads for you :)
    
    
### Effectively Immutable
- one thread modifies a data object
- that same thread shares the data object w/ other threads, synchronizing ONLY the act of sharing the object
references
    - this allows other threads to read the object w/o further synchronization 
    - AS LONG AS THE OBJECT ISN'T MODIFIED AGAIN
    
### Safe Publication
- transferring an object reference (safely) from one thread to another. 
    - HOW TO PUBLISH OBJECTS SAFELY
        - store obj ref in a static field as part of class initialization
        - store obj ref in a volatile field
        - store obj ref in a final field
        - store obj ref in a field that is accessed w/ normal locking
        - store obj ref into a concurrent collection (Item 81)