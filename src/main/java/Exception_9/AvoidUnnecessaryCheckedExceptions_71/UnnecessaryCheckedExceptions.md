# Item 71: Avoid unnecessary use of checked exceptions

## Benefits of Checked Exceptions
- they FORCE coders to deal w/ problems
    - enhanced reliability
    
- return codes and unchecked exceptions don't provide the same
benefits


## Disadvantages of Checked Exceptions
- code that invokes checked exceptions must handle them
     - catch blocks
     - methods must declare a 'throws' statement, propagating the
     exception outward? 
 - (increased burden on coder)
    - (even moreso in when Streams were impl'd because 
    methods throwing checked exceptions can't be used directlh in streams)
- overuse of checked exceptions can increase the burden unnecessarily

### Factors that increase burden
The burden on the coder increases if it is the ONLY checked exception
- if there are multiple checked exceptions, then the method already 
appears in at least one try block
    - new exception requires at most a new CATCH
- if there is only ONE checked exception
    - then that try block only exists to catch the checked exception
    - incurs all of the costs of try/catch
    - can't use streams API

## Practices to minimize the use of checked exceptions
Checked exceptions are justified IF
1. the exceptional condition can't be prevented by proper use of the API
1. coder using the API can take a useful action once confronted w/ the exception

Unless BOTH of these conditions are met:
- use an unchecked exception

### Thought process
- How will the programmer handle the question? 
- Is the current solution the best that can be done? 


    EX: 
    
        // NO! Don't do this! -> Do NOT throw Errors.
        } catch (TheCheckedExceptiom e) {
            throw new AssertionError();
        }
        
        // This is the best we can do.
        } catch (TheCheckedException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
### Optionals
- easiest way to eliminate a checked exception is to return an optional
of the desired return type
    - instead of throwing an exception, the method just returns an 
    empty optional. 
    
DISADVANTAGE
- method can't return additional info concerning its inability to 
perform the desired task. 
    - Optionals don't have benefits of Exceptions
        - descriptive types
        - special methods to provide addt'l info (Item 70)
        
### Checked -> Unchecked
Checked exceptions can be converted into unchecked exceptions
- break the method that throws the exception into 2 methods
    - first method returns a boolean that indicates whether the
    exception would be thrown 
    
    
    EX: 
    
        // Checked Exception
        try {
            obj.action(args);
        } catch (TheCheckedException e) {
            ... // Handle exceptional condition
        }
        
        // Unchecked Exception (state-tester + unchecked) 
        if (obj.actionPermitted(args)) {
            obj.action(args);
        } else {
            ... // Handle exceptional condition
        }

- both solutions are ugly, but the latter creates a more flexible API
- the refactoring is from Item 69 (state-testing method)
    
WHEN THIS IS INAPPROPRIATE
1. (the state can change during the time that elapses from
    the invokation of actionPermitted and action)
   - object is to be accessed concurrently w/o external sync
   - subject to externally induced state transitions.
1.   it is ALSO inappropriate if the refactoring introduces 
    duplication of code or other performance hits
        