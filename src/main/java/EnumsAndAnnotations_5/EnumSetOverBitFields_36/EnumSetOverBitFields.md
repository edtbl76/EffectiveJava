# Item 36: Use EnumSet instead of bit fields. 
If elements of an enum type are used primarily in sets, the traditional
convention has been to use the "int enum pattern", assigning a different 
power to each constant: 

    EX:
    
        public class Text {
            public static final int STYLE_BOLD          = 1 << 0;   // 1
            public static final int STYLE_ITALIC        = 1 << 1;   // 2
            public static final int STYLE_UNDERLINE     = 1 << 2;   // 4
            public static final int STYLE_STRIKETHROUGH = 1 << 3;   // 8
            
            // Parameter is a bitwise OR of 0 or more STYLE_ constants
            public void applyStyles(int styles) { ... }
        
        }
        
        // it would be called like this:
        text.applyStyles(STYLE_BOLD | STYLE_ITALIC);
        
        
## BIT FIELDS/BIT VECTORS
PROS
- bit fields are nice because they allow you to perform SET operations
(See Set Theory, database design!)
    - e.g. Union, Intersection, etc.
    
CONS
- they have all of the disadvantages of "int enum constants"
- ... and more
    - harder to interpret a bit field when printed as a number
    - no easy way to iterate over elements represented as bit vectors
    - you have to predict the max no of bits you'll EVER need when 
    writing the API, and then choose the type accordingly
        - usually int or long
        - once it's set, you CANT change the width of the vector w/o
        changing the API.
        
## EnumSet
- part of java.util package
    - "efficiently represents a set of values collected from a single
    enum type"
- implements Set interface
    - type safety, interoperability, 
- internally represented as a bit vector
- bulk operations (removeAll(), retainAll(), etc.) are implemented using
bitwise math. 

Most importantly, you are insulated from all of the ugliness 
and error-prone handling of manual bit packing, bit shifting etc. 
- EnumSet does the heavy lifting.


    EX:
        Moving the example from above to EnumSet
        
        
        public class Text {
            public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }
            
            public void applyStyles(Set<Style> styles { .. }
        }
        
        // it would be called like this
        text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));

NOTE: the applyStyles() method takes an instance of SET. 
- even though most clients are probably going to pass in an EnumSet, it
is considered best practices to accept the interface type rather than 
the implementation type. 

### IMMUTABILITY
One disadvantage is that there is no NATIVE Java method that can create an
immutable EnumSet

SOLUTION 1: (Native)
A "Native" solution is to wrap an EnumSet with Collections.unmodifiable()
- this is less concise and there is a performance hit.

SOLUTION 2: (Non-Native)
Google Guava provides immutable Enum Sets
https://guava.dev/releases/snapshot/api/docs/com/google/common/collect/Sets.html#immutableEnumSet-java.lang.Iterable-