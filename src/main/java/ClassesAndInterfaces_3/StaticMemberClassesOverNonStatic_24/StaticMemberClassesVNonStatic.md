# Item 24: Favor static member classes over nonstatic

## NESTED CLASS
A class defined within another class
- should exist ONLY to serve the enclosing class
- if it has some other use, it should be its own TOP-LEVEL class.

TYPES OF NESTED CLASSES:
1. static member classes
1. nonstatic member classes
1. anonymous classes
1. local classes

Type 2 -3 are known as INNER CLASSES

### PUBLIC STATIC MEMBER CLASS
Simplest form of nested class
- "ordinary" class that is defined inside another class
- has access to ALL members of enclosing class (even private)
- static member of enclosing class, obeys same principles as other static members
    - i.e. if declared private, its only accessible to enclosing class, etc.
   
USES: 
- public helper class
    - Enums that describe operation types of the outer class may fall into this category 
    
### PRIVATE STATIC MEMBER CLASS

These are usually used to represent components of the object represented by the
enclosing class
- EX: Map impls have internal "Entry" object for each KV pair in the map. 
- while the entries are PART of the Map, they are self contained components that
don't need access to the map. 

While using nonstatic members would WORK, this is considered a performance 
optimization (or wastage eliminator.)

NOTE: Componentization via static members is also a good example of COMPOSITION 
OVER INHERITANCE. 
    
### NONSTATIC MEMBER CLASS
The only "syntactical" difference between nonstatic and static member classes is 
the use of the STATIC keyword


Very different in terms of functionality
- each instance of a nonstatic member class is implicitly associated w/ an ENCLOSING
INSTANCE of its containing class
    - nonstatic member classes CAN NOT exist in isolation from instances of the
    enclosing class. 
- you can invoke methods on the enclosing instance
- you can obtain a reference to enclosing instance using QUALIFIED THIS construct

The association between a non-static member and its enclosing class is established
when the member is created and can no longer be modified.
- usually established automatically by invoking a nonstatic member class constructor
from within an instance method of the enclosing class

Common Use for NONSTATIC MEMBER
- define an ADAPTER (the GoF stuff) that allows an instance of the outer class to be
viewed as an instance of some unreleated class. 
- EX: Java Map interface impls use nonstatic member classes to implement COLLECTION VIEWS
that are returned by the keySet() entrySet() and values() methods.


    EX: 
        public class MySet<E> extends AbstractSet<E> {
            ... // Stuff
            
            @Override
            public Iterator<E> iterator() {
                return new MyIterator();
            ;
            
            private class MyIterator implements Iterator<E> { ... }
        }

### STATIC vs. NONSTATIC
If you declare a member class that does not require access to an enclosing instance
- it should be a static member (and therefore have the STATIC keyword in the declaration)

STATIC MEMBERS = have no reference to enclosing instance <br>
NONSTATIC MEMBERS = have hidden reference to enclosing instance

Why? 
- storing the reference to the enclosing class adds to the time/space used 
- the reference is also a MASSIVE risk for MEMORY LEAKS (remember.. Unintended Object References)

The hidden/implicit reference from a nonstatic inner class can prevent the enclosing instance from being eligible
for GC -> UOR 
- This is especially hard to debug the reference is invisible
- This is mentioned above in the PRIVATE STATIC MEMBER section as a "performance
optimization". 

If member classes are public/protected members of an EXPORTED CLASS:
- this means that the members are part of an exported API element in most cases
- we can't change the class from nonstatic -> static (or vice versa) w/o
violating BACKWARD COMPATIBILITY


### ANONYMOUS CLASSES
- have no name
- not a member of enclosing class. 
    - they have enclosing instances, if and only if they occur in a nonstatic
    context.
- (even if anon class is static) it can't have STATIC MEMBERS unless:
    - member is CONSTANT VARIABLE
        - (final primitive or String values init'd to constant expressions)
- both DECLARED AND INSTANTIATED AT POINT OF USE
- permitted at any point in code where an expression is legal

MANY LIMITATIONS
- they can only be instantiated at the point they are declared
- you can't perform instanceof tests or do anything that requires the class
to be named
- can't impl multiple interfaces or extend a class and impl an interface at the
same time. 
- clients of anonymous classes can't invoke any members except those it 
inherits from its supertype. 

READABILITY
- based on the way they look, and how disjointed they make code, they should be 
very brief. 
    - 10 lines or less
    
USE CASES
- For the most part, they have been completely replaced by lambdas. If you need
to use them now, its often to "undo" the abstraction created by lambdas so you
can see what is actually happening.... OR as a teaching tool to get someone to
the point of using lambdas 

### LOCAL CLASSES
Least frequently used of the 4 kinds of nested classes. 
- can be declared anywhere a local variable can be declared
- obeys the same scoping rules

TRAITS
- they have names and can be used repeatedly
- like anonymous classes, they have have enclosing instances only if 
they are defined in a nonstatic context. 
- can't contain static members
- should be kept short to avoid readability issues. 


### DECIDING WHAT TO USE
1. MEMBER CLASSES
    - used if a nested class needs to be visible outside of a single method
    or if it is too long to sit comfortably in a method
    1. NONSTATIC 
        - if every instance of the member class needs a reference to
        the enclosing instance
    1. STATIC
        - if the instances DONT need a reference to the enclosing instance
1. ANONYMOUS
    - if the class belongs in a method
    - if you need to create instances from only one location
    - if there is a pre-existing type that characterizes the class
1. LOCAL
    - same as anonymous, but it can be called from 
    multiple locations, and there isn't a pre-exisitng type
    that characterizes the class.