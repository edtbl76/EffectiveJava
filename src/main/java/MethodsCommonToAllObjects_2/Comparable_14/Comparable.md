# Item 14: Consider Implementing Cloneable

NOTE: this isn't a method defined in Object. It is the ONLY
method in the Comparable interface.


    public interface Comparable<T> {
        int compareTo(T t);
    }

## ATTRIBUTES 
- similar to equals(), but also permits order comparisons
- generic
- when a class implements Comparable it establishes a NATURAL ORDERING
for its instances
- simplifies several operations on collections of Comparable Objects
    - sorting
    - searching
    - computing extreme values
    - maintaining automatic collections of Comparable objects.


The example below demonstrates one of the advantages derived from the
fact that String implements Comparable. 


    EX:
    
        This will print an alphabetized list of CLI args w/ 
        dupes removed. 
        
        public class WordList {
            public static void main(String[] args) {
                Set<String> str = new TreeSet<>();
                Collections.addAll(str, args);
                System.out.println(str);
            }
        }

## SORTING
Sorting an array of objects that implement Comparable is as easy as

    Arrays.sort(MyComparableObject);

## ADVANTAGES
- allows an implementing class to interoperate w/ a myrid of 
generic algorithms and collection impls that depend on Comparable. 
    - HUGE bang for your buck. 
    - most of the VALUE CLASSES in the java platform libs
    - most ENUM types.
    
    
## WHEN TO USE
- if you are implementing a VALUE CLASS w/ an obvious natural ordering
, you should implement Comparable
    - EX: 
        - alphabetical
        - chronological
        - numerical
        
## GENERAL CONTRACT
(Similar to equals)

1. Compares THIS object w/ specified object for ORDER. 
    - returns NEGATIVE, ZERO or POSITIVE integer as object is respectively
    LESS THAN, EQUAL TO or GREATER THAN the specified object.
    
1. throws ClassCastException if specified object's type prevents it
from being compared to THIS object. 

1. the implementor must ensure SYMMETRY
    - sgn(x.compareTo(y)) == -(sgn(y.compareTo(x))) for all x and y
    - implies:
        - x.compareTo(y) throws exception if and only if y.compareTo(x)
        also throws exception

1. the implementor must ensure TRANSITIVITY
    - (x.compareTo(y) > 0 && y.compareTo(x > 0) implies 
    x.compareTo(z) > 0
    
1. it is recommended (BUT NOT REQUIRED)
    - (x.compareTo(y) == 0) == x.equals(y)
    - NOTE: any class that impls Comparable and violates this condition
    should indicate this with the following mesage
        - "Note: This class has a natural ordering that is inconsistent
        with equals"


NOTE: The notation sgn(expression) designates the mathematical 
SIGNUM function, which is defined to return -1, 0 or 1 according to
whether or not the expression is negative, zero or positive. 

## COMPARISON TO EQUALS
| equals() | compareTo() |
| --- | --- | 
| Deals with global equivalence | doesn't have to work across objects of different types (it can throw ClassCastException) |
| failure to comply w/ general contract can break other classes that depend on it | <--- |
| general contract includes restrictions concerning REFLEXIVITY, TRANSITIVITY and SYMMETRY | <--- |
| there is no way to extend an instantiable class w/ a NEW VALUE component while comparing the equals general contract, unless you are willing to forgo the value of OOP abstraction | there is no way to extend an instantiable class w/ a NEW VALUE component while comparing the compareTo general contract, unless you are willing to forgo the value of OOP abstraction |
| workarounds are ignoring Liskov Substitution principle or using object composition | <--- |

The last "provision" of compareTo is really just a recommendation. 
- It SHOULD be consistent w/ equals(), but if it isn't, we should let
others know that it isn't. 

## CONSISTENCY WITH EQUALS
The reason this is a strong recommendation is that the general contracts
of common Collections interfaces (i.e. Collection ,Set and Map) are
defined in terms of equals, but sorted collections use the equality 
test imposed by compareTo INSTEAD of equals. 

Classes inconsistent:
- BigDecimal


    EX: 
       An empty HashSet consisting of BigDecimal instances 
       that adds 
            1. new BigDecimal("1.0") and
            2. new BigDecimal("1.00") 
       will have 2 elements, because these are unequal when 
       comparing the equals method. 
       
       HOWEVER: 
       
       performing the same comparison using a TreeSet instead of
       a hashSet will have only ONE element, because TreeSet uses
       compareTo (where these 2 instances are considered equal) 
