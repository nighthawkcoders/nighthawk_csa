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
    StringOps string_ops = null;

    public void stringInit(String sequence) {
        this.string_ops = new StringOps();
        this.string_ops.setString(sequence);
    }

    public void frq2Init() {
        this.string_ops = StringOps.frg2_simulation();
    }

    // String initial method
    @GetMapping("/strings")
    public String strings(@RequestParam(name="sequence", required=false,  defaultValue="") String sequence, Model model) {
        //Set default as FRQ2 data
        frq2Init();
        model.addAttribute("object", string_ops);
        return "algorithm/strings"; //HTML render fibonacci results
    }

    // Starting a new sequence
    @RequestMapping(value = "/api/strings/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> stringsNew(RequestEntity<Object> request) {
        // extract term from RequestEntity
        JSONObject json = new JSONObject((Map) Objects.requireNonNull(request.getBody()));
        String action = (String) json.get("action");

        // Build new sequence
        switch (action) {
            case "new":  // new sequence
                String nu = (String) json.get("new_sequence");
                // test to make sure new sequence contains characters
                if (nu.length() > 0)
                    this.stringInit( nu );
                break;

            case "update": // update string
                String upd = (String) json.get("update_sequence");
                // test to ensure string is not empty and not the same as current
                if ((upd.length() > 0) && (upd.compareTo(string_ops.toString()) != 0) )
                    string_ops.setString( upd );
                break;

            case "insert": // insert segment at location
                String ins = (String) json.get("insert_segment");
                int index = Integer.parseInt( (String) json.get("insert_location") );
                // test to insert segment has length and index is not beyond bounds of string
                if ( (ins.length() > 0) && (index <= string_ops.toString().length()) )
                    string_ops.insertSegmentAt( ins, index );
                break;

            case "swap": // swap segment "out" for segment "in"
                String out = (String) json.get("out_segment");
                String in = (String) json.get("in_segment");
                // test to see if swap is worth it, plus swap is in string_ops
                if ( ( out.length() > 0 ) && ( out.compareTo(in) != 0 ) &&  string_ops.toString().contains(out) )
                    string_ops.replaceSegment( out, in );
                break;

            default:
                // noop
        }

        // Extract log, jsonify does not seem necessary with LIST
        List<String> events = string_ops.getEvents();

        // return resulting list of events status, error checking should be added
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Console UI is run out of the same Controller
    public static void main(String[] args) {
        // String sequence test
        System.out.println("StringsController Object Initialization Test\n");
        StringsController seqObject = new StringsController();
        seqObject.stringInit("Albert Thomas Marie Wilma");
        seqObject.string_ops.printHistory();

        System.out.println("\n");

        // FRQ2 test
        seqObject.frq2Init();
        seqObject.string_ops.printHistory();
    }
}