package com.nighthawk.csa.algorithm.fibonacciModel;

import com.nighthawk.csa.consoleUI.ConsoleMethods;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
public abstract class _Fibonacci {
    int size;
    String name;
    int hashID;
    Duration timeElapsed;
    ArrayList<Long> list;
    HashMap<Integer, Object> hash;

    /*
     Construct the nth com.tri3.nighthawk.fibonacci number
     @param: nth constrained to 92 because of maximum long
     */
    public _Fibonacci(int nth) {
        this.size = nth;
        this.list = new ArrayList<>();
        this.hashID = 0;
        this.hash = new HashMap<>();
        //initialize fibonacci and time algorithm
        Instant start = Instant.now();  // time capture -- start
        this.init();
        Instant end = Instant.now();    // time capture -- end
        this.timeElapsed = Duration.between(start, end);
    }

    protected abstract void init();

    public void setData(long num) {
        list.add(num);
        hash.put(this.hashID++, list.clone());
    }

    public int getTimeElapsed() {
        return timeElapsed.getNano();
    }

    public long getNth() {
        return list.get(size - 1);
    }

    public Object getNthSeq(int i) {
        return hash.get(i);
    }

    public ArrayList<Long> getList() {
        return list;
    }

    public HashMap<Integer, Object> getHash() {
        return hash;
    }

    public void print() {
        ConsoleMethods.println("Init method = " + this.name);
        ConsoleMethods.println("Init time = " + this.getTimeElapsed());
        ConsoleMethods.println("Fibonacci Number " + this.size + " = " + this.getNth());
        ConsoleMethods.println("Fibonacci List = " + this.getList());
        ConsoleMethods.println("Fibonacci Hashmap = " + this.getHash());
        for (int i=0 ; i<this.size; i++ ) {
            ConsoleMethods.println("Fibonacci Sequence " + (i+1) + " = " + this.getNthSeq(i));
        }
    }
}