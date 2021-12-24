package com.nighthawk.csa.algorithm.Strings;
import java.util.ArrayList;
import java.util.List;

public class StringOps {
    private String string = null;
    private final List<String> events;

    /* Zero argument Constructor
     */
    public StringOps() {
        this.events = new ArrayList<>();
    }

    public List<String> getEvents() { return this.events; }

    /* Initialize or Swap sequence
     */
    public void setString(String string) {
        this.events.add(
                (this.string != null)     // ternary operator usage to avoid null sequence
                    ? "Sequence changed from: " + this.string +" to: " + string
                    : "Set up sequence object: " + string);

        // replace new sequence over existing sequence in object
        this.string = string;
    }

    /* Insert "in" segment at "position"
     */
    public void insertSegmentAt(String in, int position) {
        this.events.add ( "Insert into: " + this.string + " segment: " + in +" at position " + position );

        // create a gap to insert segment
        this.setString(
                this.string.substring(0, position) +
                in +
                this.string.substring(position)  // single argument includes 'till end of string
        );
    }

    /* Replace "out" segment with "in" segment
     */
    public void replaceSegment(String out, String in) {
        this.events.add ( "Replace: " + this.string + " old segment: '" + out +"' with new segment '" + in + "'");

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

    /* StringOps object reference will return value of sequence attribute
     */
    @Override
    public String toString() {
        return this.string;
    }

    /* Console output helper method
     */
    public void printHistory() {
        for (String event: this.getEvents())
            System.out.println(event);
        System.out.println("Current: " + this);
    }

    public static StringOps frg2_simulation() {
        StringOps string_ops = new StringOps();
        string_ops.events.add("FRQ 2 LightSequence");

        // Test1 construct object, zero argument constructor used to capture and display event
        string_ops.events.add("\nTest (a, b): construct gradShow object + set light sequence");
        string_ops.setString("0101 0101 0101");

        // Test2 change content of object
        string_ops.events.add("\nTest (c): update light sequence");
        string_ops.setString("0011 0011 0011");

        // Test3 insert into content of object
        string_ops.events.add("\nTest (d): insert segment into light sequence at position");
        string_ops.insertSegmentAt("1111 1111", 4);

        // Test4 replacing segment with light sequence.
        string_ops.events.add("\nTest (f): remove segment from front, end, and middle of light sequence");
        string_ops.setString("1100000111");
        string_ops.replaceSegment("11", "");
        string_ops.setString("0000011");
        string_ops.replaceSegment("11", "");
        string_ops.setString("1100000111");
        string_ops.replaceSegment("00", "");

        // History of events
        return string_ops;
    }

    /* Class tester method
     */
    public static void main(String[] args) {
        // FRQ2 result simulation using StringOps Class
        StringOps gradShow = StringOps.frg2_simulation();

        // History of events
        gradShow.printHistory();
    }

}