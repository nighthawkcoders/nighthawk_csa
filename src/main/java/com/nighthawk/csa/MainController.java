package com.nighthawk.csa;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class MainController {

    @GetMapping("/course/repos")
    public String courseRepos(Model model) {
        return "routes/repos";
    }

    @GetMapping("/course/prep")
    public String coursePrep(Model model) {
        model.addAttribute("url", "https://padlet.com/jmortensen7/csatime");
        return "routes/course";
    }

    @GetMapping("/course/tri1")
    public String courseTri1(Model model) {
        model.addAttribute("url", "https://padlet.com/jmortensen7/jho9v5wc4p9jgyn2");
        return "routes/course";
    }

    @GetMapping("/course/tri2")
    public String courseTri2(Model model) {
        model.addAttribute("url", "https://padlet.com/jmortensen7/csatime1_2");
        return "routes/course";
    }

    @GetMapping("/course/tri3")
    public String courseTri3(Model model) {
        model.addAttribute("url", "https://padlet.com/jmortensen7/csa2021t3");
        return "routes/course";
    }

}