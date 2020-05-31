# Item 35: Use instance fields instead of ordinals.
All enums have an ordinal() method that returns the numerical position of
each enum constant in its time. 

A naive use of this is to leverage the ordinal to associate a value
with the constant.

Don't do this:

    Ex:
    
        public enum MusicEnsemble {
            SOLO, DUET, TRIO, QUARTET, QUINTET;
        
            public int numberOfMusicians() {
                return ordinal() + 1;
            }
        }
        
I certainly hope that the reasons why this is bad go without saying:
- we've established that this kind of pattern prevents a contract 
between the constant and the method. If I add a constant called SUCK_IT
at the beginning, it throws off every value returned..

## InstanceFields
NEVER derive a value associated w/ an enum constant from its 
ordinal. 
- use instance fields

    EX: 
        
        public enum MusicEnsemble {
            SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5);
            
            private final int numberOfMusicians;
            
            MusicEnsemble(int players) {
                this.numberOfMusicians = players;
            }
            
            public int numberOfMusicians() {
                return numberOfMusicians;
            }
        }
       
## Actual Spec Notes about ordinal()

    "Most programmers will have no use for this method. It is designed
    for use by general-purpose enum-based data structures such as
    EnumSet and EnumMap"
    
- words to live by