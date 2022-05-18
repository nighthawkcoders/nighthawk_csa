package com.nighthawk.csa.utility.LinkedLists2;

import com.nighthawk.csa.mvc.dataOps.genericDataModel.Alphabet;
import com.nighthawk.csa.mvc.dataOps.genericDataModel.Animal;
import com.nighthawk.csa.mvc.dataOps.genericDataModel.Cupcakes;
import com.nighthawk.csa.mvc.dataOps.genericDataModel.Generics;
import com.nighthawk.csa.utility.ConsoleMethods;

/**
 * Stack: custom implementation
 * @author     John Mortensen
 *
 * 1. Uses custom LinkedList of Generic type T
 * 2. "has a" LinkedList for Last In First Out (LIFO) object
 *
 */
public class Stack<T>
{
    private LinkedList<T> lifo = null;  // last in first out Object of stack

    /**
     *  Returns the current (LIFO) objects data.
     *
     * @return  the current data in Stack.
     */
    public T peek()
    {
        if (lifo == null)
            return null;
        else
            return lifo.getData();
    }

    /**
     *  Inserts a new data object at the top of this Stack,
     *
     * @param  data  to be inserted at the top of the Stack.
     */
    public void push(T data)
    {
        // note the order that things happen:
        // the new object becomes current and gets a value
        // current lifo is parameter, it is assigned as previous node in lifo
        lifo = new LinkedList<>(data, lifo);
    }

    /**
     *  Removes the top element in the Stack.
     *
     * @return  the popped data from the Stack.
     */
    public T pop()
    {
        T data = null;  // empty condition
        if (lifo != null) {
            data = lifo.getData();
            lifo = lifo.getPrevious();  // stack is overwritten with next item
        }
        return data;    // pop always returns data of element popped
    }

    /**
     *  Returns a string representation of this Stack,
     *  polymorphic nature of toString overrides of standard System.out.print behavior
     *
     * @return    string representation of data within Stack
     */
    public String toString()
    {
        StringBuilder stackToString = new StringBuilder("[");

        LinkedList<T> node = lifo;  				// start from the back
        while (node != null)
        {
            stackToString.append(node.getData()); 	// append the database to output string
            node = node.getPrevious();    		// go to previous node
            if (node != null)
                stackToString.append(", ");
        }										// loop 'till you reach the beginning
        stackToString.append("]");
        return stackToString.toString();
    }


}

/**
 * Stack Manager
 * 1. "has a" Stack
 * 2. support management of Stack tasks (aka: titling, adding a list, emptying, printing)
 *
 */
class StackDriver<T> {
    static public boolean DEBUG = false;
    private String title;
    private final Stack<T> stack = new Stack<>(); // stack object
    private int count;

    /**
     *  Stack constructor
     *
     * @param  title  name associated with stack
     * @param  seriesOfObjects  data to be inserted into stack
     */
    @SafeVarargs
    public StackDriver(String title, T[]... seriesOfObjects) {
        this.title = title;
        this.addList(seriesOfObjects);
    }

    /**
     *  Add a series of data object to the Stack
     *
     * @param  seriesOfObjects  data to be inserted into stack
     */
    @SafeVarargs
    public final void addList(T[]... seriesOfObjects)
    {
        if (DEBUG) ConsoleMethods.println("Add " + title);
        for (T[] objects: seriesOfObjects)
            for (T data : objects) {
                this.stack.push(data);
                this.count++;
                if (DEBUG) ConsoleMethods.println("Push: " + this.stack.peek() + " " + this.stack);
            }
        if (DEBUG) ConsoleMethods.println();
    }

    /**
     *  Empty or remove all data objects from the Stack
     *
     */
    public void emptyStack()
    {
        if (DEBUG) ConsoleMethods.println("Delete " + title);
        while (this.stack.peek() != null) {
            T data = this.stack.pop();
            if (DEBUG) ConsoleMethods.println("Pop: " + data + " " + stack);
        }
        if (DEBUG) ConsoleMethods.println();
    }

    /**
     *  Print analytics from the Stack
     *
     */
    public void printStack()
    {
        ConsoleMethods.println("Size: " + count);
        ConsoleMethods.println("Top Element: " + stack.peek());
        ConsoleMethods.println("Full Stack: " + stack);
        ConsoleMethods.println();
    }

}

class Main {
    /**
     * Test Stack functionality using different types of Objects
     *
     */
    public static void main(String[] args) {
        // Create Stack of Words
        StackDriver.DEBUG = false;
        String[] words = new String[]{"seven", "slimy", "snakes", "sallying", "slowly", "slithered", "southward"};
        StackDriver<String> sWords = new StackDriver<>("Words", words);
        sWords.printStack();
        sWords.emptyStack();

        // Create Stack of Integers
        StackDriver.DEBUG = false;
        Object[] numbers = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        StackDriver<Object> sNums = new StackDriver<>("Integers", numbers );
        sNums.printStack();
        sNums.emptyStack();

        // Create iterable Queue of NCS Generics
        StackDriver.DEBUG = false;
        Animal.setOrder(Animal.KeyType.name);
        Alphabet.setOrder(Alphabet.KeyType.letter);
        Cupcakes.setOrder(Cupcakes.KeyType.flavor);
        // Illustrates use of a series of arrays
        StackDriver<Generics> sGenerics = new StackDriver<>("My Generics",
                Alphabet.alphabetData(),
                Animal.animalData(),
                Cupcakes.cupCakeData()
        );
        sGenerics.printStack();
        sGenerics.emptyStack();
    }
}
