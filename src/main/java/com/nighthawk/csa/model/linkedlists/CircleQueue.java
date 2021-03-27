package com.nighthawk.csa.model.linkedlists;

import java.util.*;

/**
 *  Implementation of Queue, using LinkedList (previous and next).
 *  December 24, 2019
 * @author     John Mortensen
 *
 */
public class CircleQueue
{
    private LinkedList headNode;			// 1st element in Queue
    private LinkedList tailNode;			// Last element in Queue
    private LinkedList currentNode;

    /**
     *  Constructor for the SinglyLinkedList object
     *  Generates an empty list.
     */
    public CircleQueue()
    {
        headNode = null;
        tailNode = null;
        currentNode = null;
    }

    /**
     *  Returns the head opaqueObject.
     *
     * @return  the head opaqueObject in Stack.
     */
    public Object getFirstObject()
    {
        currentNode = headNode;

        if (headNode == null)
            return null;
        else
            return headNode.getObject();
    }

    /**
     *  Returns the tail opaqueObjects.
     *
     * @return  the current opaqueObject in Stack.
     */
    public Object getLastObject()
    {
        currentNode = tailNode;

        if (tailNode == null)
            return null;
        else
            return tailNode.getObject();
    }

    /**
     *  Returns the current Object
     *
     * @return  the current Object in Stack.
     */
    public Object getObject()
    {
        if (currentNode == null)
            return null;
        else
            return currentNode.getObject();
    }

    /**
     *  Advances the current node.
     *
     */
    public Object setNext()
    {
        currentNode = currentNode.getNext();

        // never let currentNode be null, wrap to head
        if (currentNode == null)
            currentNode = headNode;

        return currentNode.getObject();
    }

    /**
     *  Reverses the current node.
     *
     */
    public void setPrevious()
    {
        currentNode = currentNode.getPrevious();

        // never let currentNode be null, wrap to head
        if (currentNode == null)
            currentNode = tailNode;
    }


    /**
     *  Add a new object at the end of the Queue,
     *
     * @param  opaqueObject  is the data to be inserted in the Queue object.
     */
    public void add(Object opaqueObject)
    {
        // add new object to end of Queue
        // set opaqueObject
        // build previous link of tail (as current)
        tailNode = new LinkedList(opaqueObject, currentNode);

        // build next link of current (as tail)
        if (currentNode != null)
            currentNode.setNextNode(tailNode);

        // after links are established current eq tail
        currentNode = tailNode;

        // head eq tail on 1st element only
        if (headNode == null) {
            headNode = tailNode;
        }
    }

    /**
     *  Delete an object from the front of the Queue,
     *
     */
    public Object delete()
    {
        Object opaqueObject = null;

        if (headNode != null) {
            opaqueObject = headNode.getObject();
            headNode = headNode.getNext();
            if (headNode == null)
                tailNode = headNode;
            else
                headNode.setPrevNode(null);
        }

        return opaqueObject;
    }


    /**
     *  Returns a string representation of this Queue,
     *  polymorphic nature of toString overrides of standard System.out.print behavior
     *
     * @return    string representation of this Queue
     */
    public String toString()
    {
        StringBuilder queueToString = new StringBuilder("[");

        LinkedList node = headNode;  			// start from the head
        while (node != null)
        {
            queueToString.append("(").append(node.getObject()).append(")"); 	// append the data to output string
            node = node.getNext();				// go to next node
            if (node != null)
                queueToString.append(", ");
        }										// loop 'till queue ends
        queueToString.append("]");
        return queueToString.toString();
    }

    /**
     * Performs insertion sort based off of the contents of object
     */
    public void insertionSort() {

        //two nodes needed for insertion sort indexes
        LinkedList node1 = headNode;
        LinkedList node2 = (node1 == null) ? null : node1.getNext();

        //continue while nodes remain in bounds
        while (node2 != null) {

            //inner loop pointers for compares and shifts
            LinkedList slot1 = node1;
            LinkedList slot2 = node2;

            //key to be inserted into sorted slot
            LinkedList key = new LinkedList(node2);		//note: make key a different object, persistent/static in value (node2 moves)
            String keyText = node2.getObject().toString();

            //walks slots backwards until key position in found
            while ( slot1.getObject().toString().compareTo(keyText) > 0 ) {
                //shifts object greater than key value to the right in list
                slot2.setObject(slot1.getObject());

                //moves inner loop pointers
                slot1 = slot1.getPrevious();
                slot2 = slot2.getPrevious();

                //stop at the front of list
                if (slot1 == null)
                    break;

            }
            //place key in insertion position
            slot2.setObject(key.getObject());

            //advance insertion sort indexes
            node1 = node1.getNext();
            node2 = node2.getNext();
        }

    }

}
