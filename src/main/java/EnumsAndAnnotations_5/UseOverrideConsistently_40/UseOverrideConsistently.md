# Item 40: Consistently use the Override annotation

@Override is the most common annotation provided by java libraries. 
- can only be used on method declarations
- indicates that the annotated method declaration overrides a
declaration in a supertype

Catches bugs!
- Compare BuggyCode.Bigram to FixedCode.Bigram
- It makes code clean/safe, as well as communicates to other devs that 
it is safe. 

RULE:

    Use Override annotation on every method declaration that you 
    believe to be override a superclass declaration. 
    
EXCEPTION:
- if you are writing a non-abstract class that overrides an ABSTRACT
method in its overclass, there is no reason to add the @Override
annotation
    - failure to override/implement abstract methods results in 
    compile time errors. 
- (However you can still do it if you want to.)

## INTERFACES
You may use the @Override method on concrete implementations of methods
defined in interfaces. 
- this is considered best practices especially with the addition of
default methods in interfaces.

