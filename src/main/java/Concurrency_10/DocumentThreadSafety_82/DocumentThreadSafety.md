# Item 82: Document Thread Safety

Proper documentation of thread safety protects the use of class members that are 
used concurrently from incorrect assumptions about HOW they should be used:
- Insufficient Synchronization (Item 78)
- Excessive Synchronization (Item 79)


## Synchronized modifier
- This is NOT a good indicator of concurrency
- "The presence of the synchronized modifier in a method declaration 
is an impl detail, not a part of its API"

## Thread-Safety Levels
- To enable safe, concurrent use of a class
    - that class must CLEARLY document what level of thread safety it supports
    
NOTE: (The following levels loosely correspond to the thread safety levels provided in 
"Java Concurrency in Practice")
- Immutable
- ThreadSafe
- NoteThreadSafe
    
### Immutable
- instances of the class are constant
- no external synchronization is necessary
- Examples
    - String, Long, BigInteger
    
### Unconditionally Thread-Safe
- instances of the class are mutable
- class has sufficient INTERNAL SYNCHRONIZATION
    - classes can be used concurrently w/o the need for external synchronization
- supports Private Lock
- Examples
    - AtomicLong, ConcurrentHashMap
    
### Conditionally Thread-safe
- similar to unconditionally thread-safe
- SOME methods require external synchronization for safe concurrent
use
- MUST document which method invocations require external synchronization
- MUST document which lock to acquire in order to invoke those sequences
- does NOT support Private Lock 
- Examples
    - collections returned by the Collections.synchronized wrappers, 
    whose iterators require external synchronization
    
### Not Thread Safe
- instances of these class are mutable
- for these classes to be used concurrently
    - clients must encapsulate each method invocation/invocation sequence w/ an external synchronization
- Examples
    - general-purpose collection impls (ArrayList, HashMap)
    
### Thread-hostile
- considered UNSAFE for concurrent use
    - EVEN if every method invocation is surrounded by external synchronization
- results from modifying static data w/o synchronization
- this is usually not intentional
    - accidental result of failure to consider concurrency
    - FIX or DEPRECATE
- Example

## How to document conditionally thread-safe classes
- indicate which invocations/invocation sequences require external synchronization
    - indicate which lock(or locks) must be acquired to execute these sequences
    - USUALLY, lock is on the instance itself. 
    
Exception Example

    Collections.synchronizedMap
    
        "it is imperative that the user manually synchronize on the returned map when iterating over
        any of its collection views"
        
        Map<K, V> map = Collections.synchronizedMap(new HashMap<>());
        
        // Doesn't need to be in the sync'd block
        Set<K> set = map.keySet();    
        
        ...
        // Synchronizing on map, not set
        synchronized(map {
            for (K key : set) {
                key.f();
            }
        }
    
- class thread safety should be included in the class's doc comments
- methods w/ special thread-safety properties should describe these properties in the method-specific
doc comment.s
- static factories should document the thread safety of the returned object
    - EXCEPTION (when the return type makes its own thread safety obvious)
    


## Problems when classes use Publicly Accessible Locks
PROS
- clients can execute a sequence of method invocations atomically

CONS
- incompatible w/ high-performance INTERNAL concurrency control (i.e. ConcurrentHashMap)
- clients can mount DoS Attack by holding publicly accessible lock too long/forever
    - accidental OR intentional
    
### Private Lock Object
(Prevents DoS attack w/ publicly accessible locks)

    EX:
        // Private lock object paradigm (solves DoS Attack) 
        private final Object lock = new Object();
        
        public void foo() {
            synchronized(lock) {
                ... 
            }
        }

- private lock object is inaccessible outside the class, so clients can't interfere w/ the object's 
synchronization
    - (Item 15) We are encapsulating the lock object in the object it synchronizes
- this is only supported on UNCONDITIONALLY THREAD SAFE CLASSES
    - conditional thread safe classes can't use this, because they require that the class documents
    the locks the clients must acquire when performing invocation sequences. 
- VERY well-suited to classes deisgned for inheritance (Item 19)

### Lock Objects as final
- Lock fields should ALWAYS be declared final
    - prevents accidental/intentional changes to its contents
        - would cause unsynchronized access (Item 78) 
- true regardless of the type of lock
    - "ordinary lock" like the example above
    - java.util.concurrent.locks package
    