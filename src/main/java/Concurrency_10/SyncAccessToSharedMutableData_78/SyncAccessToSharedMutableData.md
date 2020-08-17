# Item 78: Synchronize access to shared mutable data. 

## Synchronized
- keyword used to ensure that only a single thread can execute a method or block at one time.
- Synchronization is REQUIRED for reliable communication between threads as well as mutual exclusion
    - due to MEMORY MODEL which specifies how changes made by one thread become visible from another. 
        - Java Memory Model is a built-in specification of the language.

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