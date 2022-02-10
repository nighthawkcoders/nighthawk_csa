package com.nighthawk.csa.risafrqs;

        import java.util.ArrayList;
        import java.util.List;


public class Data {
    private static boolean rsvp1;
    private static boolean rsvp2;
    private static int selection1;
    private static int selection2;


    public boolean getRsvp1() { return rsvp1; }
    public boolean getRsvp2() { return rsvp2; }
    public int getSelection1() { return selection1; }
    public int getSelection2() { return selection2; }

    public void setRsvp1(boolean newRsvp1) { this.rsvp1 = newRsvp1; }
    public void setRsvp2(boolean newRsvp2) {
        this.rsvp2 = newRsvp2;
    }
    public void setSelection1(int newSelection1) {
        this.selection1 = newSelection1;
    }
    public void setSelection2(int newSelection2) {
        this.selection2 = newSelection2;
    }

    public static String FRQ3(boolean rsvp, int selection) {
        String finalString;
        if (rsvp == true) {
            finalString = "Thank you for attending! You will be served ";
            if (selection == 3) {
                finalString = finalString.concat("pasta.");
            }
            else if (selection == 2) {
                finalString = finalString.concat("chicken.");
            }
            else {
                finalString = finalString.concat("beef.");
            }
        }
        else {
            finalString = "Sorry you can't make it";
        }
        return finalString;
    }

    public static String run(boolean rsvp1, boolean rsvp2, int selection1, int selection2) {
        String option1 = FRQ3(rsvp1, selection1);
        String option2 = FRQ3(rsvp2,selection2);
        String attendance;

        if (option1 == option2) {
            attendance = "Yes!";
        }
        else {
            attendance = "No.";
        }
        String output = "Person 1: " + option1 + "\nPerson 2: " + option2 + "\n Are they both attending? " + attendance;
        return output;
    }
    @Override
    public String toString() {
        return String.format(String.valueOf(rsvp1), rsvp2, selection1, selection2);
    }


}
