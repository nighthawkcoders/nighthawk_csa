package com.nighthawk.csa.hacks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import com.nighthawk.csa.mvc.DataOps.genericDataModel.Animal;
import com.nighthawk.csa.utility.LinkedLists.Stack;

public class Calculator {
    /* In mathematics,
    an expression or mathematical expression is a finite combination of symbols that is well-formed
    according to rules that depend on the context.
     */
    private final String expression;
    private ArrayList<String> tokens;
    private ArrayList<String> reverse_polish;
    private Double result = 0.0;

    private static final Map<String, Integer> OPERATORS = new HashMap<>();
    static
    {
        // Map<"token", precedence>
        OPERATORS.put("*", 3);
        OPERATORS.put("/", 3);
        OPERATORS.put("%", 3);
        OPERATORS.put("+", 4);
        OPERATORS.put("-", 4);
    }

    // Create a 1 argument constructor expecting a mathematical expression
    public Calculator(String expression) {
        // original input
        this.expression = expression;

        // parse expression into terms
        this.termTokenizer();

        // place terms into reverse polish notation
        this.reversePolish();

        // calculate reverse polish notation
        this.rpnToResult();
    }

    // Test if token is an operator
    private boolean isOperator(String token)
    {
        return OPERATORS.containsKey(token);
    }

    // Compare precedence of operators.
    private Boolean isPrecedent(String token1, String token2)
    {
        return (OPERATORS.get(token1) - OPERATORS.get(token2) <= 0) ;
    }

    // Term Splitter takes original expression and converts it to ArrayList of terms
    // a calculator will need this to keep evaluation of expression effective and simple
    private void termTokenizer() {
        this.tokens = new ArrayList<>();

        int start = 0;  // term split starting index
        String working_term = "";    // term holder
        for (int i = 0; i < this.expression.length(); i++) {
            switch (this.expression.substring(i, i+1)) {
                // term splitters: operators, parenthesis, space
                case "+":
                case "-":
                case "*":
                case "/":
                case "%":
                case "(":
                case ")":
                case " ":
                    // 1st check for working term and add if it exists
                    if (working_term.length() > 0) {
                        tokens.add(this.expression.substring(start, i));
                    }
                    // Add operator or parenthesis term to list
                    if (this.expression.charAt(i) != ' ') {
                        tokens.add(this.expression.substring(i, i+1));
                    }
                    // Get ready for next term
                    start = i+1;
                    working_term = "";
                    break;

                // multi character terms: numbers, functions, perhaps non-supported elements
                default:
                    // Add next character to working term
                    working_term = working_term + this.expression.charAt(i);
            }

        }
        // last term
        if (working_term.length() > 0) {
            tokens.add(this.expression.substring(start));
        }
    }

    private void reversePolish () {
        this.reverse_polish = new ArrayList<>();

        Stack tokenStack = new Stack();
        for (String token : tokens) {
            switch (token) {
                // If left bracket push token on to stack
                case "(":
                    tokenStack.push(token);
                    break;
                case ")":
                    while (tokenStack.peek() != null && !tokenStack.peek().equals("(")) {
                        reverse_polish.add( (String)tokenStack.pop());
                    }
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                case "%":
                    while (tokenStack.peek() != null) {
                        if ( isPrecedent(token, (String)tokenStack.peek() ) ) {
                            reverse_polish.add( (String)tokenStack.pop() );
                            break;
                        }
                    }
                    tokenStack.push(token);
                    break;
                default:
                    this.reverse_polish.add(token);
            }
        }
        while (tokenStack.peek() != null) {
            reverse_polish.add((String)tokenStack.pop());
        }

    }

    private void rpnToResult()
    {
        Stack stack = new Stack();

        // For each token
        for (String token : this.reverse_polish)
        {
            // If the token is a number push it onto the stack
            if (!isOperator(token))
            {
                stack.push(token);
            }
            else
            {
                // Pop the two top entries
                Double d2 = Double.valueOf( (String)stack.pop() );
                Double d1 = Double.valueOf( (String)stack.pop() );

                //Get the result
                Double result = token.compareTo("+") == 0 ? d1 + d2 :
                                token.compareTo("-") == 0 ? d1 - d2 :
                                token.compareTo("*") == 0 ? d1 * d2 :
                                token.compareTo("%") == 0 ? d1 % d2 :
                                                d1 / d2;

                // Push result onto stack
                stack.push( String.valueOf( result ));
            }
        }

        this.result = Double.valueOf((String)stack.pop());
    }

    // Print the expression, terms, and result
    public String toString() {
        return (this.expression + "\n" +
                this.tokens.toString() + "\n" +
                this.reverse_polish.toString() + "\n" +
                this.result.toString());
    }

    // Tester method
    public static void main(String[] args) {
        Calculator simpleMath = new Calculator("100+200");
        System.out.println("Simple Math\n" + simpleMath);

        System.out.println();

        Calculator fractionMath = new Calculator("100.2 - 99.3");
        System.out.println("Fraction Math\n" + fractionMath);

        System.out.println();

        Calculator moduloMath = new Calculator("300 % 200");
        System.out.println("Modulo Math\n" + moduloMath);

        System.out.println();

        Calculator divisionMath = new Calculator("300/200");
        System.out.println("Division Math\n" + divisionMath);

        System.out.println();

        Calculator multiplicationMath = new Calculator("300 * 200");
        System.out.println("Multiplication Math\n" + multiplicationMath);
    }
}
