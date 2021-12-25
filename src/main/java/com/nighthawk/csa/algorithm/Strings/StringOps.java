package com.nighthawk.csa.algorithm.Strings;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StringOps {
    private String string = null;
    private String title = "none";
    private String status = "none";
    private final List<String> events;

    // Zero argument Constructor
    public StringOps() {
        this.events = new ArrayList<>();
    }

    // Getters
    public String getTitle() { return this.title; }
    public String getStatus() { return this.status; }
    public List<String> getEvents() { return this.events; }

    // Getter for JSON body
    public JSONObject getBody() {
        JSONObject body = new JSONObject();
        body.put("string", this.toString());
        body.put("title", this.getTitle());
        body.put("status", this.getStatus());
        body.put("events", this.getEvents());
        return body;
    }

    // Setter to add and Event to Events
    public void addEvent(String event) {
        this.status = event;
        this.events.add(event);
    }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setStatus(String status) { this.status = status; }
    public void setString(String string) {
        this.addEvent(
                (this.string == null)     // ternary operator usage to consider null string
                ? "Set up sequence object: " + string
                : "Sequence changed from: " + this.string +" to: " + string
        );
        // replace new sequence over existing sequence in object
        this.string = string;
    }

    // Insert "in" segment at "position"
    public void insertSegmentAt(String in, int position) {
        this.addEvent( "Insert into: " + this.string + " segment: " + in +" at position " + position );

        // create a gap to insert segment
        this.setString(
                this.string.substring(0, position) +
                in +
                this.string.substring(position)  // single argument includes 'till end of string
        );
    }

    // Swap "out" segment with "in" segment
    public void replaceSegment(String out, String in) {
        this.addEvent( "Replace: " + this.string + " old segment: '" + out +"' with new segment '" + in + "'");

        // find gap for out segment
        int index1 = this.string.indexOf(out);
        int index2 = index1 + out.length();

        // build front part and back part of new segemtn
        String front = this.string.substring(0, index1);
        String back = this.string.substring(index2); // single argument includes 'till end of string

        // concatenate "in" between front and back parts of original
        this.setString(
                front +
                in +
                back
        );
    }

    // StringOps object reference will return value of sequence attribute
    @Override
    public String toString() {
        return this.string;
    }

    // Console output helper method
    public void printHistory() {
        System.out.println(this.title);
        System.out.println("Current sequence: " + this);
        System.out.println("Last action: " + this.status);
        System.out.println();

        System.out.println("Change History ...");
        for (String event: this.getEvents())
            System.out.println(event);
    }

    // FRQ2 simulation
    public static StringOps frg2Simulation() {
        // Test 0 construct gradShow object
        StringOps gradShow = new StringOps();
        gradShow.setTitle("StringOps FRQ 2 LightSequence");
        gradShow.addEvent("Test (a): construct gradShow object");

        // Test1 set light sequence
        gradShow.addEvent("Test (b): set light sequence");
        gradShow.setString("0101 0101 0101");

        // Test2 change content of object
        gradShow.addEvent("Test (c): update light sequence");
        gradShow.setString("0011 0011 0011");

        // Test3 insert into content of object
        gradShow.addEvent("Test (d): insert segment into light sequence at position");
        gradShow.insertSegmentAt("1111 1111", 4);

        // Test4 replacing segment with light sequence.
        gradShow.addEvent("Test (f): remove segment from front, end, and middle of light sequence");
        gradShow.setString("1100000111");
        gradShow.replaceSegment("11", "");
        gradShow.setString("0000011");
        gradShow.replaceSegment("11", "");
        gradShow.setString("1100000111");
        gradShow.replaceSegment("00", "");

        // History of events
        return gradShow;
    }

    // Class tester
    public static void main(String[] args) {
        // FRQ2 result simulation using StringOps Class
        StringOps gradShow = StringOps.frg2Simulation();

        // History of events
        gradShow.printHistory();
    }

}