# Item 44: Favor the use of standard functional interfaces


## Departure from Template Method due to Lambdas
A subclass overrides a "PRIMITIVE" method to specialize the behavior of
its superclass. 

This is less used in modern APIs since the introduction of lambdas

### ALTERNATIVE
- provide a static factory/constructor that accepts a function object
to achieve the same effect as a template method. 

## Newer patterns/paradigms due to lambdas
Generally, constructors/methods will use function objects as parameters
more often. 
- This shifts Java to lean towards a more functional type of programming.

## Use LinkedHashMap as a Cache (Pre-Lambdas)
This can be accomplished by overriding the removeEldestEntry() method
- removeEldestEntry() is invoked by put() when new keys are added to the
map. 
- when it returns true, the map removes its oldest entry, which is passed
to the method. 


    EX:
    
        protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
            return size() > 100;
        }
        
## Lambda-fying LinkedHashMap
- The first step would be creating a static factory or constructor
that takes a function object as an argument.
    - based on the previous design, you would think that the function
    would take same argument and have the same return type as
    removeEldestEntry()
    
    - the logic of the previous example works, because removeEldestEntry
    is an instance method of the Map it is operating against. 
    
    - this fails as a Functional Interface, because the function object
    being passed to the constructor/static factory is NOT an 
    instance method of the Map. 
        - in fact, the map doesn't even exist when the constructor/factory
        is invoked. 
        
SOLUTION? 
- the map must pass itself to the function object, which must
take the map on input in addition to its oldest entry: 


    EX: 
    @FunctionalInterface
    interface EldestEntryRemovalFunction<K,V: {
        boolean remove(Map<K,V> map, Map.Entry<K,V> eldest);
    }
    
SOLUTION!
- The previous example would work, but its an "unnecessary purpose
built functional interface"

The goal of this item is to prefer standard functional interfaces
provided by java.util.function package. 
- API is easier to learn, because we reduce the "conceptual
surface area"
- interoperability benefits by leveraging parts of the standard library


EXAMPLES:
- Predicate interface
    - provides methods for combining predicates


    EX: 
    
        BiPredicate<Map<K,V>, Map.Entry<K,V>> 
        
The example above is a preferred solution to the home built
solution in the SOLUTION? section. 

## java.util.Function, Interfaces
There are 43 total interfaces. You aren't going to remember them all. 
- BUT, if you can learn the 6 basic interfaces, you will have the tools
to derive the remaining 37. 

TYPES:
1. Operator
    - functions whose result and argument types are the same
1. Predicate
    - function that takes an argument and returns a boolean
1. Function 
    - function whose result and argument types are NOT the same
1. Supplier
    - function that takes no arguments and returns/supplies a value
1. Consumer
    - function that takes an argument and returns/nothing 
    - it "consumes its arguments"
    
(See SixBasicFunctionalInterfaces.md for more details)

Don't be tempted to use basic functional interfaces w/ boxed
primitives instead of primitive functional interfaces
- "The performance consequences of using boxed primitives for 
bulk operations can be deadly"

## When To Write Your Own FI

### CASE 1
The obvious case is -> when the standard functional interfaces 
don't meet your needs

### CASE 2
The not-so-obvious case is that there might be times you should write
your own functional interfaces, even when they are identical to the
structure of a standard one. 

EX:
Comparator<T> is structurally identical to ToIntBiFunction<T,T>

WHY?
- the naming provides an executable specification (documentation)
every time it is used in an API, and it is used PERVASIVELY
- has a GENERAL CONTRACT, where its interface has strong requirements
on what constitutes a valid instance
    - (using the interface means "I accept the contract!")
- interface has default methods that are specific to the use cases
of transforming/combining operators. 

Therefore, there are 3 cases for writing your own FI, even if a
standard FI exists. 
- frequent use that would benefit for specific identification
    - i.e. (it's own name) 
- it has a "strong contract" associated w/ it.
- it would benefit from default custom methods

## IMPLEMENTATION
- It is an interface. 
- it should be handled with great care. 

### Annotation
FI's require the @FunctionalInterface annotation which do 3 things:
1. tells readers of the class that the interface was designed to
enable lambdas
1. won't allow interface to compile unless there is exactly ONE
abstract method 
1. prevents maintainers from accidentally adding abstract methods

## Best Practices for using FIs in APIs
- Don't provide a method w/ multiple overloadings that take different
FIs in the same argument positions if it could create ambiguity in 
the client. 

    EX:
    - submit() method of ExecutorService can take either a
    Callable<T> or a Runnable. 
        - it is possible to write a client program that requires
        a cast to indicate the correct overloading. 
    
