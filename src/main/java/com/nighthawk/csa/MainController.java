package com.nighthawk.csa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class MainController {

    @GetMapping("/greet")    // CONTROLLER handles GET request for /greeting, maps it to greeting() and does variable bindings
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        // @RequestParam handles required and default values, name and model are class variables, model looking like JSON
        model.addAttribute("name", name);   // MODEL is passed to html
        return "starters/greet";                     // returns HTML VIEW (greeting)
    }

    // GET request, no parameters
    @GetMapping("/covid19")
    public String covid19(Model model) throws IOException, InterruptedException {
        //rapidapi setup
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://corona-virus-world-and-india-data.p.rapidapi.com/api"))
                .header("x-rapidapi-key", "dec069b877msh0d9d0827664078cp1a18fajsn2afac35ae063")
                .header("x-rapidapi-host", "corona-virus-world-and-india-data.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        //rapidapi call
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        //convert to java hash map
        HashMap map = new ObjectMapper().readValue(response.body(), HashMap.class);
        //pass country stats to view
        model.addAttribute("data", map);
        model.addAttribute("world", map.get("world_total"));
        model.addAttribute("countries", map.get("countries_stat"));
        return "starters/covid19";
    }

    @GetMapping("/snake")   // GET request
    public String snake() {
        return "starters/snake";
    }

    @GetMapping("/course/repos")
    public String courseRepos(Model model) {
        return "course/repos";
    }

    @GetMapping("/course/deploy")
    public String courseDeploy(Model model) {
        return "course/deploy";
    }

    @GetMapping("/course/prep")
    public String coursePrep(Model model) {
        model.addAttribute("url", "https://padlet.com/jmortensen7/csatime");
        return "course/timelines";
    }

    @GetMapping("/course/tri1")
    public String courseTri1(Model model) {
        model.addAttribute("url", "https://padlet.com/jmortensen7/csamvc");
        return "course/timelines";
    }

    @GetMapping("/course/tri2")
    public String courseTri2(Model model) {
        model.addAttribute("url", "https://padlet.com/jmortensen7/csaweb");
        return "course/timelines";
    }

    @GetMapping("/course/tri3")
    public String courseTri3(Model model) {
        model.addAttribute("url", "https://padlet.com/jmortensen7/csa2021t3");
        return "course/timelines";
    }

}