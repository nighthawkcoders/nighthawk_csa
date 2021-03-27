package com.nighthawk.csa.consoleUI;

public class ConsoleMethods {

    //Method to make sure no data is available in the
    //input stream
    private static void inputFlush() {
        try {
            while (System.in.available() != 0) {
            }
        } catch (java.io.IOException e) {
            System.out.println("Input error");
        }
    }

    public static void printChar(char txt)
    {
        System.out.print(txt);
    }

    public static void clearScreen()
    {
        printChar('\u000C');
    }

    public static void print(String txt)
    {
        System.out.print(txt);
    }

    public static void println()
    {
        System.out.println("\n");
    }

    public static void println(String txt)
    {
        System.out.println(txt);
    }

    public static void println(Object obj)
    {
        System.out.println(obj);
    }

    public static void printPrompt(String prompt) {
        print(prompt + " ");
        System.out.flush();
    }

    public static String inputString(String prompt) {
        //inputFlush();
        printPrompt(prompt);
        return inString();
    }

    private static String inString() {
        int aChar;
        StringBuilder s = new StringBuilder();
        boolean finished = false;

        while (!finished) {
            try {
                aChar = System.in.read();
                if (aChar < 0 || (char) aChar == '\n')
                    finished = true;
                else if ((char) aChar != '\r')
                    s.append((char) aChar); // Enter into string
            }
            catch (java.io.IOException e) {
                System.out.println("Input error");
                finished = true;
            }
        }
        return s.toString();
    }

    public static int inputInt(String prompt) {
        while (true) {
            inputFlush();
            printPrompt(prompt);
            try {
                return Integer.parseInt(inString().trim());
            }

            catch (NumberFormatException e) {
                System.out.println("Invalid input. Not an integer");
            }
        }
    }

    public static char inputChar(String prompt) {
        int aChar = 0;
        inputFlush();
        printPrompt(prompt);
        try {
            aChar = System.in.read();
        }
        catch (java.io.IOException e) {
            println("Input error");
        }
        inputFlush();
        return (char) aChar;
    }

}