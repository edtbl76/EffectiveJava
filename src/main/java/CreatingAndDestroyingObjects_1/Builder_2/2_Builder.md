# Builders
Builders solve a shared limitation of both Factories and Constructors
- neither of them scale well to large numbers of optional parameters

## WHEN TO USE
- when you've got a HANDFUL of parameters that make the FACTORY or CONSTRUCTOR challenging to implement

TELESCOPING CONSTRUCTOR PATTERN<br>
Uses a base constructor  w/ required parameters, then overloads the constructor
adding one optional parameter at a time

- gets out of hand/hard to manage very quickly
- hard to read
- adds complexity to client code.
- easy to transpose params, resulting in strange semantic bugs
- doesn't actually represent all possible combinations


JAVA BEANS PATTERN<br> 
Uses a no args constructor, providing setter/getters for both required and optional
parameters

- since the construction is split across (MANY) multiple calls, the Bean may have inconsistent state
    - there is no way to check/ or enforce validity (i.e. via method contract) established in constructor parameters
- HARD to debug circumstances
- since there are setters, we are allowing all of the values to be mutated.     
    - impossible to enforce immutability
    - leads to additional challenges to ensure thread safety
        - requires a FREEZE method to implement locking, preventing attribute from being used

## PROS
1. Required Parameters are provided in the Builder's constructor, allowing the Builder to perform to a contract that
can be validated. 
    - validation checks can be placed inside the constructor and withXXXX() methods to eagerly invalidate parameters
    - illegal args should through IllegalArgumentException
    
2. Optional Parameters are provided through chainable "setters" (withXXXX() methods) that return an instance of the 
Builder itself. 
    - optimally optional parameters are initialized to defaults, but if this isn't plausible, NullObject can be used.
    
3. Works well w/ Class Hierarchies
    - a class hierarchy of A-> B -> C can work well w/ a builder hierarchy BuilderA -> BuilderB -> Builder C
        - Abstract Classes -> Abstract Builders
        - Concrete Classes -> Concrete Builders
        - no self type in Java, so you need a SELF-SIMULATING idiom
            - use an abstract self() method, so that each subclass can return "this"
        - to avoid casting, use a GENERIC TYPE with a RECURSIVE TYPE PARAMETER
            - a.k.a COVARIANT RETURN TYPING
            - ("a subclass method is declared to return a subtype of the return type declared in the super class")
                - this allows method chaining to work w/ o having to cast subclasses to their parents.
                - allows the builder in each subclass to return the type of THAT subclass

4. builders can have multiple "varargs" params because each param is specified in its own method. 
    - alternatively (like in the example) you can just chain/repeat multiple fields of the same type over and over
    again. 
    
5. 1 builder can be used to repeatedly build multiple objects (constructors can't.)

6. parameters of builder can be dynamically changed between invocation 
    - objects are determined at runtime. 
    

## CONS
1. In order to create an object, you have to create the builder. 
    - in most cases not an issue, but in perf-critical circumstances, it does induce some latency. 

2. more verbose (but more flexible) than TELESCOPING CONSTRUCTOR
    - general rule of thumb is to use it when you have FOUR OR MORE parameters. 
    - also, if you are unsure (i.e. objects are under development), start w/ the builder anyway, because it is challenging
    to refactor from constructors/factories to builders





