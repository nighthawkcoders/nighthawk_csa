package com.nighthawk.csa.mvc.fibonacci;

import com.nighthawk.csa.utility.ConsoleMethods;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;

/*
 _Fibonacci class contains generalized logic to capture and analyze a fibonacci sequence.
 _Fibonacci is an "abstract" class, meaning it can't be initialized directly
 The "_" in name is used to push this "Parent" Class to top of package file listing.
 */
@Getter  // this will enable standard Getters on attributes in Class in form "getName" where "name" is attribute
public abstract class FibAbstractModel {
    int size;
    String name;
    int hashID;
    Duration timeElapsed;
    ArrayList<Long> list;
    HashMap<Integer, Object> hash;

    /*
     Zero parameter constructor uses Telescoping technique to allow setting of the required value nth
     @param: none
     */
    public FibAbstractModel() {
        this( 20);
    }

    /*
     Construct the nth fibonacci number
     @param: nth number, the value is constrained to 92 because of overflow in a long
     */
    public FibAbstractModel(int nth) {
        this.size = nth;
        this.list = new ArrayList<>();
        this.hashID = 0;
        this.hash = new HashMap<>();
        //initialize fibonacci and time mvc
        Instant start = Instant.now();  // time capture -- start
        this.init();
        Instant end = Instant.now();    // time capture -- end
        this.timeElapsed = Duration.between(start, end);
    }

    /*
     Method is abstract as init() requires extender to define their own fibonacci mvc
     Method is protected as it is only authorized to someone who extends the class
     */
    protected abstract void init();

    /*
     Number is added to fibonacci sequence, current state of "list" is added to hash for hashID "num"
     */
    public void setData(long num) {
        list.add(num);
        hash.put(this.hashID++, list.clone());
    }

    /*
     Custom Getter for timeElapsed in init process, timeElapsed.getNano() is part of Duration class
     */
    public int getTimeElapsed() {
        return timeElapsed.getNano();
    }

    /*
     Custom Getter to return last element in fibonacci sequence
     */
    public long getNth() {
        return list.get(size - 1);
    }

    /*
     Custom Getter to return last fibonacci sequence in HashMap
     */
    public Object getNthSeq(int i) {
        return hash.get(i);
    }

    /*
     Console/Terminal supported print method
     */
    public void print() {
        ConsoleMethods.println("Init method = " + this.name);
        ConsoleMethods.println("Init time = " + this.getTimeElapsed());
        ConsoleMethods.println("fibonacci Number " + this.size + " = " + this.getNth());
        ConsoleMethods.println("fibonacci List = " + this.getList());
        ConsoleMethods.println("fibonacci Hashmap = " + this.getHash());
        for (int i=0 ; i<this.size; i++ ) {
            ConsoleMethods.println("fibonacci Sequence " + (i+1) + " = " + this.getNthSeq(i));
        }
    }

    /*
    Tester class method.  This calls "main" class of each of the extended classes in the package
     */
    public static void main(String[] args) {
        FibForModel.main(null);
        FibRecurseModel.main(null);
        FibStreamModel.main(null);
        FibWhileModel.main(null);
    }
}