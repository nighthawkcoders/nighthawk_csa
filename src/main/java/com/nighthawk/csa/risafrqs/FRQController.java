package com.nighthawk.csa.risafrqs;

        import java.util.ArrayList;
        import java.util.Arrays;

        import org.springframework.ui.Model;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.ModelAttribute;
        import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FRQController {

    @GetMapping("/FRQ3")
    public String FRQ3(Model model) {
        Data data = new Data();
        model.addAttribute("data", data);
        System.out.println(data);
        return "FRQ3";
    }

    @PostMapping("/FRQ3")
    public String submitForm(@ModelAttribute("data") Data data, Model model) {
        System.out.println(data);
        String result = Data.run(data.getRsvp1(), data.getRsvp2(), data.getSelection1(), data.getSelection2());
        model.addAttribute("result", result);
        return "FRQ3result";
    }


}
