# Item 21: Design interfaces for posterity.
Default methods were added to interfaces in java 8, to make it easier to use
interfaces. 
- Fewer compile time errors. 

## PITFALLS
In the presence of default methods, existing implementations of an interface may 
compile without error or warning, but fail at runtime.

## BEST PRACTICES
- Using default methods in interfaces should be a last result
    - use SKELETAL IMPLS instead
    - the other issue is that removing default methods from interfaces is going to
    likely break clients.
    