# Item 76: Strive for failure atomicity

## Failure-Atomicity
A method is "failure-atomic" if a failed invocation of said method leaves
the object in the state that it was prior to invocation.

### Immutable Objects
Immutable objects provide failure-atomicity by default. (Item 17)
- failures may prevent new objects from being created, BUT objects will never
be left in an inconsistent state because it can't be modified after creation

### Mutable Objects
- 1.) most common approach is to validate parameters before performing the operation
(Item 49)
    - results in throwing MOST exceptions before the objects are modified
    
    
    EX:
        
        public Object pop() {
            // validation throws an exception before we mod the object
            if (size == 0) {
                throw new EmptyStackException():
            }
            
            Object result = elements[--size];
            elements[size] = null;
            return result;
        }
        
    
- 2.)  related approach is to manage logic ordering so that any part that fails
will take place before any part that modifies the object
    - similar to the approach of Sagas (Compenstable, Pivot, Retriable)

- 3.)  third approach is to perform the operation on a temp copy of the
object
    - if the operation completes successfully, copy the object contents from
    the copy to the target. 
    - similar approach is used in Sorting Functions
        - sorting functions copy their input list into an array prior to
        sorting to reduce the cost of accessing elements in the inner 
        loop of the sort. 
        - the main focus is the performance benefit, but the side bonus is
        that it ensures the input list isn't touched if the sort fails 
        - (failure atomic)
    - NOTE: 
        -  if there isn't a good reason to perform the separation/copy step, 
        DONT. 
        - adding a copy operation just for the sake of failure-atomicity is
        going to duplicate logic and result in performance hits. 
       

- 4.) last approach - > "Recovery Code"/Rollback
    - recovery code intercepts a failure that occurs in the middle of an
    operation. 
    - causes object to rollback its state to the point before the operation
    began. 
    - NOTE: This is primarily used for disk/durable/persistent data structures

## Practical Concepts of Failure Atomicity
- Concurrency scenarios where attempts are made to mod the same object w/o
proper synchronization can not provide failure atomicity. 
- Errors are unrecoverable, so there isn't a need to preserve failure atomicity
- Sometimes, the cost of providing FA is prohibitively expensive.
     