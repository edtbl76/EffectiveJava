# Item 4: Enforce noninstantiability w/ a private constructor

## Consider Container Classes for Static Methods/Fields
- considered an anti-pattern because it avoids thinking in terms of objects

### PROS
- used as a utility class to group related methods on primitive values/arrays
    - EX: java.lang.Math, java.util.Arrays
- used as a companion class to group static methods (like factories) for objects
that implement some interface (i.e. in a framework)
    - EX: java.util.Collections
- used to group methods on a final class (because final classes can't be subclasses)

In all three cases, we want access to the methods inside the utility/container class, 
rather than the class as a whole. 
- making the class abstract doesn't enforce noninstantiability, because abstract classes can
be extended, and those subclasses can be instantiated.
- if no constructor is provided, then a default constructor is generated. 

### SOLUTION
- providing a private constructor prevents a default constructor from being 
generated. 
    - private constructors are inaccessible outside the class, therefore
    the class itself can't be instantiated. 
    - its usually recommended to provide a comment to avoid confusion
    - prevents subclassing as well, because all constructors must invoke a
    superclass constructor (explicitly or implicitly)


    EX:
    
        public class Utility class {
            private UtilityClass() {
                throw new AssertionError(); 
            }
        }


    