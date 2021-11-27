package com.nighthawk.csa.data;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nighthawk.csa.data.SQL.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Built using article: https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html
// or similar: https://asbnotebook.com/2020/04/11/spring-boot-thymeleaf-form-validation-example/
@Controller
public class PersonSqlMvcController implements WebMvcConfigurer {

    // Autowired enables Control to connect HTML and POJO Object to Database easily for CRUD
    @Autowired
    private PersonSqlRepository repository;

    @GetMapping("/data/person")
    public String person(Model model) {
        List<Person> list = repository.listAll();
        model.addAttribute("list", list);
        return "data/person";
    }

    /*  The HTML template Forms and PersonForm attributes are bound
        @return - template for person form
        @param - Person Class
    */
    @GetMapping("/data/personcreate")
    public String personAdd(Person person) {
        return "data/personcreate";
    }

    /* Gathers the attributes filled out in the form, tests for and retrieves validation error
    @param - Person object with @Valid
    @param - BindingResult object
     */
    @PostMapping("/data/personcreate")
    public String personSave(@Valid Person person, BindingResult bindingResult) {
        // Validation of Decorated PersonForm attributes
        if (bindingResult.hasErrors()) {
            return "data/personcreate";
        }
        repository.save(person);
        // Redirect to next step
        return "redirect:/data/person";
    }

    @GetMapping("/data/personupdate/{id}")
    public String personUpdate(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", repository.get(id));
        return "data/personupdate";
    }

    @PostMapping("/data/personupdate")
    public String personUpdateSave(@Valid Person person, BindingResult bindingResult) {
        // Validation of Decorated PersonForm attributes
        if (bindingResult.hasErrors()) {
            return "data/personupdate";
        }
        repository.save(person);
        // Redirect to next step
        return "redirect:/data/person";
    }

    @GetMapping("/data/persondelete/{id}")
    public String personDelete(@PathVariable("id") long id) {
        repository.delete(id);
        return "redirect:/data/person";
    }

    /*
    #### RESTful API section ####
    Resource: https://spring.io/guides/gs/rest-service/
    */

    /*
    GET List of People
     */
    @RequestMapping(value = "/api/people/get")
    public ResponseEntity<List<Person>> getPeople() {
        return new ResponseEntity<>( repository.listAll(), HttpStatus.OK);
    }

    /*
    GET individual Person using ID
     */
    @RequestMapping(value = "/api/person/get/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable long id) {
        return new ResponseEntity<>( repository.get(id), HttpStatus.OK);
    }

    /*
    DELETE individual Person using ID
     */
    @RequestMapping(value = "/api/person/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deletePerson(@PathVariable long id) {
        repository.delete(id);
        return new ResponseEntity<>( ""+ id +" deleted", HttpStatus.OK);
    }


    /*
    POST Aa record by Requesting Parameters from URI
     */
    @RequestMapping(value = "/api/person/post", method = RequestMethod.POST)
    public ResponseEntity<Object> postPerson(@RequestParam("email") String email,
                                             @RequestParam("name") String name,
                                             @RequestParam("dob") String dobString) {
        Date dob;
        try {
            dob = new SimpleDateFormat("MM-dd-yyyy").parse(dobString);
        } catch (Exception e) {
            return new ResponseEntity<>(dobString +" error; try MM-dd-yyyy", HttpStatus.BAD_REQUEST);
        }
        // A person object WITHOUT ID will create a new record
        Person person = new Person(email, name, dob);
        repository.save(person);
        return new ResponseEntity<>(email +" is created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/data/person_search")
    public String person() {
        return "data/person_search";
    }

    /*
    personSearch has the intention to search across database for partial string match
     */
    @RequestMapping(value = "/api/person_search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> personSearch(RequestEntity<Object> request) {

        // This stub to calculate term as I can't figure out simple solution
        Pattern p = Pattern.compile("[^{}=,]+");
        Matcher m1 = p.matcher(Objects.requireNonNull(request.getBody()).toString());
        Map<String, String> map = new HashMap<>();
        String key="", value;
        int toggle = 0;
        while (m1.find()) {
            if ((toggle % 2) == 0) {
                key = m1.group();
            } else {
                value = m1.group();
                map.put(key, value);
            }
            toggle++;
        }

        // "term" is input from form
        String term = map.get("term");

        // jpa returns complete list of persons, a custom query is desired, these look interesting:
        // https://springframework.guru/spring-data-jpa-query/
        // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
        // https://www.baeldung.com/spring-data-jpa-query
        List<Person> list = repository.listAll();

        // this was quick as JPA stuff needs some study
        List<Person> list_filtered = new ArrayList<>();
        for (Person person : list) {
            if (person.getName().toLowerCase().contains(term.toLowerCase()) || person.getEmail().toLowerCase().contains(term.toLowerCase()))
                list_filtered.add(person);
        }

        // A person object WITHOUT ID will create a new record
        return new ResponseEntity<>(list_filtered, HttpStatus.OK);
    }

}