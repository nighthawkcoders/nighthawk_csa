package com.nighthawk.csa.data;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class WebAPIController {

    // GET request, no parameters
    @GetMapping("/data/covid19")
    public String covid19(Model model) throws IOException, InterruptedException {
        // https://rapidapi.com/spamakashrajtech/api/corona-virus-world-and-india-data/
        //rapidapi setup:
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://corona-virus-world-and-india-data.p.rapidapi.com/api"))
                .header("x-rapidapi-key", "dec069b877msh0d9d0827664078cp1a18fajsn2afac35ae063")
                .header("x-rapidapi-host", "corona-virus-world-and-india-data.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        //rapidapi call
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        //convert to java hash map (key, value)
        var map = new ObjectMapper().readValue(response.body(), HashMap.class);
        //pass stats to view
        model.addAttribute("world", map.get("world_total"));
        model.addAttribute("countries", map.get("countries_stat"));
        model.addAttribute("body", response.body());

        return "data/covid19";
    }

}
