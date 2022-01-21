package com.nighthawk.csa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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