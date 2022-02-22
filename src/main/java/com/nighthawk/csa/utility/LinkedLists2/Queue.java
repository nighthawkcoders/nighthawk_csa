package com.nighthawk.csa.utility.LinkedLists2;

import com.nighthawk.csa.mvc.DataOps.genericDataModel.Animal;

import java.util.Iterator;

// Custom Linked List class using Java Generic T and implements Iterable
class Queue<T> implements Iterable<T> {
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

    // next returns data current data and updates to next position in queue
    public T next() {
        T data = current.getData();
        current = current.getNext();
        return data;
    }
}

// Driver class
class Main {
    public static void main(String[] args)
    {
        // Create iterable Queue of Strings
        Queue<String> sQ = new Queue<>();
        sQ.add("abc");sQ.add("def");sQ.add("mno");sQ.add("pqr");sQ.add("xyz");
        // Prove as iterable with For Each Loop
        System.out.print("Strings in queue: " );
        for (String s : sQ)
            System.out.print(s + " ");
        System.out.println();

        // Create iterable Queue of Integers
        Queue<Integer> iQ = new Queue<>();
        iQ.add(0);iQ.add(1);iQ.add(2);iQ.add(3);iQ.add(4);
        // Prove as iterable with For Each Loop
        Animal.setOrder(Animal.KeyType.name);
        System.out.print("Integers in queue: ");
        for (Integer i : iQ)
            System.out.print(i + " ");
        System.out.println();

        // Create iterable Queue of Animals
        Queue<Animal> aQ = new Queue<>();
        for (Animal a : Animal.animalData()) {
            aQ.add(a);
        }
        System.out.print("Animals in queue: ");
        for (Animal a : aQ)
            System.out.print(a + " ");
        System.out.println();
    }
}
