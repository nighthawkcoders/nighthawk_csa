package com.nighthawk.csa.data.ava;

public class avaFrq6 {
    public static void main(String[]args){
        //creating the array using words given by CB
        String words[] = {"ten", "fading", "post", "card", "thunder", "hinge", "trailing", "batting"};

        //for loop to go through array
        // word: words allows to search substring of the strings
        for(String word : words){
            //lastIndexOf method allows to search for strings in array that have the last 3 letters as "ing" to be printed
            if(word.lastIndexOf("ing") == word.length() - 3){
                System.out.println(word);
            }
        }
    }


}
