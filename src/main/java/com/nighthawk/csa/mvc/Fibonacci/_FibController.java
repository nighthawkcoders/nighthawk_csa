package com.nighthawk.csa.mvc.Fibonacci;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class _FibController {

    public List<FibAbstractModel> fibInit(int nth) {
        //Fibonacci objects created with different initializers
        List<FibAbstractModel> fibList = new ArrayList<>();
        fibList.add(new FibForModel(nth));
        fibList.add(new FibWhileModel(nth));
        fibList.add(new FibRecurseModel(nth));
        fibList.add(new FibStreamModel(nth));

        return fibList;
    }

    // GET request,, parameters are passed within the URI
    @GetMapping("/mvc/fibonacci")
    public String fib(@RequestParam(name="fibseq", required=false,  defaultValue="2") String fibseq, Model model) {
        //nth is fibonacci request
        int nth = Integer.parseInt(fibseq);

        //MODEL attributes are passed back html
        model.addAttribute("fibList", fibInit(nth));

        return "mvc/fibonacci"; //HTML render fibonacci results

    }

    // Console UI is run out of the same Controller
    public static void main(String[] args) {
        int nth = 20; //!!!make dynamic using utility inputInt!!! 92 is max for long

        List<FibAbstractModel> fibList = new _FibController().fibInit(nth);
        for (FibAbstractModel fibonacci : fibList)
            fibonacci.print();  //Console output fibonacci results
    }
}