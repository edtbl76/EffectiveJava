# Item 73: Throw exceptions appropriate to the abstraction

## PROBLEM
Method throws an exception that has no apparent connection to the task it performs.
- happens when method propagates an exception thrown by a lower-level abstraction
- dirties API w/ impl details
    - changes to the API may result in changes to the exceptions it
    throws, breaking the clients
    
## SOLUTION -> Exception Translation
Higher layers should catch lower-level exceptions
- replace lower-level exceptions with exceptions that can be explained in
terms of higher-level abstraction

    EX: 
        // Exception Translation
        try {
            ...  // use lower-level abstraction to do our bidding
        } catch (LowerLevelException e) {
            throw new HigherLevelException(...);
        } 
        

### EXAMPLE - AbstractSequentialList
- This is a skeletal impl (Item 20) of List interface


    EX: 
    
        /**
         *  Returns the element at the specified position in this list
         *  @throws IndexOutOfBoundsException if the index is out of range
         *          ({@code index < 0 || index >= size()}).
         */
        public E get(int index) {
            ListIterator<E> i = listIterator(index);
            try {
                return i.next();
            } catch (NoSuchElementException e) {
                throw new IndexOutOfBoundsException("Index: " + index);
            }
        }
        
### EXAMPLE - Exception Chaining
- special form of exception translation that is called for in cases where the lower-level exception
might be useful in the process of debugging the root cause that led to the higher-level exception
    - CAUSE (lower level exception) is passed to the higher-level exception
    - higher-level exception provides an accessor method (i.e. getCause()) to extract the lower-level
    exception
    
    
    EX: 
    
        try {
            ... // use lower-level abstraction to do our bidding!
        } catch (LowerLevelException cause) {
            throw new HigherLevelException(cause);
        }
        
- THEN
    - higher-level exception's constructor passes the cuase to a CHAINING-AWARE superclass constructors
    - it is eventually passed to one fo Throwable's chaining-aware constructors 
        - i.e. Throwable(Throwable)
    
    
    EX:
    
        // Exception w/ a chaining-aware constructor
        class HigherLevelException extends Exception {
            HigherLevelException(Throwable cause) {
                super(cause);
            }
        }
        
- If the exception being used doesn't have a chaining-aware constructor
    - cause can be set via Throwable.initCause() 
    - (NOTE: most standard exceptions DO have chaining-aware constructors)
    
    
BENEFITS OF EXCEPTION CHAINING
- allows programmatic access to lower-level exception (getCause())
- the cause's stack track is integrated into the higher-level exception


