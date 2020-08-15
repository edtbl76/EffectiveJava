# Item 57: Minimize the scope of local variables. 

## WHY?
- increases readability of code
- better readability = better maintainability
- less error-prone

## Variable Declaration 
Declare variables where they are first used
- this ensures that the variable is provided near the code that
implements it. 

### Header Block of Variables
Avoid the "Header Block of Variables" (that big chunk of code
that is like a "registry" of variables at the head of a block)
- distracting
- disassociated from usage. 
- premature declaration can lead to scope starting too early
    - (it is also possible for it to end too late)
- error-prone
    - accidental PR might result in var being used before it was 
    intended
    
### Prefer Initializers
- Strive to declare variables when you have enough info to intialize them
- EXCEPTION
    - try/catch statements
        - if a var is init'd to an expression that can throw a checked exception
            - init it in a try block
            - allow enclosing method to propagate the exception
            
### Method Size/Focus
- Keeping methods small (usually) means fewer variables, which means that each variable will 
be easier to read, and easier to maintain 
    - scope is clearer to other devs
    
- Keeping methods FOCUSED (i.e. on ONE activity: ONE METHOD) ensures that we don't have variables in the scope of
one activity bleeding into the scope of another (where an inadvertent change to one could lead to
the dreaded "Undesirable Behavior")
 
### Loops (For/ForEach > While)
- Classic For
    - this is a slight twist on a classic for loop, where we provide TWO initialization variables. 
    - this is a case that a ForEach can't handle, that a ClassicFor can. 
    - NOTE: (Due to obscurity, some might argue this is hard to read.)
    
    
    EX:
    
        for (int i = 0, n = expensiveComputation(); i < n; i++) {
            ..// Do something with i.
        }
    
- ForEach example
    - (Prefer this over classic)
    
    
    EX:
    
        for (Thing thing: things) {
            ... // do unto thing.
        }
        
        
- Iterator example

    
    Ex:
    
        // NOTE: This is a classic for loop using an iterator as the mechanisms
        for (Iterator<Thing> iterator = things.iterator(); iterator.hasNext(); ) {
            Thing thing = iterator.next();
            ... // Do unto thing and iterator. 
        }

- For vs. While (Why We prefer the former)
1. While loops don't support loop variables
    - in cases, where we don't need a variable to exist beyond the scope of a loop, for loops are
    preferred because we get locality for free. 
1. Readability
    - for-style loops tend to be shorter and easier to read/maintian
1. "The Copy and Paste" scenario:
    - (First Example - WHILE ) 
        - this is a copy-and-paste error that results in a semantic bug, because the "iterator" var is still in scope.
    - (Second Example - FOR) 
        - this is another copy-and-paste error, but it won't even compile, because the variable is OUT of scope. 
    


    EX: (While Loop) 
        
        Iterator<Thing> iterator = things.iterator();
        while (iterator.hasNext()) {
            doThing(iterator.next());
        }
        
        Iterator<Thing> iterator2 = things2.iterator();
        while (iterator.hasNext()) {    // BUG!!!!! This is semantic, and will occur at RUNTIME. 
            doOtherThing(iterator2.next());
        }
        
        
    EX: (For Loop)  This is the preferred approach. We want errors caught as EARLY as possible. 
        Compile Time is sooner than Runtime!
        
        for(Iterator<Thing> iterator = things.iterator(); iterator.hasNext();) {
            Thing thing = iterator.next();
            // ... do something w/ thing and iterator
        }
        
        // This isn't going to compile due to the "iterator.hasNext()" copy/paste error. 
        for(Iterator<Thing> iterator2 = things2.iterator(); iterator.hasNext();) {
            Thing thing2 = iterator2.next();
            // ... do something w/ thing and iterator
        }
        
        
        
        
        
