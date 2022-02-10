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
import com.nighthawk.csa.data.sarahfrq.frq2.sarahLightSequence;
import com.nighthawk.csa.data.sarahfrq.frq10.gcf;

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

        if (n == 1) {
            System.out.println("there is no FRQ1");
        }
        else if(n == 2){
            System.out.println(" \n a) 0101 0101 0101 \n b) gradshow.display() \n c) 001100110011 \n d)String resultSeq = gradShow.insertSegment(“1111 1111”,4);\n e) Int index = oldSeq.index(Segment);\\n\" +\n String newSeq = oldSeq.substring(0,index) + oldSeq.substring(index + segment.length());\\n \n f) Math.sqrt(a*a + b*b)\"");
        }
        else if(n == 3){
            System.out.println("frq3");
        }
        else if(n == 4){
            System.out.println("frq4");
        }
        else if(n == 5){
            System.out.println("frq5");
        }
        else if(n == 6){
            //creating the array using words given by CB
            String words[] = {"ten", "fading", "post", "card", "thunder", "hinge", "trailing", "batting"};

            //for loop to go through array
            // word: words allows to search substring of the strings
            for(String word : words){
                //lastIndexOf method allows to search for strings in array that have the last 3 letters as "ing" to be printed
                if(word.lastIndexOf("ing") == word.length() - 3){
                    System.out.println(word);
                }
            }
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

    @GetMapping("/sarah/gcf")
    public String sarahgcf(Model model, @RequestParam(
            name = "num1", required = false, defaultValue = "0") String num1,
                           @RequestParam(name = "num2", required = false, defaultValue = "0") String num2)
            throws IOException, InterruptedException, ParseException {

        int number1 = Integer.parseInt(num1);
        int number2 = Integer.parseInt(num2);

        gcf calculate = new gcf(number1, number2);

        int message = calculate.obtainGCF();
        /*
        String viewMessage = String.valueOf(message);*/
        model.addAttribute("message", "the GCF is " + message);
        return "individual/message";
    }

    @GetMapping("/sarah/answer")
    public String sarahanswer(Model model, @RequestParam(name = "frq2", required = false, defaultValue = "default") String frq2,
                              @RequestParam(name = "frq3", required = false, defaultValue = "default") String frq3,
                              @RequestParam(name = "frq4", required = false, defaultValue = "default") String frq4
    ) throws IOException, InterruptedException, ParseException {

        sarahLightSequence gradShow = new sarahLightSequence();
        gradShow.sarahLightSequence("0101 0101 0101");
        gradShow.display();
        gradShow.changeSequence("0011 0011 0011");
        String resultSeq = gradShow.insertSegment("1111 1111", 4);

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
        if (!frq4.equals("default")) {
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

    @GetMapping("/natashaInvitation")
    public String natashaInvitation(@RequestParam(name="hostname", required=false, defaultValue="default") String hostname, Model model) {
        model.addAttribute("hostname", hostname);
        return "individual/natashaInvitation";
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

    @GetMapping("/test")
    public String test() {
        return "/tutoring/test";
    }

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
        // Obsoleted Resource: https://support.google.com/programmable-search/answer/4513903?hl=en
        // Previously used to search the users


        return "/user/search";
    }

    @GetMapping("/calendar")
    public String calendar() {
        return "tutoring/calendar";
    }

    // ELASTIC SEARCH FOR THE PAGE


    // Commented out because it's not currently working

    /*
    public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

        Page<Article> findByAuthorsName(String name, Pageable pageable);

        @Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")
        Page<Article> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);
    }

    @Configuration
    @EnableElasticsearchRepositories(basePackages = "com.baeldung.spring.data.es.repository")
    @ComponentScan(basePackages = { "com.baeldung.spring.data.es.service" })
    public class Config {

        @Bean
        public RestHighLevelClient client() {
            ClientConfiguration clientConfiguration
                    = ClientConfiguration.builder()
                    .connectedTo("localhost:9200")
                    .build();

            return RestClients.create(clientConfiguration).rest();
        }

        @Bean
        public ElasticsearchOperations elasticsearchTemplate() {
            return new ElasticsearchRestTemplate(client());
        }
    }

    @Document(indexName = "blog", type = "article")
        public class Article {

            @Id
            private String id;

            private String title;

            @Field(type = FieldType.Nested, includeInParent = true)
            private List<Author> authors;

            // standard getters and setters
        }
*/


}