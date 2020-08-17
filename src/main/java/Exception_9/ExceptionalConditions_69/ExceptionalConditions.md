# Item 69: Use exceptions only for exceptional conditions

## Example of Poor Exception Usage

    try {
        int i = 0;
        while (true) {
            range[i++].climb();
        }
    } catch (ArrayIndexOutOfBoundsException e) {
        // do something
    }
    
WHY IT SUCKS
- it creates an infinite loop that:
    - throws the ArrayOutOfBoundsException
    - CATCHES the ArrayOutOfBoundsException
    - IGNORES!!!!!! the ArrayOutOfBoundsException
- it does all this once it tries to access the first element outside
the array bounds.

### What it should do:

    for (Mountain mountain : range) {
        mountain.climb();
    }
    
### Why did they do it? 
- There is a "loop-termination" test present in each for-each loop. 
- There is a faulty believe that since the VM checks the bounds of
all array access, that the loop-termination test is redundant. 

FLAWS IN THIS LOGIC
1. exceptions are designed for "exceptional circumstances", so JVM devs 
don't make them as fast as explicit tests. 
1. code in try-catch blocks are hidden from some JVM implementation optimizations
1. standard idiom for looping through arrays doesn't always result in 
redundant checks (The JVM will optimize them away/)

RESULT
- the exception-based method results in a considerable reduction in performance
- there is no guarantee that the process is going to work
    - using exceptions for flow control can mask proper exceptions, which
    complicates the debugging process. 

## API Design and Exceptions
- a well-designed API MUST NOT force clients to use exceptions for
control flow

### State-Dependent Methods
Some classes have state-dependent methods that have specific conditions
under which they are invoked. 

#### Solution 1 - State Testing Method
- if those conditions are unpredictable, then the class may require a
separate "state-testing" method to determine if it is appropriate to
invoke the "state-dependent" method.

ITERATOR provides hasNext() which is a state-testing method used to determine if it is
safe to execute next().

    EX:
        
        for (Iterator<Foo> iterator = collection.iterator(); iterator.hasNext(); ) {
            Foo foo = iterator.next();
            ... // other code
        }    
        
    
If ITERATOR, didn't have the hasNext() method, the alternative would be:


    EX:
    
        try {
            Iterator<Foo> iterator = collection.iterator();
            while (true) {
                Foo foo = iterator.next();
                ... // other code
            } catch (NoSuchElementException e) {
            
            }
        }
        
#### Optionals (Alternative to State-Testing Methods)
DOWNSIDES of state-testing method
- coders have to remember (or know in the first place) how to
use it. 
- in certain circumstances (like where concurrency is important), the time gap between the invocation 
of the state-testing method and its state-dependent method can result in consistency problems.

The alternative is for the state-dependent method itself ot return an empty optional if it can't
perform the desired computation
- (If an Optional isn't appropriate, then return NULL -> "a distinguished return value")

#### State-Testers vs. Optionals
Conditions for using Optional/Distinguished Return Value (ie Null)
- When Consistency is critical
    - (NOTE: both of these conditions require optionals because a separate state-testing method introduces
the potential consistency problems consistent w/ the time gap between a separate state-testing method and
the state-dependent method)
    - if object is accessed concurrently w/o external sync
    - if an object is subject to externally induced state transitions
- When Performance is critical
    - in some cases, state-testing method impls might result in duplicate code that exists in the 
    state-dependent methods. 
    - using optionals/distinguished return vals removes the duplication, resulting in an impl-specific
    performance bonus
    
Conditions for State-Testing Methods
- All other things equal, this is the (slightly) preferred method
    - code is more readable
    - incorrect use is easier to test, because the state-tester is explicit
        - if you forget to use it, an exception is thrown. 




    
## Best Practices
- "exceptions are to be used ONLY for exceptional conditions"
- exceptions should NEVER be used for ordinary flow control. 
- prefer the use of standard/recognizable idioms over clear techniques
that "might" (but usually don't) get better performance. 

