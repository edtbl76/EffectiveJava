# Item 59: Know and use the libraries

## The Random Number Example

### Common Implementation of a PRNG

    EX:
    
        static Random rnd = new Random();
        static int random(int n) {
            return Math.abs(rnd.nextInt()) % n;
        }

THREE FLAWS     
1. if n is a small power of 2
    - the sequence of random numbers will repeat itself after a short period of time.
1. if n is NOT a power of 2
    - some numbers will be returned more frequently than others. 
    - as n grows large, this will become more pronounced. 
1. can actually fail catastrophically
    - return a number OUTSIDE the specified range. 
    - (it is still possible to return a negative number)
    

## Standing on Shoulders of Giants
- "By using a standard library you are taking advantage of the knowledge of
the experts who wrote it and the experience of those who used it before you."
- Keep UP TO DATE
    - make sure to follow updates to the codebase as they are published. 

### Solve the problem above!
- ThreadLocalRandom
    - (as of Java 7, this replaces Random)
    - higher quality, faster
- SplittableRandom
    - use this for fork join pools and  parallel streams instead of
    ThreadLocalRandom
    
## Java Standard Libraries That are important to know
- java.lang
- java.util
- java.io
- Collections
- Streams API
- java.util.concurrent
    
## BENEFITS
- The first advantage is that you are taking advantage of EXPERTS who 
wrote the code and those who have USED it before you 
- Frees you up to solve business problems
    - don't have to spend time solving problems that are "plumbing", and 
    only incidentally relevant to your work.
- Performance improves over time
    - you don't have to pay into this. 
    - the standard library is constantly under active development to
    be better, faster, clearer etc. 
- Functionality improves over time
    - as new use cases emerge, functionality is built in. 
    - missing features are identified, and updated. 
- "mainstream code"
    - by using libraries, it makes code 
        - readable/maintainable
        - reusable
        - not proprietary, so there is a good chance you can HIRE 
        these skill sets, making the language and convention valuable
        in terms of ensuring that staffing and code maintainance can
        remain consistent despite turnover 
        
