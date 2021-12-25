package com.nighthawk.csa.algorithm;

import com.nighthawk.csa.algorithm.Math.FunMath;
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

import java.util.Map;
import java.util.Objects;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class StringsController {
    StringOps string_ops = null;

    // Getter for JSON body
    public JSONObject getBody() {
        JSONObject body = new JSONObject();
        body.put("data", string_ops.toString());
        body.put("title", string_ops.getTitle());
        body.put("status", string_ops.getStatus());
        body.put("events", string_ops.getEvents());
        return body;
    }

    public boolean stringEvent(JSONObject json) {
        // Get string action
        String action = (String) json.get("action");

        // Update string_ops based off of action
        boolean success = true;
        switch (action) {
            case "new":  // new sequence
                String title = (String) json.get("title");
                this.string_ops = new StringOps();
                this.string_ops.setTitle(title);
                string_ops.setStatus( "Construct '" + title + "' " + action + " object" );
                break;

            case "init":  // init or update string sequence
                String init = (String) json.get("new_sequence");
                this.string_ops.setStringSeq(init);
                break;

            case "append": // update string
                String add = (String) json.get("append_segment");
                this.string_ops.appendSegment(add);
                break;

            case "insert": // insert segment at location
                String ins = (String) json.get("insert_segment");
                int index = Integer.parseInt( (String) json.get("insert_location") );
                // test to insert segment has length and index is not beyond bounds of string
                if ( (ins.length() > 0) && (index <= string_ops.toString().length()) ) {
                    string_ops.insertSegmentAt(ins, index);
                } else { success = false; }
                break;

            case "swap": // swap segment "out" for segment "in"
                String out = (String) json.get("out_segment");
                String in = (String) json.get("in_segment");
                // test to see if swap is worth it, plus swap is in string_ops
                if ( ( out.length() > 0 ) && ( out.compareTo(in) != 0 ) &&  string_ops.toString().contains(out) ) {
                    string_ops.swapSegment(out, in);
                } else { success = false; }
                break;

            default:
                // noop
        }
        return success;
    }

    // String initial method
    @GetMapping("/strings")
    public String strings(@RequestParam(name="sequence", required=false,  defaultValue="") String sequence, Model model) {
        //Set default data randomly
        if ( FunMath.random(0,1) == 0)
            string_ops = inventorList();
        else
            string_ops = StringOps.frg2Simulation();
        model.addAttribute("object", string_ops);
        return "algorithm/strings"; //HTML render fibonacci results
    }

    // Starting a new sequence
    @RequestMapping(value = "/api/strings/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> stringsNew(RequestEntity<Object> request) {
        // extract json from RequestEntity
        JSONObject json = new JSONObject((Map) Objects.requireNonNull(request.getBody()));
        // perform string action
        String action = (String) json.get("action");
        if (!stringEvent(json)) {
            string_ops.setStatus( action + " failed, check data.");
        }

        // Extract object contents into JSON
        JSONObject body = this.getBody();

        // Response entity transfers body with status messages
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    // Inventor List
    public static StringOps inventorList() {
        // String initializer test
        StringsController seqObject = new StringsController();
        JSONObject json = new JSONObject();

        // new object and set a title
        json.put("action", "new");
        json.put("title", "StringsController Inventor List");
        seqObject.stringEvent(json);

        // new test
        json.put("action", "init");
        json.put("new_sequence", "Albert Einstein, Thomas Edison, Marie Curie");
        seqObject.stringEvent(json);

        // update test
        json.put("action", "append");
        json.put("append_segment", ", Benjamin Franklin");
        seqObject.stringEvent(json);

        // insert test
        json.put("action", "insert");
        json.put("insert_segment", "Alexander Graham Bell, ");
        json.put("insert_location", "0" );
        seqObject.stringEvent(json);

        // swap test
        json.put("action", "swap");
        json.put("out_segment", "Thomas Edison");
        json.put("in_segment", "Nikola Tesla");
        seqObject.stringEvent(json);

        return seqObject.string_ops;
    }

    // Class tester
    public static void main(String[] args) {
        StringOps inventors = inventorList();
        inventors.printHistory();
    }
}