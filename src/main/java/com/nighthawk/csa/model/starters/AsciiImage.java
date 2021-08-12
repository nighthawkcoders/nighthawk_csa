package com.nighthawk.csa.model.starters;

import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class AsciiImage {

    public int SCALE_FACTOR = 2;

    public URL url;

    public int height;
    public int width;
    public int[][][] rgb;


    public AsciiImage(String url, int SCALE_FACTOR) throws MalformedURLException {
        this.url = new URL(url);
        this.SCALE_FACTOR = SCALE_FACTOR;
        read_image();
    }

    public String[] convert_to_ascii(){
        int[][] gs = this.to_grayscale();
        int[][] scaled_gs = this.scale(gs);
        return  this.to_ascii_array(scaled_gs);
    }


    public void read_image(){

        try{

            BufferedImage img = ImageIO.read(url);

            height = img.getHeight();
            width = img.getWidth();

            //System.out.println(Integer.toString(this.height) + " " + Integer.toString(this.width));

            rgb = new int[height][width][3];


            for (int y=0; y < height; y++){
                for(int x=0; x < width; x++){
                    int pixel = img.getRGB(x, y);
                    Color color = new Color(pixel, true);

                    rgb[y][x][0] = color.getRed();
                    rgb[y][x][1] = color.getGreen();
                    rgb[y][x][2] = color.getBlue();

                }
            }

        }catch (Exception e) {
            System.out.println(e);
        }

    }



    public int[][] to_grayscale(){

        int[][] gs = new int[height][width];

        for (int y=0; y < height; y++){
            for(int x=0; x < width; x++){

                int r = rgb[y][x][0];
                int g = rgb[y][x][1];
                int b = rgb[y][x][2];

                int avg = (int) (r+g+b)/3;

                gs[y][x] = avg;

            }
        }

        return gs;
    }

    public int[][] scale(int[][] gs){


        int w_scaled = (int) width/SCALE_FACTOR;
        int h_scaled = (int) height/SCALE_FACTOR;

        int[][] scaled = new int[h_scaled][w_scaled];

        for(int w=0; w<w_scaled; w++){

            for(int h=0; h<h_scaled; h++){ //looping over blocks

                Integer sum = 0;

                for(int x=(w)*SCALE_FACTOR; x<(w+1)*SCALE_FACTOR; x++){ //looping over the induvidual coordinates in block
                    for(int y=(h)*SCALE_FACTOR; y<(h+1)*SCALE_FACTOR; y++){

                        //System.out.println("("+Integer.toString(x)+", "+Integer.toString(y)+")");
                        sum = sum + gs[y][x];

                    }
                } //end loop 2


                scaled[h][w] = (int) sum/(SCALE_FACTOR*SCALE_FACTOR);


            }
        } //end loop 1

        //System.out.println(Arrays.deepToString(scaled));

        return scaled;

    }


    public void to_ascii(int[][] scaled_gs){

        String INTENSITY_MAP = "@#$&?^}{><*`'~=+-_,. "; // " .,_-+=~'`*<>{}^?&$#@"
        int INTENSITY_BIN = (int) 255/INTENSITY_MAP.length();


        for (int[] x: scaled_gs){
            for(int v: x){
                int c = (int) v/INTENSITY_BIN -1;
                if(c >=0 ){
                    System.out.print(""+INTENSITY_MAP.charAt(c)+INTENSITY_MAP.charAt(c));//+INTENSITY_MAP.charAt(c));
                }else{
                    System.out.print("  ");
                }
            }
            System.out.println();
        }


    }

    public String[] to_ascii_array(int[][] scaled_gs) {
        String INTENSITY_MAP = "@#$&?^}{><*`'~=+-_,. "; // " .,_-+=~'`*<>{}^?&$#@"
        int INTENSITY_BIN = (int) 255/INTENSITY_MAP.length();

        String[] rows = new String[scaled_gs.length];
        for (int i=0; i<scaled_gs.length; i++){

            String im_string = "";
            for(int v: scaled_gs[i]){
                int c = (int) v/INTENSITY_BIN -1;
                if(c >=0 ){
                    im_string = im_string+INTENSITY_MAP.charAt(c)+INTENSITY_MAP.charAt(c);
                }else{
                    im_string = im_string + "  ";
                }
            }
            rows[i] = im_string;
        }

        return rows;
    }

    public String to_ascii_string(int[][] scaled_gs){

        String INTENSITY_MAP = "@#$&?^}{><*`'~=+-_,. "; // " .,_-+=~'`*<>{}^?&$#@"
        int INTENSITY_BIN = (int) 255/INTENSITY_MAP.length();

        String im_string = "";

        for (int[] x: scaled_gs){
            for(int v: x){
                int c = (int) v/INTENSITY_BIN -1;
                if(c >=0 ){
                    im_string = im_string+INTENSITY_MAP.charAt(c)+INTENSITY_MAP.charAt(c);
                }else{
                    im_string = im_string + "  ";
                }
            }
            im_string = im_string + "\n";
        }

        return im_string;
    }



}
