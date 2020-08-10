# Item 49: Check parameters for validity

## Input Validation
Most methods and constructors require constraints on the values passed into their parameters
- EX: non-negative, non-null object references
- constraints should be clearly documented (in the Javadoc)
- any constraints should be evaluated at the beginning of the method body using checks. 
    - Detect errors ASAP
    - "Fail Fast!"
    
Basic Steps
1. document restrictions to inputs
2. document exceptions to be thrown if restrictions are violated
3. enforce it!
       
### Consequences of Poor Failure Management
Errors/Invalid data should be checked ASAP. 
- the longer an error remains unidentified, the more likely it is that it will go completely
undetected. 
- the further downstream an error penetrates into happy path, the harder it is to trace back
to source. 

General Failure Modes 
- fail w/ a confusing/misleading exception
- (worse) return normally w/ the wrong result (safety failure)
- (worst) -> violation of Failure Atomicty
    - return normally, leaving other objects/services in a compromised state causing an error
at some unrelated point in code at some undetermined time in future

## Use @throws tag on public/protected methods
- documents exception that will be thrown if a parameter values restriction is violated
    - EX:
        - IllegalArgumentException
        - IndexOutOfBoundsException
        - NPE


    EX:
    
        /**
            Returns a BigInteger whose value is (this mod m). This method
            differs from the remainder method in that it always returns a non-negative
            BigInteger.
            
            @param m is the modulus, which must be positive 
            @return this mod m
            @throws ArithmeticException if m is less than or equal to 0
        */
        public BigInteger mod(BigInteger m) {
            if (m.signum() <= 0) {
                throw new ArithmeticException("Modulus <= 0: " + m);
                // do work
            }
        }
       
- NOTE: The javadoc doesn't mention anything about NPE if n is null. 
    - the method does this when we call m.signum()
    - this IS documented in the class-level doc for BigInteger. 
    - this makes sense, because it would be excessive to document this for 
    every method that uses a BigInteger
- this can be combined w/ annotations (i.e. @Nullable)
    - indicates that a particular parameter may be null
    
## Range Checking
- Objects.checkFromIndexSize()
- Objects.checkFromToIndex()
- Objects.checkindex()

Limitations
- these don't allow you to specify your own exception message (like null-checking method)
- designed only for use w/ list and array indices
- doesn't handle closed ranges

## Unexported methods (i.e. nonpublic)
we have 100% control over the circumstances in which this method is called. 
- ALWAYS validate input. 

### Assertions (I'm not a big fan of this...)
Assertions can be used to check parameters on nonpublic methods
- claims that asserted conditions WILL be true, regardless of how the package is used by
clients.
- throw AssertionErrors on failure
- unlike normal validity checks they have NO effect (or cost) unless they are enabled. 
 
    EX:
    
    (Helper function for recursive sort) 
    private static void sort(long a[], int offset, int length) {
        assert a != null;
        assert offset >= 0 && offset <= a.length;
        assert length >= 0 length <= a.length - offset;
        // do work.
    }

## Validation of Stateful/Stored Parameters
Validation of values that are stored for later use prevents the case where we "fail on access". 

Example:
- let's say we have a Static Factory method that takes an int[], but returns a List<Integer>. 
- if we pass in a null value w/o a check, we won't get an NPE until the calling code
attempts to USE the return value. 
    - this is later (maybe much moreso) than the point at which we passed in the invalid data. 
    - (i.e. )
    
## BEST PRACTICES
- Use Objects.requireNonNull() instead of performing "manual null checks anymore."
       
        EX:
        
            this.strategy = Objects.requireNonNull(strategy, "strategy");
            
    - NOTE: This can also ignore return values and be used as a "free-standing null check" where that
suits your needs. 
- Use range-checking methods:

        
