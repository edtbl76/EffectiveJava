# Item 60: Avoid float and double if exact answers are required

## WHAT ARE THEY? 
Float/Double are designed for scientific/engineering use cases. 
- "binary floating-point arithmetic"
    - designed to provide accurate APPROXIMATIONS quickly over a broad 
    range of magnitudes. 
    
## DOWNSIDES
- Floating Point values DO NOT PROVIDE EXACT RESULTS
    - (don't use them if that is required.)
- Strongly recommended NOT to use floating point numbers in MONETARY calculations
    - impossible to represent 0.1 (or any negative power of 10) as a float/double
    
    
    EX:
    
        System.out.println(1.03 - 0.42);
        
            EXPECTED OUTPUT: 0.61
            
            ACTUAL OUTPUT: 0.6100000000000001.
                           
       System.out.println(1.00 - 9 * 0.10);
       
            EXPECTED OUTPUT: 0.1
            
            ACTUAL OUTPUT: 0.09999999999999998.
                           
                           

## Solving This Problem w/ BigDecimal. 

Broken Code using floating point math for monetary calculations


    EX:
    
        public static void main(String[] args) {
            double funds = 1.00;
            int itemsBought = 0;
            for (double price = 0.10; funds >= price; price += 0.10) {
                funds -= price;
                itemsBought++;
            }
            System.out.println(itemsBought + " items bought.");
            System.out.println("Change: $" + funds);
        } 
        
        This will provide the wrong answer:
        
            0.3999999999999....
            
            
Working Code using BigDecimal.
- NOTE: BigDecimal's String constructor is used instead of the
double constructor
    - we have to do this in order to protect the example from the
    lack of precision associated w/ float/double (floating point math)


    EX:
        
        public static void main(String[] args) {
            final BigDecimal TEN_CENTS = new BigDecimal(".10");
            int itemsBought = 0;
            
            BigDecimal funds = new BigDecimal("1.00");
            for (BigDecimal price = TEN_CENTS;
                    funds.compareTo(price) >= 0;
                    price = price.add(TEN_CENTS)) {
                funds = funds.subtract(price);
                itemsBought++;   
            }
            System.out.println(itemsBought + " items bought.");
            System.out.println("Change: $" + funds);
        
        }
        
        
### Disadvantages of BigDecimal
1. it's not as convenient, and kind of ugly to look at. 
1. it is much much much slower. 
    - not an issue for small calculations
    - big issue for LARGE calculations.
 
 
## Alternative Solution using int/long
This shifts the calculation from dollars to cents, so we can use
whole number values. 

    EX: 
        public static void main(String[] args) {
            int itemsBought = 0;
            int funds = 100;
            for (int price = 10; funds >= price; price +=10) {
                funds -= price;
                itemsBought++;
            }
            System.out.println(itemsBought + " items bought.");
            System.out.println("Change: " + funds + " cents"); 
        }   


## Best Practices
- if quantities > 18 digits use BigDecimal
    - no choice. 
- if 9 < quantity <= 18, use long
    - (you could use BigDecimal, but long performs better)
    - long requires that you track decimal point on your own
- if quantity <= 9, use integer
    - (you could use BigDecimal, but int performs better)
    - (you could use long, but its a waste of space)
    - int requires that you track the decimal point on your own. 