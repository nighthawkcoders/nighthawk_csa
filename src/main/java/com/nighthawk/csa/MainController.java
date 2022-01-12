package com.nighthawk.csa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import java.util.HashMap;



import com.nighthawk.csa.data.avafrq.LightSequence;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class MainController {



    @GetMapping("/ava")
    public String Ava(Model model) throws IOException, InterruptedException, org.json.simple.parser.ParseException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.balldontlie.io/api/v1/season_averages?season=2021&player_ids[]=246"))
                .GET()
                .build();
        //rapidapi call

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        Object obj = new JSONParser().parse(response.body());
        JSONObject jo = (JSONObject) obj;
        JSONArray data = (JSONArray) jo.get("data");
        JSONObject firstRow = (JSONObject) data.get(0);


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

    @GetMapping("/ava/frqs")
    public String ava(@RequestParam(name = "frq2", required = false, defaultValue = "0") String frq2, Model model) {
        int n = Integer.parseInt(frq2);

        LightSequence gradshow = new LightSequence("0101 0101 0101");
        if (n == 1) {
            System.out.println("0101 0101 0101");
        }
        else if(n == 2){
            gradshow.display();
        }
        else if(n == 3){
            System.out.println("0011 0011 0011");
        }
        else if(n == 4){
            System.out.println("String resultSeq = gradShow.insertSegment(“1111 1111”,4);\n");
        }
        else if(n == 5){
            System.out.println("valid");
        }
        else if(n == 6){
            System.out.println("Math.sqrt(a*a + b*b)");
        }
        return "individual/avaFrq";

    }



    @GetMapping("/sarah")
    public String sarah(Model model) throws IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://icanhazdadjoke.com/"))
                .header("Accept", "text/plain")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        model.addAttribute("joke", response.body());

        return "individual/sarah";
    }

    @GetMapping("/sarah/answer")
    public String sarahanswer(Model model, @RequestParam(name = "frq2", required = false, defaultValue = "default") String frq2,
                              @RequestParam(name = "frq3", required = false, defaultValue = "default") String frq3,
                              @RequestParam(name = "frq4", required = false, defaultValue = "default") String frq4
                              ) throws IOException, InterruptedException, ParseException {


        // frq2
        class LightSequence{
            String seq;
            Boolean on = true;

            public void LightSequence(String seq){
                this.seq = seq;
            }

            public void display(){
                System.out.println(this.seq);
            }

            public void updateSequence(String seq){
                this.seq = seq;
            }

            public String insertSegment(String newSeq, Integer index){
                String originalString = this.seq;
                String newString = originalString.substring(0, index + 1)
                        + newSeq
                        + originalString.substring(index + 1);
                return newString;
            }
            public void changeSequence(String seq){
                if (this.on == true){
                    this.on = false;
                }
                else {
                    this.on = true;
                }
            }
        }

        LightSequence gradShow = new LightSequence();
        gradShow.LightSequence("0101 0101 0101");
        gradShow.display();

        gradShow.changeSequence("0011 0011 0011");

        String resultSeq = gradShow.insertSegment("1111 1111", 4);

        // frq3
        boolean rsvp = true;
        int selection;
        selection = 1;
        String option1;
        String option2;

        if (selection == 1) {
            System.out.println("beef");
        } else if ( selection == 2) {
            System.out.println("chicken");
        } else if (selection ==3) {
            System.out.println("pasta");
        } else{
            System.out.println("fitsh");
        }


        // frq4
        class frq4function {
            String str;
            Boolean repeatSequence = true;

            void longestStreak(String str) {
                int n = str.length();
                Character[] visited = new Character[256];
                repeatSequence = false;
                for (int j = 0; j < n; j++){
                    if (str.charAt(j - 1) == str.charAt(j)) {
                        visited[j] = str.charAt(j);
                    } else {
                        visited[j] = 0;
                    }
                }
                for (int i = 0; i < n; ++i) {
                    System.out.println(visited[i]);
                }
            }
        }

        String message = "nothing to say";
        if (!frq2.equals( "default")){
            if (frq2.equals("gradShow.display();")){
                message = "Correct answer for FRQ2!";
            }
            else {
                message = "incorrect answer for FRQ2";
            }
        }
        if (!frq3.equals("default")){
            if (frq3.equals("boolean rsvp = true;")){
                message = "boolean rsvp = true;";
            }
            else {
                message = "incorrect answer for FRQ3";
            }
        }
        if (!frq4.equals("default")){
            message = "class frq4function {\n" +
                    "            String str;\n" +
                    "            Boolean repeatSequence = true;\n" +
                    "\n" +
                    "            void longestStreak(String str) {\n" +
                    "                int n = str.length();\n" +
                    "                Character[] visited = new Character[256];\n" +
                    "                repeatSequence = false;\n" +
                    "                for (int j = 0; j < n; j++){\n" +
                    "                    if (str.charAt(j - 1) == str.charAt(j)) {\n" +
                    "                        visited[j] = str.charAt(j);\n" +
                    "                    } else {\n" +
                    "                        visited[j] = 0;\n" +
                    "                    }\n" +
                    "                }\n" +
                    "                for (int i = 0; i < n; ++i) {\n" +
                    "                    System.out.println(visited[i]);\n" +
                    "                }\n" +
                    "            }\n" +
                    "        }";

        }


        // message = frq2;
        model.addAttribute("message", message);
        return "individual/message";
    }

    @GetMapping("/risa")
    public String calc(Model model) throws IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://love-calculator.p.rapidapi.com/getPercentage?sname=Alice&fname=John"))
                .header("x-rapidapi-host", "love-calculator.p.rapidapi.com")
                .header("x-rapidapi-key", "a917dcdd11msh8cb88225ac662ebp143616jsn13600ff2f660")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        //convert response.body() to java hash map
        var calc = new ObjectMapper().readValue(response.body(), HashMap.class);


        //pass stats to view
        model.addAttribute("calc", calc);

        return "individual/risa";
    }

    @GetMapping("/ridhima")
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

    @GetMapping("/natasha")
    public String calculate(Model model) throws IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://love-calculator.p.rapidapi.com/getPercentage?sname=Alice&fname=John"))
                .header("x-rapidapi-host", "love-calculator.p.rapidapi.com")
                .header("x-rapidapi-key", "a917dcdd11msh8cb88225ac662ebp143616jsn13600ff2f660")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        //convert response.body() to java hash map
        var calc = new ObjectMapper().readValue(response.body(), HashMap.class);


        //pass stats to view
        model.addAttribute("calculate", calc);

        return "individual/natasha";
    }

    @GetMapping("/tutors")
    public String Tutors() {
        return "tutoring/tutors";
    }

    @GetMapping("/teamabout")
    public String unit3frq() {
        return "/tpt/draw";
    }

    @GetMapping("/services")
    public String services() {
        return "/tutoring/services";
    }

    @GetMapping("/subjecttutoring")
    public String subjecttutoring() {
        return "/services/subjecttutoring";
    }

    @GetMapping("/testprep")
    public String testprep() {
        return "/services/testprep";
    }


    @GetMapping("/schedulenow")
    public String schedulenow() {
        return "/services/schedulenow";}

    @GetMapping("/contactus")
    public String contactus() {
        return "/services/contact";
    }

    @GetMapping("/profileUser")
    public String profileUser() {
        return "/user/profileUser";
    }

    @GetMapping("/profileTutor")
    public String profileTutor() {
        return "/tutoring/profileTutor";
    }

    @GetMapping("/onlinetutoring")
    public String onlinetutoring() {
        return "/services/onlinetutoring";
    }


    @GetMapping("/search")
    public String search() {
        return "/user/search";
    }

    @GetMapping("/calendar")
    public String calendar() {
        return "/tutoring/calendar";
    }



}