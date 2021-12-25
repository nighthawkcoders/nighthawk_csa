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
    // StringOps object
    StringOps string_ops = null;

    // Getter to transform string_ops object to JSON
    public JSONObject getBody() {
        JSONObject body = new JSONObject();
        body.put("data", string_ops.toString());
        body.put("title", string_ops.getTitle());
        body.put("status", string_ops.getStatus());
        body.put("events", string_ops.getEvents());
        return body;
    }

    public void stringEvent(JSONObject jo) {
        // Get string action
        String action = (String) jo.get("action");

        // Update string_ops based off of action
        switch (action) {
            case "new":  // new sequence
                String title = (String) jo.get("title");
                //avoid condition of an empty title
                if (title == null || title.length() == 0)
                    title = string_ops.getTitle();
                //create new object
                this.string_ops = new StringOps();
                this.string_ops.newStringSeq(title);
                break;

            case "init":  // init or update string sequence
                String init = (String) jo.get("new_sequence");
                this.string_ops.setStringSeq(init);
                break;

            case "append": // update string
                String add = (String) jo.get("append_segment");
                this.string_ops.appendSegment(add);
                break;

            case "insert": // insert segment at location
                String ins = (String) jo.get("insert_segment");
                int index = Integer.parseInt( (String) jo.get("insert_location") );
                string_ops.insertSegmentAt(ins, index);
                break;

            case "swap": // swap segment "out" for segment "in"
                String out = (String) jo.get("out_segment");
                String in = (String) jo.get("in_segment");
                string_ops.swapSegment(out, in);
                break;

            default:
                // noop
        }
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
        JSONObject jo = new JSONObject((Map) Objects.requireNonNull(request.getBody()));

        // process string sequence action(s)
        stringEvent(jo);

        // create JSON object of string sequence resulting data and metadata
        JSONObject body = this.getBody();

        // send ResponseEntity body and status message
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    // Inventor List
    public static StringOps inventorList() {
        // String initializer test
        StringsController seqObject = new StringsController();
        JSONObject jo = new JSONObject();

        // new object and set a title
        jo.put("action", "new");
        jo.put("title", "StringsController Inventor List");
        seqObject.stringEvent(jo);

        // new test
        jo.put("action", "init");
        jo.put("new_sequence", "Albert Einstein, Thomas Edison, Marie Curie");
        seqObject.stringEvent(jo);

        // update test
        jo.put("action", "append");
        jo.put("append_segment", ", Benjamin Franklin");
        seqObject.stringEvent(jo);

        // insert test
        jo.put("action", "insert");
        jo.put("insert_segment", "Alexander Graham Bell, ");
        jo.put("insert_location", "0" );
        seqObject.stringEvent(jo);

        // swap test
        jo.put("action", "swap");
        jo.put("out_segment", "Thomas Edison");
        jo.put("in_segment", "Nikola Tesla");
        seqObject.stringEvent(jo);

        return seqObject.string_ops;
    }

    // Class tester
    public static void main(String[] args) {
        StringOps inventors = inventorList();
        inventors.printHistory();
    }
}