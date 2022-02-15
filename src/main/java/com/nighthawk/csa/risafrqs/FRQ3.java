package com.nighthawk.csa.risafrqs;

public class FRQ3 {
    private static String finalString;



    public static String FRQ3(boolean rsvp, int selection) {
        if (rsvp == true) {
            String finalString = "Thank you for attending! You will be served ";
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

    public static String run(boolean rsvp1, int selection1, boolean rsvp2, int selection2) {
        String option1 = FRQ3(rsvp1, selection1);
        String option2 = FRQ3(rsvp2,selection2);
        String attendance;


        if (option1 == option2) {
            attendance = "true";
        }
        else {
            attendance = "false";
        }
        String output = "Person 1: " + option1 + "\nPerson 2: " + option2 + "\n Are they both attending? " + attendance;
        return output;
    }

}
