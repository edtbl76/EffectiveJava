Item 50: Make defensive copies when needed

### SAFE LANGUAGE
In the absence of native methods, it is immune to:
- buffer overruns
- array overruns
- wild pointers
- other memory corruption errors that plague UNSAFE languages

PROS:
- know w/ certainty that invariants will be maintained regardless
of what happens elsewhere in the system
    - This does NOT hold true for other classes that will USE your
    code.

UNSAFE LANGUAGES
- C/C++

## DEFENSIVE CODING
"You must program defensively w/ the assumption that clients of
your class will do their best to destroy its invariants."

### Attack Internals Number One 
(Example) Look at the code in BrokenImmutableTimePeriodClass.Period
- It appears that this code is immutable
    - invariant -> start of a period doesn't follow its end
- EXPLOITABLE!
    - the Date object is mutable, so we can do the following
    
    
    ATTACK internals of the Period instance
    
    Date start = new Date();
    Date end = new End();
    Period period = new Period(start, end);
    end.setYear(10000);     <-- Modifies Internals of period
    
    
### Java 8 Solution
- use an immutable replacement for Date
    - EX:
        - Instant
        - LocalDateTIme
        - ZonedDateTime
- NOTE: 
    - Date is considered obsolete and should no longer be used
    
### Modern Solution - Defensive Copies
- it is essential to make a defensive copy of each mutable parameter to
the constructor
    - use the copies as components of objects that intend to use
    the mutable parameters in lieu of the originals.
    
### Defensive Copies and Parameter Validation
- defensive copies are made BEFORE checking the validity of the parameters
- validity check is performed on the copies rather than the originals
    - this eliminiates the "Window of Vulnerability" that is created
        - time the parameters are checked to 
        - time they are copied
    - (AKA TOCTOU attack in security)
        Time-Of-Check/Time-of-use
    
### What about clone()
- Since java.util.Date is a non-final class, clone() isn't 
guaranteed to return a Date object. 
- it could return an untrusted subclass
    - this is actually a vulnerability that could allow an attacker
    to create a subclass w/ malicious code 
    - Attacher could record a reference to each instance in a 
    private static list
        - gives hacker free reign over ALL the instances
        
        
### Defensive Copies and Cloning
- DO NOT USE THE CLONE METHOD to make defensive copies of a parameter
    - whose TYPE is subclassable by untrusted parties
    - "final" types are technically safe, because they can't be
    subclassed. 
    
### Attack Internals Number 2
 
    SECOND ATTACK on internals
    
    Date start = new Date();
    Date end = new Date();
    Period period = new Period(start, end);
    period.end().setYear(10000);    <--- Modified internals AGAIN!

See FixAttackInternals2 for the solution! <br>
(Hint we refactor the accessors to return defensive copies
of internal fields)

### Approach
- defensive copying is NOT just about immutable classes
- any method/constructor that stores a reference to a client-provided
object in an internal data structure, ask the following questions:
    
    
    Is the client-provided object potentially mutable? 
    
    If so, 
        can the class tolerate a change in th eobject after it 
        has been entered into the data structure? 
        
    If not, 
        you MUST defensively copy the object and put the
        copy in the data structur in stead of the original.
        
    (USe the same thought-process for defensive copying of 
    internal components prior to returning them to clients
    
### Transfer of Control
- some classes contain methods/constructors whose invocation indicate
a transfer of control to the client
    - these classes CAN NOT defend against malicious clients
    - SOLUTION 1
        - there must be a mutual trust between a class and its client
    - SOLUTION 2
        - the side effect of transfer of control (i.e. damaging
        class invariants) would only harm the client. 
            - EX: WRapper (Item 18) 

### Best Practices
- The main focus of defensive copies is to ensure that fields are truly
and completely encapsulated within an object
    - make defensive copies of accessors that return values
    - make defensive copies when assigning parameters.
- avoid using clone
    - use static factory or copy an instance (See Item 13)
- non-zero length arrays are ALWAYS mutable (see Item 15)
    - ALWAYS make defensive copies of internal arrays before 
    returning it to a client
    - (alt) return an immutable view of an array
- when possible, use immutable objects as components of your 
objects


### When NOT to use
- sometimes there is a performance penalty associated w/ 
defensive copying. 
    - is the juice worth the squeeze? 

WORKAROUND
- If class and client are part of the same package, then the
client class is "trusted" not to modify an internal component
    - defensive copying can be removed if the cost is prohibitive
    
- HANDOFFS
    - this is a case when client/class are NOT in the same package, 
    but it might not be appropriate to use defensive copying
    - the methods/constructors that are invoked indicate that
    explicit handoff of the object ref'd by a parameter
    - This must be CLEARLY documented that the client PROMISES
    not to modify the object directly
    