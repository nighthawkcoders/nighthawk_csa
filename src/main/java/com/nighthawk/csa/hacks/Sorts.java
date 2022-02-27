package com.nighthawk.csa.hacks;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;

public class Sorts {
    private ArrayList<Integer> data = new ArrayList<>();
    private Duration timeElapsed;

    public Sorts(int size) {
        Instant start = Instant.now();  // time capture -- start
        // build an array
        for (int i = 0; i < size; i++) {
            data.add((int)(Math.random() * size));
        }
        data.sort(Comparator.naturalOrder());
        Instant end = Instant.now();    // time capture -- end
        this.timeElapsed = Duration.between(start, end);
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    public int getTimeElapsed() {
        return timeElapsed.getNano();
    }


    public static void main(String[] args) {
        Sorts s = new Sorts(5000);
        System.out.println(s.getData());
        System.out.println("Nanoseconds: " +s.getTimeElapsed());
    }

}
