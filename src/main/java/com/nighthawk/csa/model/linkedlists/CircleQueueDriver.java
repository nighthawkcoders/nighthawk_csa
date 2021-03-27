package com.nighthawk.csa.model.linkedlists;

import com.nighthawk.csa.consoleUI.ConsoleMethods;
import com.nighthawk.csa.model.linkedlists.data.Alphabet;
import com.nighthawk.csa.model.linkedlists.data.Animal;
import com.nighthawk.csa.model.linkedlists.data.Cupcakes;

/**
 * Circle Queue Driver takes a list of Objects and puts them into a Queue
 * @author     John Mortensen
 *
 */
public class CircleQueueDriver {

    private final CircleQueue cQueue;	// circle queue object
    private int count; // number of objects in circle queue

    /*
     * Circle queue constructor
     */
    public CircleQueueDriver()
    {
        count = 0;
        cQueue = new CircleQueue();
    }

    /*
     * Add any array of objects to the queue
     */
    public void addCQueue(Object[] objects)
    {
        System.out.println("Add " + objects.length);
        for (Object o : objects)
        {
            cQueue.add(o);
            System.out.println("Add: " + cQueue.getObject() + " " + cQueue);
            this.count++;
        }
        System.out.println();
    }

    /*
     * Show key objects/properties of circle queue
     */
    public void showCQueue()
    {
        ConsoleMethods.println("Size: " + count);
        ConsoleMethods.println("First Element: " + cQueue.getFirstObject());
        ConsoleMethods.println("Last Element: " + cQueue.getLastObject());
        ConsoleMethods.println("Full cqueue: " + cQueue);
        ConsoleMethods.println();
    }

    /*
     * Delete/Clear all object in circle queue
     */
    public void deleteCQueue()
    {
        int length = this.count;
        System.out.println("Delete " + length);

        for (int i = 0; i<length; i++)
        {
            System.out.println("Delete: " + cQueue.delete() + " " + cQueue);
            this.count--;
        }
    }


    /*
     * Illustrate different Objects that can be placed on same Queue
     */
    public static void main(String[] args)

    {
        //queue
        CircleQueueDriver trial = new CircleQueueDriver();

        //add different types of objects to the same opaque queue
        trial.addCQueue(Animal.animalData());
        trial.addCQueue(Cupcakes.cupCakeData());
        trial.addCQueue(Alphabet.alphabetData());
        //display queue objects in queue order
        ConsoleMethods.println("Add order (all data)");
        trial.showCQueue();

        //sort queue objects by specific element within the object and display in sort order
        Animal.key = Animal.KeyType.name;
        Cupcakes.key = Cupcakes.KeyType.flavor;
        Alphabet.key = Alphabet.KeyType.letter;
        trial.cQueue.insertionSort();
        ConsoleMethods.println("Sorted order (key only)");
        trial.showCQueue();

        //display queue objects
        Animal.key = Animal.KeyType.combo;
        Cupcakes.key = Cupcakes.KeyType.combo;
        Alphabet.key = Alphabet.KeyType.combo;
        ConsoleMethods.println("Sorted order (all data)");
        trial.showCQueue();

        //delete queue objects
        ConsoleMethods.println("Delete from front (all data)");
        trial.deleteCQueue();
    }

}
