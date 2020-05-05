# Item 31: Use bounded wildcards to increase API flexibility

## RECAP
Parameterized types are INVARIANT
- for any 2 types (Type1 and Type2), List<Type1> is neither a subtype
or supertype of List<Type2>


    EX:
        List<String> is NOT a subtype of List<Object>
        
        
## PROBLEMS
(See NoBoundedWC.Stack#pushAll)
(See NoBoundedWC.Stack#popAll)

- Compiles? Yes.
- It only works if the element type of the Iterable source matches
the type of the Stack EXACTLY


    EX: (Demonstrate Failure)
    
        Stack<Number> stack = new Stack<>();
        Iterable<Integer> iterable = ...; 
        stack.pushAll(iterable);
        
        RESULT:
            Stack.java:<lineNo>: error: incompatible types: Iterable<Integer>
            cannot be converted to Iterable<Number>
                stack.pushAll(iterable);
                              ^


## BOUNDED WILDCARD TYPE
(See BoundedWC.Stack#pushAll)
(See BoundedWC.Stack#popAll)

- Compiles? Yes.
- The client code that previously failed will now work. (Keep in mind
this isn't a cure-all. There are still constraints, but we are
working towards ROBUSTNESS by being able to focus on flexibility.)
        
NOTE the differences in both cases. 
- pushAll() required us to extend the Collection 
    - Collection<? extends E>
- popAll() required us to have a collection of some supertype of E
    - Collection<? superE>
    
    
## The GET AND PUT PRINCIPLE
For MAXIMUM flexibility use WC types on INPUT parameters that
represent PRODUCERS or CONSUMERS
- if an input parameter acts as both, then WC types are useless
    - you require an exact match

PECS RULE
- Producer -> Extends
    - T producer, <? extends T>
- Consumer -> Super
    - T consumer, <? super T>
