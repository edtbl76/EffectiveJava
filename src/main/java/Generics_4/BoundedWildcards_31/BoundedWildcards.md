# Item 31: Use bounded wildcards to increase API flexibility

The purpose of bounded wildcards are to promote flexibility in design, but as stated, in a
"bounded" fashion.
- accept the types you should accept
- reject everything else.

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

## Bounded Wildcards and API/Clients
If a user has to think about wildcard types, then there is usually something wrong with
the API
- good API design promotes simplicity. 

## Bounded WC and TypeParameters
(see NoBoundedWC#Max for the initial example and BoundedWC#Max for
the changes) 

NOTE: This uses a Comparator<T> , which is ALWAYS a consumer. 
Per PECS above, this means that Comparator should typically use
a bounded wc type parameter, ie.. 
Comparator<? super T>

### WHY? 
Take the code below as an example. 

    List<ScheduledFuture<?>> scheduledFutures = ...

This would be excluded by the original example (NoBounded#Max), but
is permitted by (BoundedWC#Max)
- It fails because ScheduledFuture doesn't implement Comparable\<ScheduledFuture>
- It's a subinterface of Delayed, which extends Comparable\<Delayed>

This isn't specific enough for the first comparison (original example)
to succeed. 

Generically, the advantage (in this case) is that a bounded WC 
(when used w/ type parameters) supports types that don't implement 
Comparable/Comparator directly but extend a type that does.

## UNBOUNDED TYPE PARAMETER VS. UNBOUNDED WILDCARD

UNBOUNDED TYPE PARAMETER
    
    public static<E> void swap(List<E> list, int i, int j);
    
UNBOUNDED WILDCARD

    public static void swap(List<?> list, int i, int j);
    
The second example (UNBOUNDED WC) is considered preferable. 
- You can pass in ANY list, and the method performs work on it. 
- there are no parameter to worry about. 

GENERAL RULE: 
- if a type parameter appears only once in a method declaration, 
replace it with a wildcard. 
- if it's an unbounded type parameter, replace it with an 
unbounded wildcard. 
- same goes for bounded.
 
## CAPTURING WILDCARD
see Swap code for example.
- there is a problem w/ the previous example of the unbounded
wildcard. 
    - List<?> only takes null as a value, so any attempt to 
    read/write non-null items into the list will fail as a compile
    time error. 

The way to solve this is to create a private method called by the
method that uses the wildcard. 
- the public method has a wildcard
- the private method has a type parameter, but is more complex, 
so we abstract it from the user/API

The purpose of this code is a tradeoff. 
- the wildcard is more customer-friendly, but we need the
type parameter piece for the whole solution to work properly. We
deem it too complicated so we hide it behind the wildcard. 

