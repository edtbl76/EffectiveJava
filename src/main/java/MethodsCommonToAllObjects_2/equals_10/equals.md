# Item 10: Obey the general contract when overriding equals

## WHEN NOT TO OVERRIDE
If you elect NOT to override equals(), then you are suggesting
that each instance of the class is equal only to itself

1. i.e. Each instance of the class is inherently unique
    - this is true for any class that represents "active entities"
    rather than values 
    - EX: Thread
1. there is no need to provide a "logical equality" test
    - ex. java.util.regex.Pattern doesn't really need an
    equality test, because it isn't necessary functionality
1. a superclass has already overridden EQUALS and that override is appropriate for the subclass as well.
    - examples (Collections)
        - Set -> AbstractSet
        - List -> AbstractList
        - Map -> AbstractMap
1. The class is private/package-private 
    - this means that equals() will never be invoked
    - NOTE: for true risk adversity, you can override equals() to prevent accidental invocation.
    
    
    EX:
        public boolean equals(Object o) {
            throw new AsertionError(); // Method never called
        }
        
## WHEN TO OVERRIDE
When a class has the concept of LOGICAL EQUALITY that differs from object identity <br>
    && <br>
the superclass hasn't already overriden it. 

VALUE CLASSES
- this is a simple class that represents a value
    - i.e. Integer, String
    - perfect use case for equals() method.

Some VALUE CLASSES don't need to override equals()
- any class that uses instance control to ensure that at most one object exists w/ each value
    - Enum Types are a good example. 
    - for these classes LOGICAL EQUALITY == OBJECT IDENTITY

## BENEFITS
- satisfies development expectations by fulfilling general contract
- enables instances of objects to serve as map keys/set elements with PREDICTABLE, DESIRED behavior

## THE GENERAL CONTRACT (java.lang.Object#equals) 

equals() implements an EQUIVALENCE RELATION
- REFLEXIVE
    - for any NON-NULL reference value x
        - x.equals(x) must return true
- SYMMETRIC
    - for any NON-NULL reference values x and y
        - x.equals(y) must return true, if and only if y.equals(x) returns true
- TRANSITIVE
    - for any NON-NULL reference values x, y and z
        - if x.equals(y) returns true and y.equals(z) returns true 
            - then x.equals(x) must return true
- CONSISTENT
    - For any NON-NULL reference values, x and y, 
        - multiple invocations of x.equal(y) must consistently return true or false, provided
        that no info used in the equals() comparisons has been modified
- NON-NULLITY (unofficial name)
    - For any NON-NULL reference valuex, 
        - x.equals(null) must return false
        
## The Transitivity Problem
"There is no way to extend an instantiable class and add a value 
component while perserving the equals contract"
- (Unless you are willing to forego the benefits of Object Oriented
abstraction.)

EXAMPLE IN THE WILD
- java.sql.Timestamp extends java.util.Date 
    - it adds a nanoseconds field. 
    - the equals implementation violates both symmetry and transivitiy

NOTE: DO NOT MIX Date and TimeStamp. 

### LISKOV SUBSTITUTION PRINCIPLE
Any important property of a type should also hold for all its subtypes so
that any method written for the type should work equally well on its subtypes. 
- This means that any subclass of a class, is in fact an instance of 
the class and should behave like it. 

### THE WORKAROUND
- By Using Object Composition, you can get around this in order to
prevent the unpredictable behavior associated by breaking transivity
via extension and adding a value. 
- However, we're also no longer using inheritance.

### MUTATION AND CONSISTENCY
Consistency largely deals w/ mutation
- mutable objects can be equal to different objects at different times
while immutable objects can't. 
- if objects/fields should be immutable, then the equals method
should enforce this restriction

NOTE: Regardless of mutability, do NOT write an equals() that
depends on unreliable resources. 

EXAMPLE IN THE WOLD:
- java.net.URL has an equals method that relies on the comparison of IP address of the hosts
associated w/ the URLs. 
    - translating hostname to IP requires network access
    - there is no guarantee that its going to yield the same results over time
    - VIOLATES CONSISTENCY
    
BEST PRACTICES
- equals() should only perform DETERMINISTIC computations on MEMORY-RESIDENT objects


## BEST PRACTICES: equals() 
1. use '==' operator to check if the argument is a reference to this
object
    - if yes, return true. 
    - this is a PERFORMANCE OPTIMIZATION that avoids wasting resources
    on expensive comparisons that are going to fail
    
1. use 'instanceof' operator to check if the argument has the correct type
    - if no, return false
    
1. cast the argument to the correct type
    - because we preceded this w/ the 'instanceof' test it
    is guaranteed not to throw a ClassCastException

1. for each "significant" field in the class, check if that field of
the argument matches the corresponding field of this object
    - if (and only if) ALL tests succeed, return true
    - else return false
    - DO NOT compare fields that are NOT part of an object's logical state
        - EX: lock fields used for sync operations.

1. ALWAYS override hashCode() when you override equals()

1. DON't outsmart yourself. Just follow the contract

1. Don't substitute another type for Object in the signature. 
    - remember, you are Overriding java.lang.Object. 
    
1. if you don't need it... don't touch it.

### COMPARISON RECOMMENDATIONS for fields in an object

- use == for primitive types (other than float/double)
    - use Float.compare(float, float) for floats
    - use Double.compare(double, double) for doubles
        - floating point types require differentiation due to the NaN value
        - equals() will work, but it uses autoboxing on every comparison
            - SAS (Slow as Shit)
- for object reference fields, call 'equals()' recursively
- for arrays, use the above guidelines per element
    - if the elements of the array are significant use Arrays.equals
- for fields that allow null
    - use a precheck 'Objects.equals(Object, Object)'
    
### CANONICAL FORM
- in the event that field comparisons are particular complex (and 
expensive) its recommended to store a canonical form of the field for
performing less costly comparisons. 
    - this is easier to do with IMMUTABLE fields
    - if the object can change, you have to keep the canonicals 
    updated. 
    
### PERFORMANCE
- equals() performance is usually impacted by the order of comparisons
    - start with the fields that are MOST likely to differ or
    less expensive to compare
        - i.e. check the things that are most likely to return false
        first, so we don't waste expensive checks on a comparison that
        is going to fail.
- you don't need to compare DERIVED FIELDS
    - but this might improve performance by short circuiting more
    expensive comparisons
    - (if the derived fields are able to SUMMARIZE logical state of
    the object, then checking those fields can determine whether or
    not it is worth the expense of finishing the other checks )
    

### UNIT TESTS
- That's right. When you are done, ask three questions
1. is it symmetric? 
    - prove it with a test
1. is it transitive? 
    - prove it with a test
1. is it consistent? 
    - prove it with a test



