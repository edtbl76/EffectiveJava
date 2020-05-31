# Item 34: Use Enums Instead of Int Constants

## ENUM = Enumerated Type
Type whose legal values consist of a fixed set of constants
- seasons of year
- planets in solar system
- suits in deck of playing cards, etc. 

A more formal definition of the use case:

    Use Enums any time you need a set of constants whose members are
    known at compile time.
    
It isn't required for the enum type to stay fixed for all time. 
- they can grow to accomodate new use cases and they can shrink or
be deprecated/replaced if the use case is no longer neccessary or is no
longer fulfilled by an enum. 

created to supplant the practice of using int constants:

## INT CONSTANTS - YUCK!

    EX:
    
        public static final int MERCURY = 0;
        public static final int MARS = 1;
        public static final int VENUS = 2;
        public static final int EARTH = 3;
        OK NO MORE!
        
WHY IT SUCKS
- poor type safety
- poor expressive
- no compiler checking
- doesn't know which operators to evalutate
- no namespaces, so there could be collisions with values that had
the same name. (required the use of PREFIXES_) in the names. 

Since they were ints, you could actually calculate them to get 
weird results. 
- Above, EARTH - VENUS = MARS is true, even though it has absolutely no
meaning or significance. 

MORE REASONS IT SUCKS
- the values are CONSTANTS, so an application must be recompiled for
the values to change. 
- no good way to print the ints into a string for identification (
logging, debugging etc.)

NO, Using Strings isn't any better. 
- poor performance due to the need for string comparisons
- typogrphical issues etc. 

## Back to Enums. 

    EX: 
    
        public enum Planet {
            MARS, 
            MERCURY, 
            VENUS, 
            EARTH, 
            JUPITER,
            SATURN,
            URANUS,
            NEPTUNE,
            PLUTO
        }
        
## Not your C/C++/C# ENUMS
Java enums are full-fledged classes. 
- classes that export ONE instance for each enumeration constant via a
PUBLIC STATIC FINAL FIELD
- support the addiitonal of arbitrary methods, fields and interfaces
- "high quality implementations of all Objects methods"
    - equals, toString, finalize, and hashCode
- implements Comparable and Serializable
    - serialized form is designed to withstand most changes to the 
    enum type.
    
    

All enum types are EFFECTIVELY FINAL because they don't have accessible
constructors
- clients can't create instances of an enum
- clients can't extend an enum

Enum Types are "Instance-Controlled"
- there can be no instances other than the declared enum constants
- they are essentially singletons

Enums provide compile-time type safety
- guaranteed that any non-null object reference passed as a parameter is 
one of the valid constant values of the Enum. 
- attempts to pass the wrong time won't compile. 
    - same as attempting to assign expressions of an enum type to a
    var of another type
    - same as using == operator to compare values of different enum types

Solves naming collisions, recompile issues and debug printing issues
- Each Enum provides a separate name space for the CONSTANTS it contains, 
so CONSTANTS inside it can be safely named without having to worry about
collisions
- constants can be added/reordered w/o needing to recompile clients
- enums can be printed as strings by calling their toString() methods


## ADDING METHODS/FIELDS to an enum type
(see EnumTypeWithDataAndBehavior.Planet as an example)
- You might want to associate data w/ the enum type constants. 
- enums can be augmented with any method that seems appropriate.

To associate data w/ enum constants
- declare instance fields
- write a constructor that takes the data and stores it in the fields

Enums are immutable by default
- but it's still more conventional to make fields private and use
accessors. 

## Feature Restrictivity (Private, Package-Private)
There are some behaviors associated w/ enum constants that may need
to be restricted to the class/package in which the enum is defined
- in these cases it is a good idea to implement those features as
private or default (package-private)
- this isn't really a departure from other classes. Unless there is
a good reason for functionality to be exposed.. don't. 

## Enum Placement (Code Reuse!)
If an Enum is "generally" useful 
- Make it a top-level class

If the use is tied to a specific top-level class
- Make it a member class of the top-level class. 

Example is java.math.RoundingMode. 
- used by BigDecimal, but the abstraction isn't specifically tied to
BigDecimal. 
- making RoundingMode a top-level enum, encourages code reuse. 

## Associating Different Behavior With each Constant
Look at EnumTypeWithDataAndBehavior.OperationBad.
- This is an example of implementing behavior... poorly. 
    - the first problem is that we have to have that throw statement in
    order to get it to even compile. 
    - the second problem is that the code is somewhat brittle because 
    the operation of the switch statement is tied to the CONSTANTS, it
    woudl be very easy to break. 
    
Now look at EnumTypeWithDataAndBehavior.OperationBetter 
- This is the "better" way to associate different behaviors with each
enums. 
    - start by declaring an abstract method in the enum type
    - then we override the abstract method for each constant in a
    CONSTANT-SPECIFIC CLASS BODY
    
These methods are called "CONSTANT-SPECIFIC METHOD IMPLEMENTATIONS"

Now Look at EnumTypeWithDataAndBehavior.OperationBetterPlusData
- This combines using data AND behavior in the same Enum Type.

### valueOf()
Enum types have an auto generated valueOf() method that translates the
constant's name into the constant self. 
- BEST PRACTICE
    - if you are going to override toString() method in an Enum type, 
    - consider writing a fromString() method to translate the custom
    string representation back to the corresponding enum
    
    
    EX:
    
        private static final Map<String, Operation> stringToEnum =
            Stream.of(values()).collect(toMap(Object::toString, e -> e));
            
        // Returns operation for string, if any
        public static Optional<OperationBetterPlusData> fromString(String symbol) {
            return Optional.ofNullable(stringToEnum.get(symbol));
        }
    
The Enum constants are put into the stringToEnum map
- this is a static field initialization that occurs after the enum
constants are created.
- The use of a stream over the provided array returned by values() is
a Java 8+ convention. 
    - this is more readable and terse as we don't have to create an 
    empty data structure and initialize it. 


Enum Constructors can't access an Enum Types static fields
- EXCEPTION: they CAN access constant variables.
- this is because the static fields haven't been initialized when
enum constructors are executed. 
- a special case of this restriction is that enum constants can't
access each other through their constructors.

fromString returns an Optional<>
- Optionals are a good way of handling the possibility that the String
passed in was not valid. 

### Disadvantages of ConstantSpecific Method Implementation
- hard to share code among enum constants. 
(see ConstantSpecificMethodImplementation.PayrollDayBad)

These types of enums are challenging. 
- while they are terse, they are so terse that it makes maintenance and
extension error prone. 
    - adding new constants w/o adjusting the switch statements or internal
    code can result in silent semantic bugs
    
The obvious/brute force/naive solutions all introduce boilerplate which
decreases readability and makes code more brittle. 

### SOLUTIONS (STRATEGY ENUM)
(see ConstantSpecificMethodImplementation.PayrollDayStrategyEnum)
- the danger or lack of safety from ConstantSpecific Method Implmentation
paradigm is that there is no direct way to force new constants to 
implement the methods (or to adjust the methods to accommodate the new
constant)

- the best way to achieve safety is to FORCE the implementation of the
method on creation of a new constant. 

STRATEGY ENUM
- we achieve this through a STRATEGY ENUM, which is essentially a 
nested enum type that follows the spirit of the GOF Strategy Pattern.
- this removes the ConstantSpecificMethodImplementation altogether
- boilerplate is minimized to single instances
-  no switch statement

The tradeoff is terse/conciseness for safety and flexibility

## SWITCH STATEMENTS w/ ENUMS
DON'T
- use switch statements on enums for implementing constant-specific 
behavior on enums

DO
- use switch statements on enums for AUGMENTING enum types with 
constant-specific behavior. 

NOTE: these methods are NOT included in the Enum, they'll live
somewhere else. 
    
    EX:
        remember our Operation Enum? 
        - let's say it was a third party Enum (or even our own!)
        and it was missing a method that returns the inverse of
        the mathematical operation we want (as use case that we 
        particularly need for some reason or another). 
        
        We can do this...
        
        public static operation inverse(Operation operation) {
            switch(operation) {
                case PLUS:
                    return Operation.MINUS;
                case MINUS:
                    return Operation.PLUS;
                case TIMES:
                    return Operation.DIVIDE;
                case DIVIDE:
                    return Operation.TIMES;
        
                default:
                    throw new AssertionError("Unknown: " + operation);
            }
        
        }

