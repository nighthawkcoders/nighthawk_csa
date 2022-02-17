package com.nighthawk.csa.data.ava;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;


// Write a Class Number
public class Number implements Comparable<Number>{ //allows you to compare two objects of type Number
    // instance variables
    int count;
    int index;
    static int squirrelIndex = 0;


    // Number has a zero Argument constructor
    // It initializes a random number between 3 and 36, ie the number of squirrels in class
    public Number() {
        // constructor
        Random r = new Random();
        count = 3 + r.nextInt(34);
        index = squirrelIndex;
        squirrelIndex++;
    }

    // It contains a getter for the Random Number
    public int getCount(){
        return count;
    }

    // It contains a getter for Index, or the order it was initialized
    public int getIndex(){
        return index;
    }

    public int compareTo(Number other){
        return this.getCount() - other.getCount(); // compares ArrayLists
    }


    // Write a tester method
    public static void main(String[] args) {

        // Create an ArrayList of Type Number, my ArrayList is called squirrels
        ArrayList<Number> squirrels = new ArrayList<Number>();

        // Initialize 10 squirrels of class type Number
        for (int i = 0; i < 10; i++)
        {
            squirrels.add(new Number());
        }

        // Insert Number instance into ArrayList Squirrel in least to greatest order by random number, mine required nested loops

        Collections.sort(squirrels);

        // Print a formatted message with number of Squirrels and Index by which they were created, use enhanced for loop
        for (int i = 0; i < squirrels.size(); i++)
        {
            System.out.println("Squirrels: " + squirrels.get(i).getCount() + " Day: " + squirrels.get(i).getIndex());
        }





    }
}