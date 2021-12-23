package com.nighthawk.csa.algorithm;

import com.nighthawk.csa.algorithm.Strings.StringOps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class StringsController {

    public StringOps stringInit(String sequence) {
        StringOps seqObj = new StringOps();
        seqObj.setString(sequence);
        return seqObj;
    }

    public StringOps frq2Init() {
        return StringOps.frg2_simulation();
    }

    // GET request,, parameters are passed within the URI
    @GetMapping("/strings")
    public String fib(@RequestParam(name="sequence", required=false,  defaultValue="") String sequence, Model model) {
        //MODEL attributes are passed back html
        model.addAttribute("object", frq2Init());
        return "algorithm/strings"; //HTML render fibonacci results
    }

    // Console UI is run out of the same Controller
    public static void main(String[] args) {
        // String Ops test
        System.out.println("StringsController Object Initialization Test\n");
        StringOps seqObj = new StringsController().stringInit("Albert Thomas Marie Wilma");
        seqObj.printHistory();
        System.out.println("\n");

        // FRQ2 test
        StringOps gradShow = new StringsController().frq2Init();
        gradShow.printHistory();
    }
}