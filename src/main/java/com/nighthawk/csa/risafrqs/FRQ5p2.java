package com.nighthawk.csa.risafrqs;

import java.util.Random;

public class FRQ5p2 {
    private static int passwordsGenerated = 0;
    private static Random random = new Random();
    private String prefix;
    private int length;
    public FRQ5p2(int length,String prefix) {
        this.prefix = prefix;
        this.length = length;
    }
    public FRQ5p2(int length) {
        this.prefix = "A";
        this.length = length;
    }
    public String pwGen(){
        String pwd= this.prefix+".";
        for(int i=1;i<=this.length;i++){
            pwd+=random.nextInt(10);
        }
        passwordsGenerated+=1;
        return pwd;
    }
    public int pwCount(){
        return passwordsGenerated;
    }
    public static void main(String[] args) {
        FRQ5p2 pw1 = new FRQ5p2(4,"chs");
        System.out.println(pw1.pwCount());
        System.out.println(pw1.pwGen());
        System.out.println(pw1.pwGen());
        System.out.println(pw1.pwCount());
        FRQ5p2 pw2 = new FRQ5p2(6);
        System.out.println(pw2.pwCount());
        System.out.println(pw2.pwGen());
        System.out.println(pw2.pwCount());
        System.out.println(pw1.pwCount());
    }
}
