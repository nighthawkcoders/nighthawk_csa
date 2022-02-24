package com.nighthawk.csa.utility.LinkedLists2;

import com.nighthawk.csa.mvc.DataOps.genericDataModel.Alphabet;
import com.nighthawk.csa.mvc.DataOps.genericDataModel.Animal;
import com.nighthawk.csa.mvc.DataOps.genericDataModel.Cupcakes;

import java.util.Iterator;

/**
 * Queue: custom implementation
 * @author     John Mortensen
 *
 * 1. Uses custom LinkedList of Generic type T
 * 2. Implements Iterable
 * 3. "has a" LinkedList for head and tail
 */
public class Queue<T> implements Iterable<T> {
    LinkedList<T> head, tail;

    /**
     *  Add a new object at the end of the Queue,
     *
     * @param  data,  is the data to be inserted in the Queue.
     */
    public void add(T data) {
        // add new object to end of Queue
        LinkedList<T> tail = new LinkedList<>(data, null);

        if (head == null)  // initial condition
            this.head = this.tail = tail;
        else {  // nodes in queue
            this.tail.setNextNode(tail); // current tail points to new tail
            this.tail = tail;  // update tail
        }
    }

    /**
     *  Returns the head object.
     *
     * @return  this.head, the head object in Queue.
     */
    public LinkedList<T> getHead() {
        return this.head;
    }

    /**
     *  Returns the tail object.
     *
     * @return  this.tail, the last object in Queue
     */
    public LinkedList<T> getTail() {
        return this.tail;
    }

    /**
     *  Returns the iterator object.
     *
     * @return  this, instance of object
     */
    public Iterator<T> iterator() {
        return new QueueIterator<>(this);
    }
}

/**
 * Queue Iterator
 *
 * 1. "has a" current reference in Queue
 * 2. supports iterable required methods for next that returns a data object
 */
class QueueIterator<T> implements Iterator<T> {
    LinkedList<T> current;  // current element in iteration

    // QueueIterator is intended to the head of the list for iteration
    public QueueIterator(Queue<T> q) {
        current = q.getHead();
    }

    // hasNext informs if next element exists
    public boolean hasNext() {
        return current != null;
    }

    // next returns data object and advances to next position in queue
    public T next() {
        T data = current.getData();
        current = current.getNext();
        return data;
    }
}

/**
 * Queue Manager
 * 1. "has a" Queue
 * 2. support management of Queue tasks (aka: titling, adding a list, printing)
 */
class QueueManager {
    // queue data
    private final String name; // name of queue
    private int count = 0; // number of objects in queue
    public final Queue<Object> queue = new Queue<>(); // queue object

    /**
     *  Queue constructor
     *  Title with empty queue
     */
    public QueueManager(String name) {
        this.name = name;
    }

    /**
     *  Queue constructor
     *  Title with series of Arrays of Objects
     */
    public QueueManager(String name, Object[]... seriesOfObjects) {
        this.name = name;
        this.addList(seriesOfObjects);
    }

    /**
     * Add a list of objects to queue
     */
    public void addList(Object[]... seriesOfObjects) {
        for (Object[] objects: seriesOfObjects)
            for (Object o : objects) {
                this.queue.add(o);
                this.count++;
            }
    }

    /**
     * Print any array objects from queue
     */
    public void printQueue() {
        System.out.println(this.name + " count: " + count);
        System.out.print(this.name + " data: ");
        for (Object o : queue)
            System.out.print(o + " ");
        System.out.println();
    }
}

/**
 * Driver Class
 * Tests queue with string, integers, and mixes of Classes and types
 */
class QueueTester {
    public static void main(String[] args)
    {
        // Create iterable Queue of Words
        Object[] words = new String[] { "seven", "slimy", "snakes", "sallying", "slowly", "slithered", "southward"};
        QueueManager qWords = new QueueManager("Words", words );
        qWords.printQueue();

        // Create iterable Queue of Integers
        Object[] numbers = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        QueueManager qNums = new QueueManager("Integers", numbers );
        qNums.printQueue();

        // Create iterable Queue of NCS Generics
        Animal.setOrder(Animal.KeyType.name);
        Alphabet.setOrder(Alphabet.KeyType.letter);
        Cupcakes.setOrder(Cupcakes.KeyType.flavor);
        // Illustrates use of a series of repeating arguments
        QueueManager qGenerics = new QueueManager("My Generics",
                Alphabet.alphabetData(),
                Animal.animalData(),
                Cupcakes.cupCakeData()
        );
        qGenerics.printQueue();

        // Create iterable Queue of Mixed types of data
        QueueManager qMix = new QueueManager("Mixed");
        qMix.queue.add("Start");
        qMix.addList(
                words,
                numbers,
                Alphabet.alphabetData(),
                Animal.animalData(),
                Cupcakes.cupCakeData()
        );
        qMix.queue.add("End");
        qMix.printQueue();
    }
}
