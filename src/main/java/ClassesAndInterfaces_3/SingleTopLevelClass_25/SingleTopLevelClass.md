# Item 25: Limit source files to a single top-level class.

## RISKS OF ASSOCIATING MULTIPLE TOP-LEVEL CLASSES IN A SINGLE SOURCE FILE
The risks are based on making it possible to provide multiple definitions for a class. 


    EX: 
        Main.java
        
            public class Main {
                public static void main(String[] args) {
                    System.out.println(Utensil.NAME + Dessert.NAME);
                }
            }
        
        Utensil.java
        
            class Utensil {
                static final String NAME = "pan"; 
            }
            
            class Dessert {
                static final String NAME  = "cake";
            }
        
        Dessert.java
    
            class Utensil {
                static final String NAME = "pot";
            }
            
            class Dessert {
                static final String NAME = "pie";
            }
    
    
NOTE: we have the same two classes, doing different things, each declared in two separate
source files. <br>

PLEASE don't ever do this.

This will fail/succeed solely based on the order in which the source files are passed to the
compiler. 
- This is the single most important reason for splitting class per source file. 
- if you absolutely HAVE to put more than one class in a file, it should be some 
form of member class. 
    - Remember that STATIC MEMBER CLASSES are "almost" like having another top-level class
    within the same source file.
    

    EX:
        Using Static Member Classes to solve the ShitShow above
        
        public class Test {
            public static void main(String[] args) {
                System.out.println(Utensil.NAME + Dessert.NAME);
            }
            
            private static class Utensil {
                static final String NAME = "pan";
            }
            
            private static class Dessert {
                static final String NAME = "cake";
            }
        }
