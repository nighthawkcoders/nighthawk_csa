package com.nighthawk.csa.data.sarahfrq;

public class frq2 {
    // frq2
    public static class sarahLightSequence{
        String seq;
        Boolean on = true;

        public void sarahLightSequence(String seq){
            this.seq = seq;
        }

        public void display(){
            System.out.println(this.seq);
        }

        public void updateSequence(String seq){
            this.seq = seq;
        }

        public String insertSegment(String newSeq, Integer index){
            String originalString = this.seq;
            String newString = originalString.substring(0, index + 1)
                    + newSeq
                    + originalString.substring(index + 1);
            return newString;
        }
        public void changeSequence(String seq){
            if (this.on == true){
                this.on = false;
            }
            else {
                this.on = true;
            }
        }
    }
}
