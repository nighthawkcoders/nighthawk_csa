package com.nighthawk.csa.data;

// FRQ5

public class natashaInvitation {

        private String hostname;
        private String address;

        public natashaInvitation(){

        }

        public natashaInvitation(String n, String a)
        {
            hostname = n;
            address = a;
        }

        public void setHostname(String hostname){
            hostname = hostname;
        }

        public String getHostname()
        {
            return hostname;
        }

        public void setAddress(String a)
        {
            address = a;
        }

        public String invite(String n, String address, String hostname)
        {
            System.out.println("Dear " + n + ", please attend my event at "+ address + ". See you\nthen, " + hostname + ".");
            return "Dear " + n + ", please attend my event at "+ address + ". See you\nthen, " + hostname + ".";
        }

        public natashaInvitation(String address)
        {
            this.address = address;
            hostname = "Host";
        }
}

