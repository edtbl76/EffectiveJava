# Item 65: Prefer interfaces to reflection

## Reflection
java.lang.reflect (core reflection facility)
- provides coders access to arbitrary classes. 

Given a class Object, you can obtain the following class instances
- Constructor
- Method
- Field

that represent the associated 
- constructor
- methods
- fields

represented by the Class instance. 


### Constructor, Method, Field
- These objects provide programmatic access to class's members
- instances of these objects allow the manipulation of their 
underlying counterparts "reflectively"
    - construct instances
    - invoke methods
    - access fields of 
    - (all of this is accomplished by invoking methods on Constructor, Method
    or Field.)
    
### Reflection Power (and its cost)
Reflection allows one class to use another, even if the latter didn't
exist when the former was compiled. 

COST:
- You lose all advantages of compile time checking
    - includes exception checking
    - This means that reflective invocations of nonexistent/inaccessible
    methods will fail at runtime.
- code is hard to maintain/read
- performance hit
    - reflective method invocation is much slower than method invocation
    
    
## WHERE IT IS USED
- code analysis tools
- DI frameworks

## Reflecting Effectively
The best way to use reflection is in limited form
- optimize the benefits
- minimize the costs

## Example 1 - Instantiate Reflectively, Access Normally
at compile time, programs that require a class that isn't available
may be able to substitute an appropriate superclass/interface by which to
refer to the required class. 
- create the instances reflectively
- access them normally via the interface/superclass

(See EffectiveReflectionExample1.EffectiveReflectionExample1)
- this is a typical representation of all a growing developer needs in terms of 
reflection

CONS (specific to this example)
- there are SIX possible exceptions that can occur at runtime
    - w/o reflection, these were all compile time errors (which is preferred)
- the code is an absolute horror to read and maintain. 
    - it is 25 lines to generate an instance of the class from its name
    - VS. a single line constructor. 

    
    
