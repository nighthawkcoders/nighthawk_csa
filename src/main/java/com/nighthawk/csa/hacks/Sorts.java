package com.nighthawk.csa.hacks;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;

public class Sorts {
    private final ArrayList<Integer> data = new ArrayList<>();
    private final Duration timeElapsed;

    public Sorts(int size) {
        Instant start = Instant.now();  // time capture -- start
        // build an array
        for (int i = 0; i < size; i++) {
            data.add((int)(Math.random() * (size+1)));
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
        int sum=0, time=0, TIMES=10, SIZE=5000;

        for(int i=0; i< TIMES; i++) {
            Sorts s = new Sorts(SIZE);
            for(int j = 0; j<s.getData().size(); j++) sum += s.getData().get(j);
            System.out.println("Average random: " + sum / ((i+1)*SIZE));
            System.out.println("Nanoseconds: " + s.getTimeElapsed());
            time += s.getTimeElapsed();
        }
        System.out.println("Average random: " + sum / (TIMES*SIZE));
        System.out.println("Average Nanosecond: " + time / TIMES);
    }

}
