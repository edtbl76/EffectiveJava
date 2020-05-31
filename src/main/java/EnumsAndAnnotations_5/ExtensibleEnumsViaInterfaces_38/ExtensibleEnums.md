# Item 38: Emulate extensible enums with interfaces

## Extensibility
It is not possible to have one enum type extend another, by design. 
- it would be confusing that elements of an extension type are also
instances of a base type and not vice versa
- there is no good way to enumerate over all of the elements of a base type
and its extension
- extensibility complicates design/impl aspects


## OPCODES = a use case for extensible enumerated types
Operation codes are enumerate types whose elements represents operations on 
some machine. 
- there are use cases to let users of an API provide their own 
operations
    - by definition this extends the set of operations provided by the API

## Emulating Extensibility
This can be achieved with interfaces, because the interface itself is 
extensible, and it may be implemented by an enum. 
- If we implement an interface for the opcode, then the enum can
implement that opcode. <br>

(See Operation and BasicOperation)
- Operation is our interface
- BasicOperation is the default or base implementation. 

I can't extend the enum, but I CAN extend the interface and use
instances of some new implementation. 
(See ExtendedOperation)

### NOTES
- as long as the API is written to take the interface type (Operation) rather
than the impl, it will support any one of the implementations of that
interface
- Unlike non-extensible enums, we don't have to make the apply() method 
abstract, because it is a member of the interface. 
- DISADVANTAGE is that we only get a single "layer" of extensibility, 
because the enums that implement the interface can't be further extended
    - this also means that impls can't be inherited from one enum type
    to another. 
    - results in some degree of code duplication between enums. 
    - a Static Container/Helper class can resolve this. 


This pattern is used in several Java libraries
- java.nio.file.LInkOption enum type
    - implements CopyOption and OpenOption interfaces
    
    
### IMPL VARIATIONS
Look at Client and ClientV2

Client is more complicated because it uses a BOUNDED TYPE TOKEN, passing
a class from main() to the test method. 
- this is less flexible overall, but it has the advantage of being able to
use EnumSet and EnumMap for the operations if necessary. 

ClientV2 is less complicated as it uses a BOUNDED WC TYPE, passing a 
Collection from main() to the test method. 
- this is slightly more flexible overall, but we are breaking the linkage
with the Enum<> type, so we can't use EnumSet or EnumMap. 
    