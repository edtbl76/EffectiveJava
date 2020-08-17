# Item 74: Document all exceptions thrown by each method.

## Checked Exceptions
- should be declared individually
- precisely document the conditions under which each one is thrown
    - @throws tag (Javadoc)

Avoid shortcuts
- don't declare that a method throws a superclass of multiple exception
classes that it can throw. 
     - EX: Exception, Throwable
     - shitty/vague documentation (lazy)
     - obscures the use of exceptions, hindering the effective use of the method
     - EXCEPTION
        - main method can throw Exception

## Unchecked Exceptions
- not required, but recommended to be documented as carefully as 
checked exceptions.

### PRECONDITIONS
- contains a well-documented list of the unchecked exceptions that 
the method can throw
- EVERY public method should describe its preconditions (Item 56)
    - documenting unchecked exceptions can satisfy this. 
    
## @throws tag vs. throws keyword
- @throws tag (Javadoc) should be used to doc each exception that a method can throw
- throws keyword should NOT be used on unchecked exceptions

These two conventions help developers identify which exceptions are
checked and which are unchecked
- developers must remediate each situation differently
    - @throws + throws = CHECKED
    - @throws only = UNCHECKED


## Class vs. Method Documentation
- If an exception is thrown by many methods in a class for the same reason, 
you can place it in the CLASS doc comment rather than per method.
