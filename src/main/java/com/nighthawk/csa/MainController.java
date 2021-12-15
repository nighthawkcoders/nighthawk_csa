package com.nighthawk.csa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nighthawk.csa.starters.ImageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class MainController {

    @GetMapping("/greet")    // CONTROLLER handles GET request for /greeting, maps it to greeting() and does variable bindings
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        // @RequestParam handles required and default values, name and data are class variables, data looking like JSON
        model.addAttribute("name", name);   // MODEL is passed to html
        return "starters/greet";                     // returns HTML VIEW (greeting)
    }

    @GetMapping("/image")
    public String image(Model model)  {
        //String web_server = "http://localhost:8080/";
        String web_server = "https://csa.nighthawkcodingsociety.com";
        List<ImageInfo> lii = new ArrayList<>();

        String file0 = "/images/Mona_Lisa.png";
        lii.add(new ImageInfo(file0, web_server+file0, 12));
        lii.get(0).read_image();

        String file1 = "/images/bulb_on.gif";
        lii.add(new ImageInfo(file1, web_server+file1, 2));
        lii.get(1).read_image();

        String file2 = "/images/bulb_off.png";
        lii.add(new ImageInfo(file2, web_server+file2, 7));
        lii.get(2).read_image();

        model.addAttribute("lii", lii);
        return "starters/image";
    }

    @GetMapping("/image/grayscale")
    public String image_grayscale(Model model) {
        //String web_server = "http://localhost:8080/";
        String web_server = "https://csa.nighthawkcodingsociety.com";
        List<ImageInfo> lii = new ArrayList<>();

        String file0 = "/images/Mona_Lisa.png";
        lii.add(new ImageInfo(file0, web_server+file0, 12));
        String str = lii.get(0).grayscale();
        model.addAttribute("str", str);
        return "starters/image_grayscale";
    }

    @GetMapping("/binary")    // CONTROLLER handles GET request for /greeting, maps it to greeting() and does variable bindings
    public String binary() {
        return "starters/binary";
    }

    @GetMapping("/snake")   // GET request
    public String snake() {
        return "algorithm/snake";
    }

    @GetMapping("/ava")   // GET request
    public String Ava() {
        return "individual/ava";
    }

    @GetMapping("/sarah")   // GET request
    public String Sarah() {
        return "individual/sarah";
    }

    @GetMapping("/risa")   // GET request
    public String coronavirus(Model model) throws IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://coronavirus-smartable.p.rapidapi.com/news/v1/US/"))
                .header("x-rapidapi-host", "coronavirus-smartable.p.rapidapi.com")
                .header("x-rapidapi-key", "a917dcdd11msh8cb88225ac662ebp143616jsn13600ff2f660")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        var coronavirus = new ObjectMapper().readValue(response.body(), HashMap.class);
        model.addAttribute("coronavirus", coronavirus);

        return "individual/risa";
    }

    @GetMapping("/ridhima")   // GET request
    public String space(Model model) throws IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://quotes15.p.rapidapi.com/quotes/random/"))
                .header("x-rapidapi-host", "quotes15.p.rapidapi.com")
                .header("x-rapidapi-key", "e25c6452c9msh8ae4033709e74bbp16b06cjsnb8c921a2f043")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        //convert response.body() to java hash map
        var quotes = new ObjectMapper().readValue(response.body(), HashMap.class);


        //pass stats to view
        model.addAttribute("quotes", quotes);

        return "individual/ridhima";
    }

    @GetMapping("/natasha")   // GET request
    public String Natasha() {
        return "individual/natasha";
    }

    @GetMapping("/tutors")   // GET request
    public String Tutors() {
        return "tutoring/tutors";
    }

    @GetMapping("/teamabout")   // GET request
    public String unit3frq() {
        return "/tpt/draw";
    }

    @GetMapping("/signup")   // GET request
    public String SignUp() {
        return "data/signup";
    }

}