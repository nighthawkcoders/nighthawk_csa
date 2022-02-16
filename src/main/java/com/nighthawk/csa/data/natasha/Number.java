/*
package com.nighthawk.csa.starters.squirrel;
import groovyjarjarpicocli.CommandLine;

import java.util.ArrayList;
import java.lang.Math;

    // Write a Class Number
    public class Number {
        // instance variables
        private int randomNum;
        private int index;

        // Number has a zero Argument constructor
        // It initializes a random number between 3 and 36, ie the number of squirrels in class
        public Number() {
            randomNum = int((Math.random() * 33) + 3);;
            index = this.index();
        //int((Math.random() * 33) + 3); // constructor
        }

        // It contains a getter for the Random Number

        public int randomNum(){
            return randomNum;
        }

        // It contains a getter for Index, or the order it was initialized

        public int index(){
            return index;
        }

        // Write a tester method
        public static void main(String[] args) {

            int [] squirrels = new int[10];
            // Create an ArrayList of Type Number, my ArrayList is called squirrels
            // Initialize 10 squirrels of class type Number


            // Insert Number instance into ArrayList Squirrel in least to greatest order by random number, mine required nested loops

            int n = squirrels.length();
            for (int i = 0; i <= n - 2; i++){
                int minIndex = i;
                for (int j = i + 1; j <= n - 1; j++){
                    if (squirrels[j] < squirrels[minIndex]){
                        minIndex = j;
                    }
                }
                System.out.println(minIndex);
                int temp = squirrels[i];
                squirrels[i] = squirrels[minIndex];
                squirrels[minIndex] = temp;
            }

            // Print a formatted message with number of Squirrels and Index by which they were created, use enhanced for loop
            for (int index : squirrels) {
                System.out.println(index);
            }
        }

    }
}


 */