package com.nighthawk.csa.mvc.StringOps;

import java.util.ArrayList;
import java.util.List;

public class StringSeqModel {
    private String stringSeq = null;
    private String title = "none";
    private String status = "none";
    private final List<String> events;

    // Zero argument Constructor
    public StringSeqModel() {
        this.events = new ArrayList<>();
    }

    // Getters
    public String getTitle() { return this.title; }
    public String getStatus() { return this.status; }
    public List<String> getEvents() { return this.events; }


    // Setter to add and Event to Events
    public void addEvent(String event) {
        this.status = event;
        this.events.add(event);
    }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setStatus(String status) { this.status = status; }

    // Set new string sequence properties
    public void newStringSeq(String title) {
        this.setTitle(title);
        this.addEvent( "Construct '" + title + "' new object" );
    }

    // Set/Init string sequence
    public void setStringSeq(String stringSeq) {
        if (stringSeq.length() == 0) {
            this.setStatus("Set string sequence failed: empty");
        } else if ( (this.stringSeq != null) && (stringSeq.compareTo(this.stringSeq) == 0) ) {
            this.setStatus("Set string sequence failed: no change");
        } else {
            this.addEvent(
                    (this.stringSeq == null)     // ternary operator usage to consider null string
                            ? "Set string sequence '" + stringSeq + "'"
                            : "Update string sequence from '" + this.stringSeq + "' to: '" + stringSeq +"'"
            );
            // replace new sequence over existing sequence in object
            this.stringSeq = stringSeq;
        }
    }

    // Append "in" segment to end of string sequence
    public void appendSegment(String in) {
        if ((in.length() == 0) ) {
            this.setStatus("Append string segment failed: empty");
        } else {
            this.addEvent("Append to '" +
                    this.stringSeq +
                    "' segment: '" +
                    in + "'"
            );

            // create a gap to insert segment
            this.setStringSeq(
                    this.stringSeq +
                    in
            );
        }
    }

    // Insert "in" segment at "position"
    public void insertSegmentAt(String in, int index) {
        if ( (in.length() == 0) ) {
            this.setStatus("Insert string segment failed: empty");
        } else if (index > this.stringSeq.length()) {
            this.setStatus("Insert index failed: out of bounds");
        } else {
            this.addEvent("Insert into '" +
                    this.stringSeq +
                    "' new segment: '" +
                    in +
                    "' at index " +
                    index
            );
            // create a gap to insert segment
            this.setStringSeq(
                    this.stringSeq.substring(0, index) +
                    in +
                    this.stringSeq.substring(index)  // single argument includes 'till end of string
            );
        }
    }

    // Swap "out" segment with "in" segment
    public void swapSegment(String out, String in) {
        if (out.length() == 0) {
            this.setStatus("Swap segment failed: 'out' segment is empty");
        } else if ( out.compareTo(in) == 0 ) {
            this.setStatus("Swap segment failed: 'out' and 'in' are equal");
        } else if ( !this.stringSeq.contains(out) ) {
            this.setStatus("Swap segment failed: 'out' segment not found within string sequence");
        } else {
            this.addEvent("Swap '" + this.stringSeq +
                    "' old segment '" + out +
                    "' with new segment '" + in + "'"
            );

            // find gap for out segment
            int index1 = this.stringSeq.indexOf(out);
            int index2 = index1 + out.length();

            // build front part and back part of new segemtn
            String front = this.stringSeq.substring(0, index1);
            String back = this.stringSeq.substring(index2); // single argument includes 'till end of string

            // concatenate "in" between front and back parts of original
            this.setStringSeq(
                    front +
                    in +
                    back
            );
        }
    }

    // StringOps object reference will return value of sequence attribute
    @Override
    public String toString() {
        return this.stringSeq;
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
    public static StringSeqModel frg2Simulation() {
        // Test 0 construct gradShow object
        StringSeqModel gradShow = new StringSeqModel();
        gradShow.addEvent("Test (a): construct gradShow object");
        gradShow.newStringSeq("StringOps FRQ 2 gradShow LightSequence");

        // Test1 set light sequence
        gradShow.addEvent("Test (b): set light sequence");
        gradShow.setStringSeq("0101 0101 0101");

        // Test2 change content of object
        gradShow.addEvent("Test (c): update light sequence");
        gradShow.setStringSeq("0011 0011 0011");

        // Test3 insert into content of object
        gradShow.addEvent("Test (d): insert segment into light sequence at position");
        gradShow.insertSegmentAt("1111 1111", 4);

        // Test4 replacing segment with light sequence.
        gradShow.addEvent("Test (f): remove segment from front, end, and middle of light sequence");
        gradShow.setStringSeq("1100000111");
        gradShow.swapSegment("11", "");
        gradShow.setStringSeq("0000011");
        gradShow.swapSegment("11", "");
        gradShow.setStringSeq("1100000111");
        gradShow.swapSegment("00", "");

        // History of events
        return gradShow;
    }

    // Class tester
    public static void main(String[] args) {
        // FRQ2 result simulation using StringOps Class
        StringSeqModel gradShow = StringSeqModel.frg2Simulation();

        // History of events
        gradShow.printHistory();
    }

}