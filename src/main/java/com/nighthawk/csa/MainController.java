package com.nighthawk.csa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nighthawk.csa.tutoring.Tutor;
import org.hibernate.loader.collection.BasicCollectionJoinWalker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.ArrayList;
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
    public String natashaInvitation(Model model, @RequestParam(name = "hostname", required = false, defaultValue = "host") String hostname,
                                    @RequestParam(name = "address", required = false, defaultValue = "at my house") String address,
                                    @RequestParam(name = "invited", required = false, defaultValue = "guest") String invited
    ) throws IOException, InterruptedException, ParseException {
        model.addAttribute("hostname", hostname);
        model.addAttribute("address", address);
        model.addAttribute("invited", invited);
        return "individual/natashaInvitation";
    }

    @RequestMapping(value={"/tutors", "/tutors/{sort}"})
    public String tutors(Model model, @PathVariable(required = false) String sort) {
        ArrayList<Tutor> englishTutorList = buildEnglishTutorList();
        ArrayList<Tutor> mathTutorList = buildMathTutorList();
        ArrayList<Tutor> chemistryTutorList = buildChemistryTutorList();
        ArrayList<Tutor> languageTutorList = buildLanguageTutorList();
        if(sort != null){
            sortTutorList(englishTutorList);
            sortTutorList(mathTutorList);
            sortTutorList(chemistryTutorList);
            sortTutorList(languageTutorList);
        }
        model.addAttribute("englishTutorList", englishTutorList);
        model.addAttribute("mathTutorList", mathTutorList);
        model.addAttribute("chemistryTutorList", chemistryTutorList);
        model.addAttribute("languageTutorList", languageTutorList);

        return "tutoring/tutors";
    }

    private ArrayList<Tutor> buildMathTutorList() {
        ArrayList<Tutor> tutorList = new ArrayList<Tutor>();
        Tutor tutor1 = new Tutor("Kaitlin Gil",
                "Statistics",
                "Kaitlin is a freshman in college and majoring in statistics. She took AP statistics in high school and received a 5 on the AP exam. She can help assist with homework, classwork, and go over problems.",
                "/images/tutorkaitlin.jpg");
        Tutor tutor2 = new Tutor("Calvin Wren",
                "Calculus",
                "Calvin is a senior in high school and took AP Calculus AB, BC, and college calculus courses in college by the time he was a junior in high school. He is very familiar with the course and loves to help students.",
                "/images/tutorcalvin.jpg");
        Tutor tutor3 = new Tutor("Mark Kellar",
                "Algebra & Geometry",
                "Mark is a freshman in high school majoring in mathematics and took every possible math class in high school. He performs very well on tests and his assistance can be beneficial to students.",
                "/images/tutormark.jpg");

        tutorList.add(tutor1);
        tutorList.add(tutor2);
        tutorList.add(tutor3);

        return tutorList;
    }

    private ArrayList<Tutor> buildChemistryTutorList() {
        ArrayList<Tutor> tutorList = new ArrayList<Tutor>();
        Tutor tutor1 = new Tutor("Cole Styles",
                "Organic Chemistry",
                "Kaitlin is a freshman in college and majoring in statistics. She took AP statistics in high school and received a 5 on the AP exam. She can help assist with homework, classwork, and go over problems.",
                "/images/tutorcole.jpg");
        Tutor tutor2 = new Tutor("Emma Wat",
                "Biochemistry",
                "Emma is a junior in college at UC Berkeley and majors in biochemistry. She is a responsible student who earned a 5 on her AP Chemistry and AP Biology AP exam. She can assist with classwork, homework, and test prep.",
                "/images/tutoremma.jpg");
        Tutor tutor3 = new Tutor("Justin Webs",
                "Physical Chemistry",
                "Justin is a senior in high school and earned a 5 on the AP Chem exam. He is very familiar with the course as he recently took the class. He can assist with homework and class work as well as test prep.",
                "/images/tutorjustin.jpg");

        tutorList.add(tutor1);
        tutorList.add(tutor2);
        tutorList.add(tutor3);

        return tutorList;
    }

    private ArrayList<Tutor> buildLanguageTutorList() {
        ArrayList<Tutor> tutorList = new ArrayList<Tutor>();
        Tutor tutor1 = new Tutor("Ed Sheshe",
                "Mandarin",
                "Ed is familiar with reading, writing, and speaking mandarin. He can help assist with classwork and school work, as well as teaching students how to hold a conversation.",
                "/images/tutored.jpg");
        Tutor tutor2 = new Tutor("Austin Dubois",
                "French",
                "Austin was born and raised in France and recently moved to the United Stated. He is very familiar with the French language and can help assist with reading, writing, and speaking French.",
                "/images/tutoraustin.jpg");
        Tutor tutor3 = new Tutor("Trish Lopez",
                "Spanish",
                "Trish lived in Mexico for 8 years and speaks spanish at home. She can assist with speaking, reading, and writing spanish, with practice in holding conversations.",
                "/images/tutortrish.jpg");

        tutorList.add(tutor1);
        tutorList.add(tutor2);
        tutorList.add(tutor3);

        return tutorList;
    }

    private ArrayList<Tutor> buildEnglishTutorList(){
        ArrayList<Tutor> tutorList = new ArrayList<Tutor>();

        Tutor tutor1 = new Tutor("Jennifer Mckennen",
                "History Essay",
                "Jennifer is a senior in high school and has been assisting students with their essays for four years. She enjoys poetry and historical fiction. Her favorite book is The Song of Achilles by Madeline Miller.",
                "/images/tutorjennifer.jpg");

        Tutor tutor2 = new Tutor("Kevin Kim",
                "Literature",
                "Kevin is a junior in high school and has been working with the WHAT Center for two years. He enjoys reading classic books and his favorite author is Jane Austen.",
                "/images/tutorkevin.jpg");

        Tutor tutor3 = new Tutor("Jonathan Do",
                "Creative Writing",
                "Jonathan is a senior in high school and has crafted stellar english essays in his AP Literature class. He enjoys creative writing and hopes to write his own book in the future!",
                "/images/tutorjonathan.jpg");

        tutorList.add(tutor1);
        tutorList.add(tutor2);
        tutorList.add(tutor3);
        return tutorList;
    }

    private void sortTutorList(ArrayList<Tutor> tutors){
        for (int i = 0; i < tutors.size(); i++) {
            for (int j = i+1; j < tutors.size(); j++) {
                if ((tutors.get(i).getName().compareTo(tutors.get(j).getName()) > 0) && (i != j)) {
                    Tutor tutor = tutors.get(i);
                    tutors.set(i, tutors.get(j));
                    tutors.set(j, tutor);
                }
            }
        }
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

    @GetMapping("/customerfeedback")
    public String customerfeedback() {
        return "/services/customerfeedback";
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