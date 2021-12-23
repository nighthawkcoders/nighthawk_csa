package com.nighthawk.csa.algorithm.Math;

import java.util.ArrayList;
import java.util.List;

public class FunMath {

    /* Convert num into a list of digits of the specified base
        num: an integer
        base: binary, octal, decimal, hexadecimal
        @return: a list containing the series of digits of specified base
     */
    static List<Integer> baseConvert(int num, int base) {
        List<Integer> num_list = new ArrayList<>();         // what is List versus ArrayList?  Which one is Abstract?
        do {
            num_list.add(0, num % base);    // what does this do?  explain boxing and unboxing
            num /= base;                                  // what does this do?
        } while (num > 0);                                // why did I put this on the end

        return num_list;
    }

    /* Recursion algorithm that does the summation of series of integers up to n
        num: an integer
        @return:
     */
    static int sumUp(int n) {
        return n == 0 ? 0 : n + sumUp(n - 1);          // what is this called?  hint TO.
    }

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
    }
}

