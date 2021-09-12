package com.nighthawk.csa.model.starters;

import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

@Getter
public class ImageInfo {
    public int SCALE_FACTOR;
    public String file;
    public int height, scaled_height;
    public int width, scaled_width;
    public int[][][] rgb_matrix;

    public ImageInfo(String file, int SCALE_FACTOR) {
        this.file = file;
        this.SCALE_FACTOR = SCALE_FACTOR;
    }

    public Exception read_image() {
        try{
            BufferedImage img = ImageIO.read(new URL(this.file));
            this.height = img.getHeight();
            this.scaled_height = this.height / SCALE_FACTOR;
            this.width = img.getWidth();
            this.scaled_width = this.width / SCALE_FACTOR;
            //System.out.println(Integer.toString(this.height) + " " + Integer.toString(this.width));
            this.rgb_matrix = new int[this.height][this.width][3];

            for (int y=0; y < this.height; y++){
                for(int x=0; x < this.width; x++){
                    int pixel = img.getRGB(x, y);
                    Color color = new Color(pixel, true);

                    this.rgb_matrix[y][x][0] = color.getRed();
                    this.rgb_matrix[y][x][1] = color.getGreen();
                    this.rgb_matrix[y][x][2] = color.getBlue();
                }
            }

        } catch (Exception e) {
            return e;
        }

        return null;
    }

    public int getScaled_height(int row) {
        return row * this.SCALE_FACTOR;
    }

    public int getScaled_width(int col) {
        return col * this.SCALE_FACTOR;
    }

    public String getScaled_rgb(int row, int col) {
        int h = row * this.SCALE_FACTOR;
        int w = col * this.SCALE_FACTOR;
        return get_rgb(h,w);
    }

    public String get_rgb(int h, int w) {
        return "(" + rgb_matrix[h][w][0] +
                "," + rgb_matrix[h][w][1] +
                "," + rgb_matrix[h][w][2] + ")";
    }

    public String[] convert_to_ascii(){
        int[][] gs = this.to_grayscale();
        int[][] scaled_gs = this.scale(gs);
        return  this.to_ascii_array(scaled_gs);
    }

    public int[][] to_grayscale(){
        // grey scale is calculated to average of pixel
        int[][] gs = new int[height][width];
        for (int h=0; h < height; h++){
            for(int w=0; w < width; w++){
                int r = this.rgb_matrix[h][w][0];
                int g = this.rgb_matrix[h][w][1];
                int b = this.rgb_matrix[h][w][2];
                // averaging
                int avg = (r+g+b) /3;
                gs[h][w] = avg;
            }
        }
        return gs;
    }

    public int[][] scale(int[][] gs){
        // scale image down by SCALE_FACTOR
        int w_scaled = width /SCALE_FACTOR;
        int h_scaled = height /SCALE_FACTOR;

        int[][] scaled = new int[h_scaled][w_scaled];
        for(int w=0; w<w_scaled; w++){
            for(int h=0; h<h_scaled; h++){ //looping over blocks

                int sum = 0;
                for(int x=(w)*SCALE_FACTOR; x<(w+1)*SCALE_FACTOR; x++){ //looping over the induvidual coordinates in block
                    for(int y=(h)*SCALE_FACTOR; y<(h+1)*SCALE_FACTOR; y++){
                        //System.out.println("("+Integer.toString(x)+", "+Integer.toString(y)+")");
                        sum = sum + gs[y][x];
                    }
                } //end loop 2

                scaled[h][w] = sum /(SCALE_FACTOR*SCALE_FACTOR);

            }
        } //end loop 1
        //System.out.println(Arrays.deepToString(scaled));
        return scaled;
    }


    public void to_ascii(int[][] scaled_gs){
        String INTENSITY_MAP = "@#$&?^}{><*`'~=+-_,. "; // " .,_-+=~'`*<>{}^?&$#@"
        int INTENSITY_BIN = 255 /INTENSITY_MAP.length();

        for (int[] x: scaled_gs){
            for(int v: x){
                int c = v /INTENSITY_BIN -1;
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
        int INTENSITY_BIN = 255 /INTENSITY_MAP.length();

        String[] rows = new String[scaled_gs.length];
        for (int i=0; i<scaled_gs.length; i++){

            StringBuilder im_string = new StringBuilder();
            for(int v: scaled_gs[i]){
                int c = v /INTENSITY_BIN -1;
                if(c >=0 ){
                    im_string.append(INTENSITY_MAP.charAt(c)).append(INTENSITY_MAP.charAt(c));
                }else{
                    im_string.append("  ");
                }
            }
            rows[i] = im_string.toString();
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
