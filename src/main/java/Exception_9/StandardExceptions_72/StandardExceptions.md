# Item 72: Favor the use od standard exceptions

## Code Reuse
A sign of expert programming vs just "good" programming is how well 
the coder is able to master/demonstrate code reuse

## Standard Exceptions
These are the exceptions provided by Java libs
- covers most API-driven needs

### Benefits of Reusing Standard Exceptions
- makes API easier to learn/use
    - standard exceptions are a familiar convention for most coders.
- makes API easier to read
    - (same reason as above)
- fewer exception classes results in a smaller memory footprint
    - less time spent loading classes
    - less memory means... less memory :)
    
### Common Exception Types

IllegalArgumentException
- (Item 49) thrown when caller passes in an argument whose value is inappropriate
    - (i.e. passing negative number, when positive is required)

IllegalStateException
- thrown if the invocation is illegal because of the state of the receiving
object. 
    - (i.e attempting to use an object before it was init'd)
    
NullPointerException
- thrown when null is provided as a parameter for which null vals are
prohibited. 

IndexOutOfBoundsExceptions
- passing in an out-of-range value in a parameter representing an index
into a sequence

ConcurrentModificationException
- thrown if an object that was designed for use by a single thread 
(or w/ external synchronization) detects that it is being modified concurrently
    - NOTE: this is a "best effort" exception, because it is 
    IMPOSSIBLE to reliably detect concurrent modification

UnsupportedOperationException
- thrown if an object doesn't support an attempted operation
- pretty rare, because most objects support all of their methods
- the use case for this exception:
    - by classes that fail to impl one or more OPTIONAL operations defined by
    an interface it impls. 
    
    
These are the most GENERALISTICALLY pervasive exceptions
- However there are plenty more where reuse is appropriate in more specific circumstances
    
### Exceptions that should NOT be reused directly
- Exception, RuntimeException, Throwable, Error
    - treat this as if they were abstract
    - they can't be reliably tested for because they are SUPERCLASSES
    of other exceptions that a method may throw
    
### Common Exception Table

| Exception | Occasion for Use | 
| --- | --- |
| IllegalArgumentException | non-null parameter value is inappropriate |
| IllegalStateException | Object state is inappropriate for method invocation |
| NullPointerException  | Parameter value is null where prohibitied | 
| IndexOutOfBoundsException | index parameter value is out of range | 
| ConcurrentModificationException | Concurrent modification of an object has been detected where it is prohibited |
| UnsupportedOperationException | Object does not support method |

## Reuse Best Practices
- reuse should be based on documented semantics, not just the names. 
    - choose exceptions based on the exception's docs. 
- subclass to add detail (Item 75)
    - CAREFULLY: exceptions are serializable
- do NOT write your own exception class w/o good reason
    - exceptions are serializable