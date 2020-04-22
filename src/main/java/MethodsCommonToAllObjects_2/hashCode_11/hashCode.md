# Item 11: Always override hashCode() when you override equals()

If you don't do this, you violate the GENERAL CONTRACT for hashCode(), 
which will break its functionality when using collections that require
hashing 
- HashMap, HashSet, etc. 


## GENERAL CONTRACT
1. when hashCode method is invoked on an object repeatedly during the
execution of an app
    - it must consistently return the same value as long as no data used
    in equals() comparisons is modified. 
    - this value does NOT have to remain consistent from one execution of
    an application to another. (i.e. it's app specific)

1. if 2 objects are equal according to equals(Object) method
    - then calling hashCode on the two objects must product the same
    int result

1. if 2 objects are unequal according to the equals(Object) method
    - then it is NOT required that calling hashCode on each of the objects
    must produce distinct results
        - BUT, doing so would iprove the performance of hash tables
        

Item 2 is the key
- equal objects must have equal hashCodes
    - if you override equals() but you don't override hashCode(), 
    then this property is most likely going to be violated.
    
Ideally, a good hash function should "distribute any reasonable collection
of unequal instances uniformly across all int values"

## WORST HASHCODE EVER
This is the worst hash code ever

    
    EX:
        @Override
        public int hashCode() {
            return 42;
        }
        
Yes, this works. However it works because EVERY object will have the
same hashCode. 
- every object ends up in the same bucket, therefore the hash tables
are reduced to linked lists. 
    - this causes programs intended to run in linear time to run in 
    quadratic time (i.e. VERY SLOWLY)
    
## Approximating an Ideal Hash Code
1. declare an 'int' variable named 'result'
    - initialize it to the hash code 'c' for the significant field in 
    the object. 
        - (significant fields are those that affect equals comparison)

1. for every remaining signficant field 'f' in object
    1. (COMPUTE STEP) compute 'int' hash code 'c' for fields
        - if field is primitive, compute Type.hashcode(f)
            - where Type is boxed primitive class corresponding to f
        - if field is object reference
            - and equals() is compared via recursive equals() invocations
                - recursively call hashCode(f)
            - if complexity dictates use of CANONICAL FORM for field
                - invoke hashCode(f) on canonical
            - if field is null
                - use some constant (0 is traditional)
        - if field is an array
            - treat each significant as a separate field 
                - (compute hashCode for each significant element by applying
            rules recursively and combine using COMBINE step)
            - if array has no significant elements
                - use a constant (preferably NOT 0)
            - if ALL elements are significant
    1. COMBINE STEP
        - combine hash code (c) computed in the computation steps into
        a result
            - result = 31 * result + c;
                - result = (result << 5) - result + c (alternative)
        - NOTE: 31 is an odd prime
            - using primes reduces the chance of collisions 
        - NOTE: the alternative 
1. return 'result'

## WHEN YOU ARE DONE...
- Ensure that each equal instance has an equal hash code
- verify this via unit tests (better yet, write the tests first!)
- DERIVED FIELDS can be omitted from hashCode computation
- fields that aren't part of equals() MUST be omitted from hashCode()
    - violates second property of hashCode() GENERAL CONTRACT
- the multiplication in the COMBINE step makes the result dependent on
the order of the fields
    - creates better hash function if class has multiple similar fields
    - (if this were omitted, all ANAGRAMS would have identical hash codes)
    
## OPTIMIZATIONS
for immutable classes with signiciant hashCode computation
- the hashCode isn't going to change (because the object can't) so it makes sense to cache the hashCode 
in the object instead of recalculating it per request. 

if the objects are primarily used as hash keys
- hashCode should be calculated at creation time 

if objects are used on-demand (and will likely never be used for hash keys)
- it might make sense to defer the cost of creation by lazy loading it the first time hashCode() is called
- (and then caching it in the object)

NOTE: lazy initialization requires additional work to ensure thread-safety.

DO NOT under any circumstances omit significant fields from the hash code computation to improve performance
- yes, it will run faster
- yes, collisions will increase considerably, reducing the performance of the hash tables, eventually making them 
unusable.
    - LINEAR -> QUADRATIC = SLOW
    
DO NOT provide a detailed explanation for the value returned by hashCode
- your description will become an unofficial contract for those who use it, and they will rely on that. 
- while documentation is nice, in this case it is a trap that prevents you from improving/modifying the hash function 
in the future. 