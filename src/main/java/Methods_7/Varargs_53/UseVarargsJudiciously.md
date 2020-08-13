# Item 53: Use Varargs Judiciously

## Varargs Methods (Variable Arity Methods)
methods that accept 0+ arguments of a single specifed type. 
- this works by creating an array initialized to the size of the
number of arguments provided at the "call site"
- the arg values are then stored in that array and passed to the
method. 

Varargs wer originally designed for printf and reflection (Item 65)

    
    EX 1: basic varargs
    
        static int sum(int... args) {
            int sum = 0;
            for (int arg : args) {
                sum += arg;
            }
            return sum;
        }


### Varargs with 1+ arguments?
This demonstrates THE BAD WAY

    EX: 
        
        static int min(int... args) {
            if (args.length == 0) {
                throw new IllegalArgumentException("Too few args");
            }
            
            int min = args[0];
            for (int i = 1; i < args.length; i++) {
                if (args[i] < min) {
                    min = args[i];
                }
            }
            return min;
        
        }    
        
WHY IT SUCKS
- this fails at runtime rather than compile time
- It is ugly
    - explicit validity check on args
    - can't use a for-each loop unless we initialize min to 
    Integer.MAX_VALUE

### Varargs with 1+ arguments. (Period)
(THE BETTER WAY) 

    EX: 
    
        static int min(int firstArg, int... remaining) {
            int min = firstArg;
            for (int arg " remaining) {
                if (arg < min) {
                    min = arg;
                }
            }
            return min;
        }
        
THIS IS BETTER
- it is more readable

## OVERLOADING + VARARGS
If performance is very critical, Varargs can be prohibitively 
expensive. The workaround is to use overloading. 
1. determine (estimate) the number of parameters that will needed
a majority of the time. 
    1. (let's say 4 for giggles)
1. declarate N + 1 overloadings of the method with a telescoping 
method signature. 


    EX: 
        public void myMethod() {}
        public void myMethod(int arg1) {}
        public void myMethod(int arg1, int arg2) {}
        public void myMethod(int arg1, int arg2, int arg3) {}
        public void myMethod(int arg1, int arg2, int arg3, int arg4) {}
        public void myMethod(int arg1, int arg2, int arg3, int arg4, int... rest) {}
        
Assuming a narrow majority, we'll cut at least 49% of the cost of array allocation and initialization.          

## BEST PRACTICES/CONCERNS
- every invocatinon of a varargs method causes array allocation and
intitialization
    - performance hit. Can be expensive.