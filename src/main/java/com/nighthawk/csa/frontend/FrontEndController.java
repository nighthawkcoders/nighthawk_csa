package com.nighthawk.csa.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class FrontEndController {

    @GetMapping("/frontend/binary")    // CONTROLLER handles GET request for /greeting, maps it to greeting() and does variable bindings
    public String binary() {
        return "frontend/binary";
    }

    @GetMapping("/frontend/draw")   // GET request
    public String draw() {
        return "frontend/draw";
    }

    @GetMapping("/frontend/snake")   // GET request
    public String snake() {
        return "frontend/snake";
    }

    @GetMapping("/frontend/life")   // GET request
    public String life() {
        return "frontend/life";
    }

}