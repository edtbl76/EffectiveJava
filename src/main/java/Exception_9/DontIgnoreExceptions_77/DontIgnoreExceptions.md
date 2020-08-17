# Item 77: Don't ignore exceptions

## Empty Catch
- Empty catch blocks defeat the purpose of exceptions, 
which is to force you to handle exceptional conditions.
    - This is like ignoring a fire alarm
    
    
    EX:
    
        try  {
            logic()
        } catch (ExceptionToIgnore e) {
            // nothing here?
        }
    
    
## Documented Catch
If there is a reason (preferrably justified w/ logic and facts) to 
ignore an exception
- there should be an explicit comment explaining why it is appropriate
- the variable name should be 'ignored'


    EX: 
    
        Future<Integer> future = exec.submit(planarMap:: chromaticNumber);
        int numColors = 4;  // Default; guaranteed sufficient for any map
        
        try {
            numColors = future.get(1L, TimeUnit.SECONDS);
        } catch (TimeoutException | ExecutionException ignored) {
            // Use default: minimal coloring is desirable, not required.
        }
    
        