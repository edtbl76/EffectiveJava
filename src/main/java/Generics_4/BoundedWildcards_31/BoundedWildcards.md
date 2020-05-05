# Item 31: Use bounded wildcards to increase API flexibility

## RECAP
Parameterized types are INVARIANT
- for any 2 types (Type1 and Type2), List<Type1> is neither a subtype
or supertype of List<Type2>


    EX:
        List<String> is NOT a subtype of List<Object>
        
        
