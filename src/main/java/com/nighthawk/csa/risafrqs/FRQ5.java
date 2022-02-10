package com.nighthawk.csa.risafrqs;

public class FRQ5 {
    private static String hostName;
    private String address;
    private String name;
    private String age;

    public FRQ5(String n, String a, String c, String d)
    {
        hostName = n;
        address = a;
        name = c;
        age = d;
    }

    public String getHostName() { return hostName; }
    public String getAddress() { return address; }
    public String getName() { return name; }
    public String getAge() { return age; }


    public String setAddress(String ad)
    {
        address=ad;
        return address;
    }

    public String invite(String me)
    {
        return "Dear "+me+", please attend my event at "+address+". See you then, "+hostName+ ".";
    }


}
