//package com.nighthawk.csa.mvc.database.notes;
//
//import com.nighthawk.csa.mvc.database.ModelRepository;
//import com.nighthawk.csa.mvc.database.notes.Notes;
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
//@RequestMapping("/api/notes")
//public class NotesApiController {
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
//    @GetMapping("/")
//    @ResponseBody
//    public ResponseEntity<List<Notes>> getNotes(@RequestParam String id) {
//        return new ResponseEntity<>( repository.listAllNotes(), HttpStatus.OK);
//    }
//
//    /*
//    GET individual Person using ID
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<Notes> getNotes(@PathVariable long id) {
//        return new ResponseEntity<>( repository.getNotes(id), HttpStatus.OK);
//    }
//
//    /*
//    DELETE individual Person using ID
//     */
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Object> deleteNotes(@PathVariable long id) {
//        repository.deleteNotes(id);
//        return new ResponseEntity<>( ""+ id +" deleted", HttpStatus.OK);
//    }
//
//    /*
//    POST Aa record by Requesting Parameters from URI
//     */
//    @PostMapping( "/post")
//    public ResponseEntity<Object> postNotes(@RequestParam("name") String name,
//                                              @RequestParam("link") String link,
//                                            @RequestParam("chapter") String chapter) {
////        Date dob;
////        try {
////            dob = new SimpleDateFormat("MM-dd-yyyy").parse(dobString);
////        } catch (Exception e) {
////            return new ResponseEntity<>(dobString +" error; try MM-dd-yyyy", HttpStatus.BAD_REQUEST);
////        }
//        // A person object WITHOUT ID will create a new record with default roles as student
//        Notes notes = new Notes(name, link, chapter);
//        repository.saveNotes(notes);
//        return new ResponseEntity<>(name +" is created successfully", HttpStatus.CREATED);
//    }
//
//
//}