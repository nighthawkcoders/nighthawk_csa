//package com.nighthawk.csa.mvc.database.chapters;
//
//import com.nighthawk.csa.mvc.database.ModelRepository;
//import com.nighthawk.csa.mvc.database.chapters.Chapter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import org.json.simple.JSONObject;
//
//import java.util.*;
//
//import java.text.SimpleDateFormat;
//
//@RestController
//@RequestMapping("/api/chapter")
//public class ChapterApiController {
//    /*
//    #### RESTful API ####
//    Resource: https://spring.io/guides/gs/rest-service/
//    */
//
//    // Autowired enables Control to connect HTML and POJO Object to database easily for CRUD
//    @Autowired
//    private ModelRepository repository;
//
//    /*
//    GET List of People
//     */
//    @GetMapping("/all")
//    public ResponseEntity<List<Chapter>> getChapter() {
//        return new ResponseEntity<>( repository.listAllChapters(), HttpStatus.OK);
//    }
//
//    /*
//    GET individual Person using ID
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<Chapter> getChapter(@PathVariable long id) {
//        return new ResponseEntity<>( repository.getChapter(id), HttpStatus.OK);
//    }
//
//    /*
//    DELETE individual Person using ID
//     */
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Object> deleteChapter(@PathVariable long id) {
//        repository.deleteChapter(id);
//        return new ResponseEntity<>( ""+ id +" deleted", HttpStatus.OK);
//    }
//
//    /*
//    POST Aa record by Requesting Parameters from URI
//     */
//    @PostMapping( "/post")
//    public ResponseEntity<Object> postChapter(@RequestParam("name") String name,
//                                              @RequestParam("password") String password) {
////        Date dob;
////        try {
////            dob = new SimpleDateFormat("MM-dd-yyyy").parse(dobString);
////        } catch (Exception e) {
////            return new ResponseEntity<>(dobString +" error; try MM-dd-yyyy", HttpStatus.BAD_REQUEST);
////        }
//        // A person object WITHOUT ID will create a new record with default roles as student
//        Chapter chapter = new Chapter(name, password);
//        repository.saveChapter(chapter);
//        return new ResponseEntity<>(name +" is created successfully", HttpStatus.CREATED);
//    }
//
//    /*
//    The personSearch API looks across database for partial match to term (k,v) passed by RequestEntity body
//     */
////    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
////    public ResponseEntity<Object> personSearch(RequestEntity<Object> request) {
////
////        // extract term from RequestEntity
////        JSONObject json = new JSONObject((Map) Objects.requireNonNull(request.getBody()));
////        String term = (String) json.get("term");
////
////        // custom JPA query to filter on term
////        List<Person> list = repository.listLikeNative(term);
////
////        // return resulting list and status, error checking should be added
////        return new ResponseEntity<>(list, HttpStatus.OK);
////    }
//}