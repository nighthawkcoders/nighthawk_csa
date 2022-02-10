package com.nighthawk.csa.data;

import com.nighthawk.csa.consoleUI.ConsoleMethods;
import com.nighthawk.csa.data.DataOps.genericDataModel.Alphabet;
import com.nighthawk.csa.data.DataOps.genericDataModel.risaFRQ6;

import com.nighthawk.csa.data.LinkedLists.CircleQueue;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Circle Queue Driver takes a list of Objects and puts them into a Queue
 * @author     John Mortensen
 *
 */
@Getter
@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class DataOpsController {
    private CircleQueue queue;	// circle queue object
    private int count; // number of objects in circle queue
    //control variables for UI checkboxes and radios

    private boolean alpha;
    private Alphabet.KeyType alphaKey;
    private boolean risa;
    private risaFRQ6.KeyType risaKey;

    /*
     * Circle queue constructor
     */
    public DataOpsController()
    {
        //circle queue inits
        count = 0;
        queue = new CircleQueue();
    }

    /*
     * Add any array of objects to the queue
     */
    public void addCQueue(Object[] objects)
    {
        ConsoleMethods.println("Add " + objects.length);
        for (Object o : objects)
        {
            queue.add(o);
            ConsoleMethods.println("Add: " + queue.getObject() + " " + queue);
            this.count++;
        }
        ConsoleMethods.println();
    }

    /*
     * Delete/Clear all object in circle queue
     */
    public void deleteCQueue()
    {
        int length = this.count;
        ConsoleMethods.println("Delete " + length);

        for (int i = 0; i<length; i++)
        {
            ConsoleMethods.println("Delete: " + queue.delete() + " " + queue);
            this.count--;
        }
    }

    /*
     * String buffer for each row is created to support Thymeleaf (Interable could be alternative)
     */
    public List<String> getCQList()
    {
        List<String> log = new ArrayList<>();
        //travers each row, halting when first is re-encountered (circle queue halt)
        Object first = queue.getFirstObject();
        do {
            log.add(queue.getObject().toString());
        } while (queue.setNext() != first);
        return log;
    }

    /*
     GET request,, parameters are passed within the URI
     */
    @GetMapping("/data/dataops")
    public String data(Model model) {

        //initialize data
        this.count = 0;
        this.queue = new CircleQueue();
        //application specific inits
        //title defaults
        this.alphaKey = Alphabet.KeyType.title;
        Alphabet.key = this.alphaKey;
        this.risaKey = risaFRQ6.KeyType.title;
        risaKey = this.risaKey;
        this.alpha = true;
        this.risa = true;
        this.addCQueue(Alphabet.alphabetData());
        this.addCQueue(risaFRQ6.risaFRQ6data());
        //data is not sorted, queue order (FIFO) is default
        model.addAttribute("ctl", this);
        return "data/dataops"; //HTML render default condition
    }

    /*
     GET request,, parameters are passed within the URI
     */
    @PostMapping("/data/dataops")
    public String dataFilter(
            @RequestParam(value = "alpha", required = false) String alpha,
            @RequestParam(value = "alphaKey", required = false) Alphabet.KeyType alphaKey,
            @RequestParam(value = "risa", required = false) String risa,
            @RequestParam(value = "risaKey", required = false) risaFRQ6.KeyType risaKey,
            Model model)
    {
        //re-init data according to check boxes selected
        count = 0;
        queue = new CircleQueue();
        //for each category rebuild data, set presentation and data defaults

        if (alpha != null) {
            this.addCQueue(Alphabet.alphabetData());
            this.alpha = true;
            this.alphaKey = alphaKey;
            Alphabet.key = this.alphaKey;
        } else {
            this.alpha = false;
        }
        if (risa != null) {
            this.addCQueue(risaFRQ6.risaFRQ6data());
            this.risa = true;
            this.risaKey = risaKey;
            risaFRQ6.key = this.risaKey;
        } else {
            this.risa = false;
        }
        //sort data according to selected options
        this.queue.insertionSort();
        //render with options
        model.addAttribute("ctl", this);
        return "data/dataops";
    }

    /*
     * Show key objects/properties of circle queue
     */
    public void printCQueue()
    {
        //queue and object of queue all print via toString()
        ConsoleMethods.println("Size: " + count);
        ConsoleMethods.println("First Element: " + queue.getFirstObject());
        ConsoleMethods.println("Last Element: " + queue.getLastObject());
        ConsoleMethods.println("Full cqueue: " + queue);
        for (String line : this.getCQList()) {
            ConsoleMethods.println(line);
        }
        ConsoleMethods.println();
    }

    /*
     * Illustrate different Objects that can be placed on same Queue
     */
    public static void main(String[] args)
    {
        //queue
        DataOpsController trial = new DataOpsController();

        //add different types of objects to the same opaque queue
        trial.addCQueue(Alphabet.alphabetData());
        trial.addCQueue(risaFRQ6.risaFRQ6data());
        //display queue objects in queue order
        ConsoleMethods.println("Add order (all data)");
        trial.printCQueue();

        //sort queue objects by specific element within the object and display in sort order
        Alphabet.key = Alphabet.KeyType.letter;
        risaFRQ6.key = risaFRQ6.KeyType.newList;
        trial.queue.insertionSort();
        ConsoleMethods.println("Sorted order (key only)");
        trial.printCQueue();

        //display queue objects, changing output but not sort
        Alphabet.key = Alphabet.KeyType.title;
        risaFRQ6.key = risaFRQ6.KeyType.title;
        ConsoleMethods.println("Retain sorted order (all data)");
        trial.printCQueue();
        trial.queue.insertionSort();
        //display queue objects, changing sort order
        ConsoleMethods.println("Order by data type (all data)");
        trial.printCQueue();

        //delete queue objects
        ConsoleMethods.println("Delete from front (all data)");
        trial.deleteCQueue();
    }

}
