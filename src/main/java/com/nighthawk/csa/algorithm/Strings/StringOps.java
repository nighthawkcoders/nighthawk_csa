package com.nighthawk.csa.algorithm.Strings;
import java.util.ArrayList;
import java.util.List;

public class StringOps {
    private String sequence = null;
    private final List<String> events;

    /* Zero argument Constructor
     */
    public StringOps() {
        this.events = new ArrayList<>();
    }

    public List<String> getEvents() { return events; }

    /* Initialize or Swap sequence
     */
    public void setString(String sequence) {
        this.events.add(
                (this.sequence != null)     // ternary operator usage to avoid null sequence
                    ? "Sequence changed from: " + this.sequence +" to: " + sequence
                    : "Set up sequence object: " + sequence);

        // replace new sequence over existing sequence in object
        this.sequence = sequence;
    }

    /* Insert "in" segment at "position"
     */
    public void insertSegmentAt(String in, int position) {
        this.events.add ( "Insert into: " + this.sequence + " segment: " + in +" at position " + position );

        // create a gap to insert segment
        this.setString(
                this.sequence.substring(0, position) +
                in +
                this.sequence.substring(position)  // single argument includes 'till end of string
        );
    }

    /* Replace "out" segment with "in" segment
     */
    public void replaceSegment(String out, String in) {
        this.events.add ( "Replace: " + sequence + " old segment: '" + out +"' with new segment '" + in + "'");

        // find gap for out segment
        int index1 = this.sequence.indexOf(out);
        int index2 = index1 + out.length();

        // build front part and back part of new segemtn
        String front = this.sequence.substring(0, index1);
        String back = this.sequence.substring(index2); // single argument includes 'till end of string

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
        return this.sequence;
    }

    /* Console output helper method
     */
    public void printHistory() {
        for (String event: getEvents())
            System.out.println(event);
        System.out.println("Current: " + this);
    }

    public static StringOps frg2_simulation() {
        // Test1 construct object, zero argument constructor used to capture test event
        StringOps sequence = new StringOps();
        sequence.events.add("FRQ 2 LightSequence");
        sequence.events.add("\nTest (a, b): construct object + display sequence");
        sequence.setString("0101 0101 0101");

        // Test2 change content of object
        sequence.events.add("\nTest (c): update sequence");
        sequence.setString("0011 0011 0011");

        // Test3 insert into content of object
        sequence.events.add("\nTest (d): insert into sequence at position");
        sequence.insertSegmentAt("1111 1111", 4);

        // Test4 replace sequence with one that has proper spacing
        sequence.events.add("\nTest (f): remove segment from front, end, and middle");
        sequence.setString("1100000111");
        sequence.replaceSegment("11", "");
        sequence.setString("0000011");
        sequence.replaceSegment("11", "");
        sequence.setString("1100000111");
        sequence.replaceSegment("00", "");

        // History of events
        return sequence;
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