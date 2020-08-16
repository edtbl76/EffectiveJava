# Item 63: Beware the performance of String concatenation

## Concatenation Downsides
"Using the string concatenation operator repeatedly to concatenate n strings
requires time quadratic in n"
- This is because Strings are immutable (Item 17)
- string concatenation copies the content of all strings involved


    EX:
        
        public String statement() {
            String result = "";
            for (int i = 0; i < numItems(); i++) {
                result += lineForItem(i);           // String concat
            }
            return result;
        }   
        

### Solution - StringBuilder
- StringBuilder can be used to store the string under construction

    EX: 
    
        public String statement() {
            Stringbuilder sb = new StringBuilder(numItems() * LINE_WIDTH);
            for (int i = 0; i < numItems(); i++) {
                sb.append(lineForItem(i));
            }
            return sb.toString();
        }
        
- This preallocates the StringBuilder to be large enough to hold the
entire result
    - this eliminates the need for auto-growth. 
    - HOWEVER, even a default StringBuilder can be 5-10x faster than
    String concatenation
    

## Best Practices
- StringBuilder, preallocated (if you know how much space you need)
- StringBuilder, default size, uses auto-growth
- process Strings one at a time
- use non-immutable structure like a Char Array