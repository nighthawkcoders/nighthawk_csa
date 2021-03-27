package com.nighthawk.csa.model.linkedlists;
/**
 *  Implementation of a Linked List with an Object reference as data;  forward and backward links point to adjacent Nodes.
 *
 */

public class LinkedList
{
    private Object opaqueObject;
    private LinkedList prevNode;
    private LinkedList nextNode;

    /**
     *  Constructs a new element with object objValue,
     *  followed by object address
     *
     * @param  opaqueObject  Address of Object
     */
    public LinkedList(Object opaqueObject, LinkedList node)
    {
        setObject(opaqueObject);
        setPrevNode(node);
        setNextNode(null);
    }

    /**
     *  Clone an object,
     *
     * @param  node  object to clone
     */
    public LinkedList(LinkedList node)
    {
        opaqueObject = node.opaqueObject;
        prevNode = node.prevNode;
        nextNode = node.nextNode;
    }

    /**
     *  Setter for opaqueObjecg in LinkedList object
     *
     * @param  opaqueObject  Address of Object
     */
    public void setObject(Object opaqueObject)
    {
        this.opaqueObject = opaqueObject;
    }

    /**
     *  Setter for prevNode in LinkedList object
     *
     * @param node     A LinkedList object that is prevNode to current Object
     */
    public void setPrevNode(LinkedList node)
    {
        this.prevNode = node;
    }

    /**
     *  Setter for nextNode in LinkedList object
     *
     * @param node     A LinkedList object that is nextNode to current Object
     */
    public void setNextNode(LinkedList node)
    {
        this.nextNode = node;
    }

    /**
     *  Returns opaqueObject for this element
     *
     * @return    The opaqueObject associated with this element
     */
    public Object getObject()
    {
        return opaqueObject;
    }

    /**
     *  Returns reference to previous object in list
     *
     * @return    The pointer is to the previous object in the list
     */
    public LinkedList getPrevious()
    {
        return prevNode;
    }

    /**
     *  Returns reference to next object in list
     *
     * @return    The pointer is to the next object in the list
     */
    public LinkedList getNext()
    {
        return nextNode;
    }

}
