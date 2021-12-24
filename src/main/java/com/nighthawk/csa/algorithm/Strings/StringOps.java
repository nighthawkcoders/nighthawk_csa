package com.nighthawk.csa.algorithm.Strings;
import java.util.ArrayList;
import java.util.List;

public class StringOps {
    private String string = null;
    private final List<String> events;

    // Zero argument Constructor
    public StringOps() {
        this.events = new ArrayList<>();
    }

    // Getter for Events
    public List<String> getEvents() { return this.events; }

    // Setter to add and Event to Events
    public void addEvent(String event) { this.events.add(event); }

    // Set string action
    public void setString(String string) {
        this.addEvent(
                (this.string != null)     // ternary operator usage to avoid null sequence
                    ? "Sequence changed from: " + this.string +" to: " + string
                    : "Set up sequence object: " + string);

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
        for (String event: this.getEvents())
            System.out.println(event);
        System.out.println("Current: " + this);
    }

    // FRQ2 simulation
    public static StringOps frg2Simulation() {
        // Test 0 construct gradShow object
        StringOps gradShow = new StringOps();
        gradShow.addEvent("StringOps FRQ 2 LightSequence");
        gradShow.addEvent("\nTest (a): construct gradShow object");

        // Test1 set light sequence
        gradShow.addEvent("\nTest (b): set light sequence");
        gradShow.setString("0101 0101 0101");

        // Test2 change content of object
        gradShow.addEvent("\nTest (c): update light sequence");
        gradShow.setString("0011 0011 0011");

        // Test3 insert into content of object
        gradShow.addEvent("\nTest (d): insert segment into light sequence at position");
        gradShow.insertSegmentAt("1111 1111", 4);

        // Test4 replacing segment with light sequence.
        gradShow.addEvent("\nTest (f): remove segment from front, end, and middle of light sequence");
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