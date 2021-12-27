package com.nighthawk.csa.utility;

import java.util.ArrayList;
import java.util.List;

/* Static FunMath Algorithms */
public class FunMath {
    /* Convert num into a list of digits of the specified base
        num: an integer
        base: binary, octal, decimal, hexadecimal
        @return: a list containing the series of digits of specified base
     */
    static public List<Integer> baseConvert(int num, int base) {
        List<Integer> num_list = new ArrayList<>();       // what is List versus ArrayList?  Which one is Abstract?
        do {
            num_list.add(0, num % base);    // what does this do?  explain boxing and unboxing
            num /= base;                                  // what does this do?
        } while (num > 0);                                // why did I put this on the end

        return num_list;
    }

    /* Recursion mvc that does the summation of series of integers up to n
        num: an integer
        @return: an integer containing summation total
     */
    static public int sumUp(int n) {
        return n == 0 ? 0 : n + sumUp(n - 1);          // what is this called?  hint TO.
    }

    /* Random mvc that finds a number in low to high range (inclusive)
        low: integer
        high: integer
        @return: an integer containing random number
     */
    static public int random(int low, int high) {
        int multiplier = (high - low) + 1;  // multiplier is one plus difference as int cast truncates
        return (int) (Math.random() * multiplier) + low;  // plus low establishes floor
    }

    /* FunMath Tester
     */
    public static void main(String[] args) {
        System.out.println("Sum Up testing for 3, 4, 5, 6, 7");
        System.out.println(sumUp(3));
        System.out.println(sumUp(4));
        System.out.println(sumUp(5));
        System.out.println(sumUp(6));
        System.out.println(sumUp(7));

        System.out.println("Base 2 to array for 0, 255, 256");
        System.out.println(baseConvert(0,2));
        System.out.println(baseConvert(255,2));
        System.out.println(baseConvert(256,2));

        System.out.println("Base 8 to array for 0, 255, 256");
        System.out.println(baseConvert(0,28));
        System.out.println(baseConvert(255,8));
        System.out.println(baseConvert(256,8));

        System.out.println("Base 10 to array for 0, 255, 256");
        System.out.println(baseConvert(0,10));
        System.out.println(baseConvert(255,10));
        System.out.println(baseConvert(256,10));

        System.out.println("Base 16 to array for 0, 255, 256");
        System.out.println(baseConvert(0,16));
        System.out.println(baseConvert(255,16));
        System.out.println(baseConvert(256,16));

        System.out.println("Random between 0,1, 10 times");
        for (int i = 0; i < 10; i++)
            System.out.print( random(0,1) );

        System.out.println("\nRandom between 1,8, 20 times");
        for (int i = 0; i < 20; i++)
            System.out.print( random(1,8) );
    }
}

