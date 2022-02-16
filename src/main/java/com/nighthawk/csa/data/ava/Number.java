/* package com.nighthawk.csa.data.ava;
import java.util.Random;
import java.util.ArrayList;

// Write a Class Number
public class Number {

    // instance variables
    int num;
    private static int counter;

    // Number has a zero Argument constructor

    // It initializes a random number between 3 and 36, ie the number of squirrels in class
    public Number() {
        // constructor
        this.num = (int)Math.floor(Math.random()*37);
        this.counter = counter;
        counter ++;
    }

    // It contains a getter for the Random Number
    public int getNumber(){
        return this.num;
    }

    // It contains a getter for Index, or the order it was initialized
    public static int index() {
        return counter;
    }

    // Write a tester method
    public static void main(String[] args) {
        // Create an ArrayList of Type Number, my ArrayList is called squirrels
        ArrayList<Number> squirrels = new ArrayList<Number>();

        // Initialize 10 squirrels of class type Number
        for (int i = 0; i < 10; i++)
        {
            Number newnumber = new Number();
            squirrels.add(newnumber);
        }
        // Insert Number instance into ArrayList Squirrel in least to greatest order by random number, mine required nested loops
        ArrayList<Number> sorted = new ArrayList<Number>();




        // Print a formatted message with number of Squirrels and Index by which they were created, use enhanced for loop
        for (Number sort : sorted)
        {
            System.out.println("There were " + sort.getNumber() + " squirrels on day " + sort.index());
        }
    }

}

 */