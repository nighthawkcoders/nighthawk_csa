package com.nighthawk.csa.algorithm;

import com.nighthawk.csa.algorithm.fibonacciModel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class FibonacciController {

    public List<_Fibonacci> fibInit(int nth) {
        //Fibonacci objects created with different initializers
        List<_Fibonacci> fibList = new ArrayList<>();
        fibList.add(new FibFor(nth));
        fibList.add(new FibWhile(nth));
        fibList.add(new FibRecurse(nth));
        fibList.add(new FibStream(nth));

        return fibList;
    }

    // GET request,, parameters are passed within the URI
    @GetMapping("/fib")
    public String fib(@RequestParam(name="fibseq", required=false,  defaultValue="2") String fibseq, Model model) {
        //nth is fibonacci request
        int nth = Integer.parseInt(fibseq);

        //MODEL attributes are passed back html
        model.addAttribute("fibList", fibInit(nth));

        return "algorithm/fib"; //HTML render fibonacci results

    }

    // Console UI is run out of the same Controller
    public static void main(String[] args) {
        int nth = 20; //!!!make dynamic using consoleUI inputInt!!! 92 is max for long

        List<_Fibonacci> fibList = new FibonacciController().fibInit(nth);
        for (_Fibonacci fibonacci : fibList)
            fibonacci.print();  //Console output fibonacci results
    }
}