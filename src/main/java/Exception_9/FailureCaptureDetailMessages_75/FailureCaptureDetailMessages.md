# Item 75: Include failure-capture information in detail messages

## Stack Trace
- automatically printed when a program fails due to an uncaught exception
- contains the exception's "string representation" 
    - (String Representation is the result of invoking the exception's
    toString method.)
        - USUALLY: ClassName followed by "Detail Message"
- includes exact file and line numbers from which exception was thrown.

### Detail Message
Detail Message is typically the "primo uno" breadcrumb left for 
debugging when a failure occurs. 
- ideally, this should "Capture the Failure"

### Capturing the Failure
- ensures that the contents of the detail message includes the values of 
all parameters and fields that contributed to the exception
    
#### Security
- an important aspect of capturing the failure is that it must protect 
classified/sensitive data because debugging information will be highly
visible. 
- do NOT include:
    - passwords
    - encryption keys
    - other things that shouldn't be viewed by large swaths of beady eyes
    
#### Accessor Methods
(Item 70) in some cases, it's appropriate to provide accessor methods for an exception's
failure-capture information
- more important on CHECKED than UNCHECKED. 
    
### SUCCINCTNESS (Stack Trace)
- Parameters/Values YES
- Prose/Verbage NO

The stack trace is intended to be analyzed in conjunction w/ docs and
source code. 
- if we need "detail" it will be from the documentation and/or source code.
- not localized
- information content > readability

### User-Level Content
- User Level Content is intended to be "localized" (i.e. translated to
native languages)
- needs to be readable and "lay-person" understandable.

### BEST PRACTICES
- require the failure-capture information in the exception constructors
instead of a string detail message. 
    - allows string detail message to be generated automatically to
    include the provided information.
    - (This actually makes it hard NOT to capture the failure)
    - code is centralized and "driven" to generate a high quality "Detail Message"
    
    
    EX: 
    
        /** 
         *  Constructs an IndexOutOfBoundsException
         *
         *  @param lowerBound   the lowest legal index value
         *  @param upperBound   the highest legal index value plus one
         *  @param index        the actual index value
         */
        public IndexOutOfBoundsException(int lowerBound, int upperBound,
                                        int index) {
            
            // Generate a detail message that captures the failure
            super(String.format(
                        "Lower bound: %d, Upper bound: %d, Index: %d",
                        lowerBound, upperBound, index));
            
            // Save failure information for programmatic access
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.index = index;                               
        }
        
