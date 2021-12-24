package com.nighthawk.csa.algorithm;

import com.nighthawk.csa.algorithm.Strings.StringOps;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class StringsController {
    StringOps object = null;

    public void stringInit(String sequence) {
        this.object = new StringOps();
        this.object.setString(sequence);
    }

    public void frq2Init() {
        this.object = StringOps.frg2_simulation();
    }

    public StringOps getObject() {
        return this.object;
    }

    // String initial method
    @GetMapping("/strings")
    public String strings(@RequestParam(name="sequence", required=false,  defaultValue="") String sequence, Model model) {
        //Set default as FRQ2 data
        frq2Init();
        model.addAttribute("object", getObject());
        return "algorithm/strings"; //HTML render fibonacci results
    }

    // Starting a new sequence
    @RequestMapping(value = "/api/strings/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> stringsNew(RequestEntity<Object> request) {
        // extract term from RequestEntity
        JSONObject json = new JSONObject((Map) Objects.requireNonNull(request.getBody()));
        int action = (int) json.get("action");

        // Build new sequence
        switch (action) {
            case 1:  // new sequence
                this.stringInit(
                        (String) json.get("new_sequence")
                );
                break;
            case 2: // update string
                this.getObject().setString(
                        (String) json.get("update_sequence")
                );
                break;
            case 3: // insert segment at location
                this.getObject().insertSegmentAt(
                        (String) json.get("insert_segment"),
                        Integer.parseInt((String) json.get("insert_location"))
                );
                break;
            case 4: // swap segment "out" for segment "in"
                this.getObject().replaceSegment(
                        (String) json.get("out_segment"),
                        (String) json.get("in_segment")
                );
                break;
            default:
                // noop
        }

        // Extract log, jsonify does not seem necessary with LIST
        List<String> events = this.getObject().getEvents();

        // return resulting list of events status, error checking should be added
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Console UI is run out of the same Controller
    public static void main(String[] args) {
        // String sequence test
        System.out.println("StringsController Object Initialization Test\n");
        StringsController seqObject = new StringsController();
        seqObject.stringInit("Albert Thomas Marie Wilma");
        seqObject.getObject().printHistory();

        System.out.println("\n");

        // FRQ2 test
        seqObject.frq2Init();
        seqObject.getObject().printHistory();
    }
}