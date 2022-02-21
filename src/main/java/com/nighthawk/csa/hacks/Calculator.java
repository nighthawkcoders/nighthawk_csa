package com.nighthawk.csa.hacks;

import java.util.ArrayList;
import java.util.Arrays;

public class Calculator {
    /* In mathematics,
    an expression or mathematical expression is a finite combination of symbols that is well-formed
    according to rules that depend on the context.
     */
    private final String expression;
    private final ArrayList<String> parsed;
    private Double result = 0.0;

    // Create a 1 argument constructor with input as a mathematical expression
    public Calculator(String expression) {
        this.expression = expression;
        this.parsed = new ArrayList<>();
        this.termSplitter();
    }

    // Term Splitter takes original expression and converts it to ArrayList of terms
    private void termSplitter() {
        int start = 0;  // term split starting index
        String working_term = "";    // term holder
        for (int i = 0; i < this.expression.length(); i++) {
            switch (this.expression.substring(i, i+1)) {
                // term splitters: operators, parenthesis, space
                case "+":
                case "-":
                case "*":
                case "/":
                case "(":
                case ")":
                case " ":
                    // 1st check for working term
                    if (working_term.length() > 0) {
                        parsed.add(this.expression.substring(start, i));
                    }
                    // Add operator or parenthesis to term list
                    if (this.expression.charAt(i) != ' ') {
                        parsed.add(this.expression.substring(i, i+1));
                    }
                    start = i+1;
                    working_term = "";
                    break;

                // multi character terms: numbers, functions, perhaps non-supported elements
                default:
                    working_term = working_term + this.expression.charAt(i);
            }

        }
        // last term
        if (working_term.length() > 0) {
            parsed.add(this.expression.substring(start));
        }
    }

    // Print the expression, terms, and result
    public String toString() {
        return (this.expression + "\n" +
                this.parsed.toString() + "\n" +
                this.result.toString());
    }

    // Tester method
    public static void main(String[] args) {
        Calculator simpleMath = new Calculator("100+200");
        System.out.println("Simple Math\n" + simpleMath);

        System.out.println();

        Calculator extraMath = new Calculator("(100+200) / 300 + 1 + SQRT(2+2)");
        System.out.println("Extra Math\n" + extraMath);
    }
}
