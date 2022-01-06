package com.nighthawk.csa.data;
import java.lang.*;

import org.springframework.web.bind.annotation.GetMapping;

public class avafrq {
    public static class LightSequence{
        private String sequence;

        public LightSequence(String sequence){
            this.sequence = sequence;

        }

        public String display(){
            return this.sequence;
        }

        public void changeSequence(String newSequence){
            this.sequence = newSequence;
        }

        public String insertSequence(String newSequence, int index){
            StringBuffer sb = new StringBuffer(this.sequence);
            sb.insert(index + 1, newSequence);
            this.sequence = sb.toString();
            return this.sequence;

        }

        public String removeSequence(String newSequence){
            int index = this.sequence.indexOf(newSequence);
            if (index == -1)
                return this.sequence;
            this.sequence = this.sequence.substring(0, index) + this.sequence.substring(index + newSequence.length());
            return this.sequence;
        }





    }

}
