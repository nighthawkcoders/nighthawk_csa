package com.nighthawk.csa.hacks.squirrel;

import java.util.ArrayList;

// Write a Class Number
public class Number {
    private static int COUNT = 0;
    private final int number;
    private final int index;

    // Number has a zero Argument constructor
    // It initializes a random number between 3 and 36, ie the number of squirrels in class
    public Number() {
        int CLASS_SIZE = 36;
        int CLASS_MIN = 3;
        int range = CLASS_SIZE - CLASS_MIN + 1;
        this.number = (int)(Math.random()*range) + CLASS_MIN;
        this.index = Number.COUNT++;
    }

    // It contains a getter for the Random Number
    public int getNumber() {
        return this.number;
    }

    // It contains a getter for Index, or the order it was initialized
    public int getIndex() {
        return this.index;
    }

    public String toString() {
        return "Number: " + this.getNumber() + " Day: " + this.getIndex();
    }

    // Write a tester method to:
    public static void main(String[] args) {

        // Create an ArrayList of Type Number, mine ArrayList is called squirrels
        ArrayList<Number> squirrels = new ArrayList<>();

        // Initialize 10 squirrels of class type Number
        // Insert Number instance into ArrayList Squirrel in least to greatest order by getNumber()
        for (int i = 0; i < 10; i++) {
            Number num = new Number();
            // Insert in position now
            for (int j = 0; true; j++) {
                if ((squirrels.size() == 0) // empty condition means add it
                        || (squirrels.size() == j) // at end means add to end
                        || (num.getNumber() < squirrels.get(j).getNumber()) )  // less than current add in j position
                {
                    squirrels.add(j, num);
                    break;  // break forever loop when inserted
                }
            }
        }

        // Print a formatted message with number of Squirrels and Index by which they were created
        System.out.println("Best case of a Teachers life vs Squirrels over 10 day period");
        int total = 0;
        for (Number s : squirrels) {
            total += s.getNumber();
            System.out.println(s);  // prints toString() method
        }
        System.out.println("Average squirrels per day: " + total / (double) squirrels.size());

    }

}


