package com.nighthawk.csa.algorithm;
import java.util.ArrayList;

public class StringSequence {
    private String sequence = null;
    private final ArrayList<String> events;

    /* Zero argument Constructor
     */
    public StringSequence() {
        this.events = new ArrayList<>();
    }

    /* Initialize or Swap sequence
     */
    public void setSequence(String sequence) {
        this.events.add(
                (this.sequence != null)     // ternary operator usage to avoid null sequence
                    ? "Sequence changed from: " + this.sequence +" to: " + sequence
                    : "Set up sequence object: " + sequence);

        // replace new sequence over existing sequence in object
        this.sequence = sequence;
    }

    /* Insert "segment" at "position"
     */
    public void insertSequenceAt(String segment, int position) {
        this.events.add ( "Insert into: " + this.sequence + " segment: " + segment +" at " + position );

        // create a gap to insert segment
        this.setSequence(
                this.sequence.substring(0, position) +
                segment +
                this.sequence.substring(position)
        );
    }

    /* Replace "out" segment with "in" segment
     */
    public void replaceSequence(String out, String in) {
        if (in.length()!=0)
            this.events.add ( "Replace: " + sequence + " old segment: '" + out +"' with new segment '" + in + "'");
        else
            this.events.add ( "Replace: " + sequence + " old segment: '" + out +"' with new segment " + "''");

        // find gap for out segment
        int index1 = this.sequence.indexOf(out);
        int index2 = index1 + out.length();

        // build front part and back part of new segemtn
        String front = this.sequence.substring(0, index1);
        String back = this.sequence.substring(index2); // automatically goes to end of string

        // concatenate "in" between front and back parts of original
        this.setSequence(
                front +
                in +
                back
        );
    }

    /* StringSequence object reference will return value of sequence attribute
     */
    @Override
    public String toString() {
        return this.sequence;
    }

    /* Console output helper method
     */
    public void printHistory() {
        for (String event: events)
            System.out.println(event);
        System.out.println("Current: " + this);
    }

    public static StringSequence frg2_simulation() {
        // Test1 construct object, zero argument constructor used to capture test event
        StringSequence sequence = new StringSequence();
        sequence.events.add("LightSequence Test (a, b): construct object + display sequence");
        sequence.setSequence("0101 0101 0101");

        // Test2 change content of object
        sequence.events.add("LightSequence Test (c): update sequence");
        sequence.setSequence("0011 0011 0011");

        // Test3 insert into content of object
        sequence.events.add("LightSequence Test (d): insert into sequence at position");
        sequence.insertSequenceAt("1111 1111", 4);

        // Test4 replace sequence with one that has proper spacing
        sequence.events.add("LightSequence Test (f): remove segment from front, end, and middle");
        sequence.setSequence("1100000111");
        sequence.replaceSequence("11", "");
        sequence.setSequence("0000011");
        sequence.replaceSequence("11", "");
        sequence.setSequence("1100000111");
        sequence.replaceSequence("00", "");

        // History of events
        return sequence;
    }

    /* Class tester method
     */
    public static void main(String[] args) {
        // FRQ2 result simulation using StringSequence Class
        StringSequence gradShow = StringSequence.frg2_simulation();

        // History of events
        gradShow.printHistory();
    }

}