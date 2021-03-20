package com.nighthawk.csa.algorithm;

/* MVC code that shows defining a simple Model, calling View, and this file serving as Controller
 * Web Content with Spring MVCSpring Example: https://spring.io/guides/gs/serving-web-con
 */

import com.nighthawk.csa.algorithm.fibonacciModel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class AlgoController {

    // GET request,, parameters are passed with URL
    @GetMapping("/fib")
    public String fib(@RequestParam(name="fibseq", required=false,  defaultValue="2") String fibseq, Model model) {
        //nth is fibonacci request
        int nth = Integer.parseInt(fibseq);

        //fibonacci objects
        List<_Fibonacci> fibs = new ArrayList<>();
        fibs.add(new FibFor(nth));
        fibs.add(new FibWhile(nth));
        fibs.add(new FibRecurse(nth));
        fibs.add(new FibStream(nth));

        //MODEL attributes are passed back html
        model.addAttribute("fibList", fibs);

        //render fibonacci results
        return "algorithm/fib";
    }
}