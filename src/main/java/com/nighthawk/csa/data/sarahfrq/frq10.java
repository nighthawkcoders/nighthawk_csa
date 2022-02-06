package com.nighthawk.csa.data.sarahfrq;

public class frq10 {
    public static class gcf{
        private Integer number1;
        private Integer number2;

        public gcf(Integer num1, Integer num2){
            this.number1 = num1;
            this.number2 = num2;
        }
        
        public String display(){
            return "The two numbers are " + number1 + " and " + number2;
        }
        
        public int obtainGCF(){
            //x and y are the numbers to find the GCF
            int gcd = 1;
            //running loop form 1 to the smallest of both numbers
            for(int i = 1; i <= number1 && i <= number2; i++)
            {
            //returns true if both conditions are satisfied
                if(number1%i==0 && number2%i==0){
                    //storing the variable i in the variable gcd
                    gcd = i;
                }
            }
            return gcd;
        }

    }

}
