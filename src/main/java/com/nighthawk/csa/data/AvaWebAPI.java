package com.nighthawk.csa.data;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class AvaWebAPI {

    // GET request, no parameters
    @GetMapping("/ava")
    public String Ava(Model model) throws IOException, InterruptedException, ParseException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.balldontlie.io/api/v1/season_averages?season=2021&player_ids[]=246"))
                .GET()
                .build();
        //rapidapi call

        System.out.println("hello");
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());

        //alternative #1: convert response.body() to java hash map
        //var map = new ObjectMapper().readValue(response.body(), HashMap.class);

        //alternative #2: convert response.body() to JSON object
       Object obj = new JSONParser().parse(response.body());
       JSONObject jo = (JSONObject) obj;
       JSONArray data = (JSONArray) jo.get("data");
       JSONObject firstRow = (JSONObject) data.get(0);

       System.out.println(firstRow.get("games_played"));

        //pass stats to view
        model.addAttribute("row", firstRow);
        model.addAttribute("games_played", firstRow.get("games_played"));  //illustrative of jo get
        model.addAttribute("min", firstRow.get("min"));
        model.addAttribute("reb", firstRow.get("reb"));
        model.addAttribute("ast", firstRow.get("ast"));
        model.addAttribute("stl", firstRow.get("stl"));
        model.addAttribute("blk", firstRow.get("blk"));

        return "individual/ava";
    }

}
