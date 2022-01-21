package com.nighthawk.csa.data.DataOps.genericDataModel;
import com.nighthawk.csa.consoleUI.ConsoleMethods;

import java.util.*;

public class risaFRQ6 extends Generics {
    public enum KeyType {title, frq, newList}
    public static KeyType key = KeyType.title;
    private final List<String> newList;
    private final List<String> frq;
    // initializes the parameter

    risaFRQ6(List<String> frq)
    {

        super.setType("FRQ6");
        int len = frq.size();
        this.frq = frq;

        List<String> newList = new ArrayList<String>();

        for (int i = 0; i < len; i++) {
            String str = frq.get(i);

            if (str.contains("ing")) {
                newList.add(str);
            } else {
                System.out.print("wrong answer");
            }

        }

        this.newList = newList;
        System.out.print(newList);
    }


    /*
     * toString provides output based off of this.key setting
     */
    @Override
    public String toString() {
        String output="";
        switch(key) {
            case frq:
                output += this.frq;
                break;
            case title:
            default:
                output = super.getType() + ": " + this.frq;
        }
        return output;
    }

    public static risaFRQ6[] risaFRQ6data() {
        List<String> word = new ArrayList<String>();
        word.add("looking");
        word.add("hello");
        word.add("jumping");
        word.add("coding");
        word.add("icecream");
        System.out.print(word);
        return new risaFRQ6[]{
                new risaFRQ6(word),
        };
    }

    public static void main(String[] args)
    {
        Generics[] risaFRQ6 = risaFRQ6data();
        for(Generics c:  risaFRQ6)
            ConsoleMethods.println(c);
    }

}
