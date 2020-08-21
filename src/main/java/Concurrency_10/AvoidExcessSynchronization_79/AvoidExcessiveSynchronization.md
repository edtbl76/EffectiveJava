# Item 79: Avoid excessive synchronization

## Consequences of excessive synchronization
- reduced performance
- deadlock
- nondeterministic behavior

## Avoiding Safety + Liveness Failures
- NEVER give control to the client within a synchronized method/block
    - inside these blocks do NOT invoke "ALIEN" methods
        - methods designed to be overriden
        - methods provided by a client in the form of a function object (ITEM 24)
        

### Example 1 - Failure isn't Evident 
- (See AlienMethodExample1.ObservableSet) 
    - for an example of an Observer pattern that allows an alien method to modify an object inside the synchronized block
        - addObserver and removeObserver register/deregister subscribers
- (See AlienMethodExample1.SetObserver)
    - this is the functional interface provided as a callback to the Observers
    registration methods. 
- (See AlienMethodExample1.NaiveClient) 
    - this provides an executable "mini program"
    - this application will execute fine (printing the numbers from 0 - 99)
    
### Example 2 - ConcurrentModificationException 
- (See AlienMethodExample1.FancyClient)
    - Expected Behavior
        - print the numbers 0 - 23, 
        - subscriber is removed
        - program terminates 
    - Actual Behavior
        - print the numbers 0 - 23
        - ConcurrentModificationException
        
#### WHY?
- notifyElementAdded is a for loop that is iterating over the list of observers
when the added() method is called. 
- the added() method calls the ObservableSet's removeObserver method. 

The exception is thrown because we are trying to remove an element while 
iterating over it
- even though the notifyElementAdded method is in a synchronized block to prevent
concurrent modification
    - it does NOT prevent the iterating thread from calling back into the
    ObservableSet and modifying the observers list.
    - (This is the source of the concurrent modification)

### Example 3 - Deadlock
- (See AlienMethodExample1.ExecutorClient) for an example that attempts to 
solve the issues from Example 2
    - in example 2 we attempt to unsubscribe calling removeObserver directly
    - here we use an ExecutorService (Item 80) to use another thread to 
    perform the unsubscribe. 
    
- The result isn't much better than the previous example. While there is no 
exception, we run into a deadlock
    - the bg thread we create w/ the executor service calls removeObserver()
    - removeObserver() will attempt to lock observers, but it can't, because
    the main thread already has the lock
    - Unfortunately, the main thread is waiting for the bg thread to finish
    removing the observer
    - DEADLOCK.
    
## Examples when shared resources are INCONSISTENT 
- The previous examples included resources guarded by the sync'd region that
were in a CONSISTENT state when the alien method (added()) was invoked. 
- locks in java are reentrant
    - calls to resources in an inconsistent state won't deadlock

### Reentrant Locks
- reentrant locks are provided in java to give greater flexibility to synchronization
    - code that manages shared resources is guarded by calls to lock/unlock 
    - this ensures that the lock is owned by the current working thread, which 
    blocks all the other threads trying to acquire a lock on the shared resource

PROS
- simplify construction of multithreaded object-oriented programs

CONS
- can turn liveness failures into safety failures
    
### How the Failure Occurs
- calling thread holds the lock
    - it will succeed if attempts to reacquire the lock
    - (However, another unrelated operation is executing on the data
    guarded by the lock)
- This causes data inconsistency catastrophes
- "The lock fails to do its jobs"


## Solution 1 - Relocate Alien Methods Manually 
- move the alien method invocations out of synchronized blocks
    - this particular example uses a "snapshotting" technique that
    copies the observer list so that it can be safely traversed w/o a lock


    EX:
    
        private void notifiyElementAdded(E element) {
            List<SetObserver<E>> snapshot = null;
            synchronized(observers) {
                snapshot = new ArrayList<>(observers);
            }
            
            for (SetObserver<E> observer : snapshot) {
                observer.added(this, element);
            }
        } 

## Solution 2 - Relocate Alien Methods With Help (Concurrent Collection)

### Concurrent Collection 
(Item 81) <br> 
CopyOnWriteArrayList
- specifically made for the purposes of relocating alien methods
- variant of ArrayList 
    - all modifications are impl'd by taking a snapshot of the underlying array
    - since internal array is never modified
        - iteration is fast
        - no locking is required
    - NOTE: 
        - performance is TERRIBLE for conventional use. 
        - Perfect for observer lists which are traversed a lot and modified infrequently


    EX: 
    
        // Thread-safe observable set with CopyOnWriteArrayList
        private final List<SetObserver<E>> observers = new CopyOnWriteArrayList<>();
        
        public void addObserver(SetObserver<E> observer) {
            observers.add(observer);
        }
        
        public boolean removeObserver(SetObserver<E> observer) {
            return observers.remove(observer);
        }
        
        private void notifyElementAdded(E element) {
            for (SetObserver<E> observer : observers) {
                observer.added(this, element);
            }
        }
         

NOTE: the use of a concurrent collection removes the need for the explicit specification of a synchronized block

### Open Call
an open call is an alien method invoked OUTSIDE of a synchronized region
- prevents failures
- increases concurrency
- alien methods may be long running services
    - if these are run within a synchronize block, then other threads would be denied access to guarded
    resources necessarily. 
    - open calls provide a way to safely execute Alien Methods in classes/methods that require concurrency, 
    without putting the Alien Methods in the critical path (i.e. the synchronized blocks)
    
    
## Do as little as possible in synchronized regions
- get the lock
- examine shared data
- transform data as necessary
- drop lock

### Open Calls are King
- for Alien Methods and tasks that take a lot of time
    - get it out of the synchronized regions w/o breaking the rules of Item 78
    
## Synchronization and Performance
- Synchronization cost has improved
    - CPU time spent getting locks has decreased over time
- CONTENTION
    - this is the "real" performance cost of synchronization
    - lost opportunities for parallelism/ delays imposed by the need to ensure that
    every core has a consistent view of memory
- oversynchronization can limit VM's ability to optimize code execution

### Options for Guarding Mutable Classes
1. Omit all synchronization 
    - allow client to synchronize externally if concurrent use is required
    - java.util Collections do this (Except Vector and Hashtable)
    - StringBuilder
    - java.util.concurrent.ThreadLocalRandom
2. Synchronize internally 
    - this makes the class "Thread-Safe" (Item 82) 
    - only choose this if you can get MUCH BETTER concurrency than you could w/ 
    option 1.
    - java.util.concurrent Collections take this approach
    - StringBuffer
    - java.util.Random
    
3. When in doubt:  
    - do NOT synchronize
    - document that the class is NOT thread-safe
    
    
#### Techniques for synchronizing classes to achieve high concurrency
- lock splitting
- lock striping
- non-blocking concurrency control

#### Static Fields
If a method modifies a static field, and there is a possibility that the method will
be called from multiple threads
    - it MUST be synchronized internally
    - it is impossible for multithreaded clients to perform external synchronization 
    because unrelated clients can invoke the method w/o synchronization
    
