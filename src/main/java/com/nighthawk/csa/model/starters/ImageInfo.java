package com.nighthawk.csa.model.starters;

import lombok.Getter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.imageio.ImageIO;

@Getter
public class ImageInfo {
    public int scale_factor;
    public String file, url;
    public int height, scaled_height;
    public int width, scaled_width;
    public int[][][] rgb_matrix;

    public ImageInfo(String file, String url, int scale_factor) {
        this.file = file;
        this.url = url;
        this.scale_factor = scale_factor;
    }

    public Exception read_image() {
        try{
            BufferedImage img = ImageIO.read(new URL(this.url));
            this.height = img.getHeight();
            this.scaled_height = this.height / scale_factor;
            this.width = img.getWidth();
            this.scaled_width = this.width / scale_factor;
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


    public String grayscale() {
        try {
            BufferedImage img = ImageIO.read(new URL(url));
            byte[] pixels = image_to_pixels(img);
            int[] pixels_int = grayscale(pixels);
            String base64 = pixels_to_base64(img.getWidth(), img.getHeight(), pixels_int);
            return "data:image/jpg;base64,"+base64;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int[] grayscale(byte[] pixels){
        int[] pixels_int = new int[pixels.length];
        for(int i=0;i<pixels.length;i+=4) {
            float val = 0;
            for(int y=1;y<4;y++) {
                val += (pixels[i+y] & 0xFF)/3.0;
            }
            pixels_int[i+0] = pixels[i];
            pixels_int[i+1] = (int)val;
            pixels_int[i+2] = (int)val;
            pixels_int[i+3] = (int)val;
        }
        return pixels_int;
    }


    public byte[] image_to_pixels(BufferedImage img) throws IOException {
            byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
            return pixels; // IMPORTANT:
    }

    public String pixels_to_base64(int width, int height, int[] pixels) throws IOException {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int y=0;y<height;y++) {
            for(int x=0; x<width; x++) {
                int a,r,g,b;
                int s = (y*width + x)*4;
                a=pixels[s+0];
                r=pixels[s+1];
                g=pixels[s+2];
                b=pixels[s+3];
                img.setRGB(x,y, argb(a,r,g,b));
            }
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", bos);
        byte[] data = bos.toByteArray();
        return Base64.encodeBase64String(data);
    }

    public int argb(int a, int r, int g, int b) {
        return ((a&0x0ff)<<24)|((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
    }

    public String test() {
        try {
            BufferedImage img = ImageIO.read(new URL(this.url));
            byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
            System.out.println(pixels.length);

            BufferedImage bi2 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for(int y=0;y<img.getHeight();y++) {
                for(int x=0; x<img.getWidth(); x++) {
                    int a, r, g, b;
                    int s = (y*img.getWidth() + x)*4;
                    a=pixels[s+0] & 0xFF;
                    r=pixels[s+1] & 0xFF;
                    g=pixels[s+2] & 0xFF;
                    b=pixels[s+3] & 0xFF;
                    bi2.setRGB(x,y, new Color(r,g,b,a).getRGB());
                }
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bi2, "png", bos);
            byte[] data = bos.toByteArray();
            System.out.println(data.length);
            return "data:image/jpg;base64," + Base64.encodeBase64String(data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public String gs() {
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = image.getRaster();
        DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = buffer.getData();

        for(int y=0; y<this.height; y++) {
            for(int x=0; x<this.width; x++) {
                byte avg = 50;//(byte) ((rgb_matrix[y][x][0]+rgb_matrix[y][x][1]+rgb_matrix[y][x][2])/3);
                data[(y*this.width) + x] = avg;
            }
        }


        System.out.println(data.length);

        StringBuilder sb = new StringBuilder();
        sb.append("data:image/png;base64,");
        sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(data, false)));
        return sb.toString();
    }



    public int getScaled_height(int row) {
        return row * this.scale_factor;
    }

    public int getScaled_width(int col) {
        return col * this.scale_factor;
    }

    public String getRGB(int row, int col) {
        int h = row * this.scale_factor;
        int w = col * this.scale_factor;
        return "(" + rgb_matrix[h][w][0] +
                "," + rgb_matrix[h][w][1] +
                "," + rgb_matrix[h][w][2] +
                ")" ;
    }

    public String getHexCode(int row, int col) {
        int h = row * this.scale_factor;
        int w = col * this.scale_factor;
        // String.format guarantees 0 padding vs Integer.toHexString
        return "#" +
                String.format("%02X",rgb_matrix[h][w][0]) +
                String.format("%02X",rgb_matrix[h][w][1]) +
                String.format("%02X",rgb_matrix[h][w][2]) ;
    }

    public String getBinary(int row, int col) {
        int h = row * this.scale_factor;
        int w = col * this.scale_factor;
        // Java does not have binary as string formatter
        return "0b" +
                String.format("%8s", Integer.toBinaryString(rgb_matrix[h][w][0])).replace(' ', '0') +
                String.format("%8s", Integer.toBinaryString(rgb_matrix[h][w][1])).replace(' ', '0') +
                String.format("%8s", Integer.toBinaryString(rgb_matrix[h][w][2])).replace(' ', '0') ;
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
        // scale image down by scale_factor
        int w_scaled = width /scale_factor;
        int h_scaled = height /scale_factor;

        int[][] scaled = new int[h_scaled][w_scaled];
        for(int w=0; w<w_scaled; w++){
            for(int h=0; h<h_scaled; h++){ //looping over blocks

                int sum = 0;
                for(int x=(w)*scale_factor; x<(w+1)*scale_factor; x++){ //looping over the induvidual coordinates in block
                    for(int y=(h)*scale_factor; y<(h+1)*scale_factor; y++){
                        //System.out.println("("+Integer.toString(x)+", "+Integer.toString(y)+")");
                        sum = sum + gs[y][x];
                    }
                } //end loop 2

                scaled[h][w] = sum /(scale_factor*scale_factor);

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
        int INTENSITY_BIN = 255 /INTENSITY_MAP.length();

        StringBuilder im_string = new StringBuilder();

        for (int[] x: scaled_gs){
            for(int v: x){
                int c = v /INTENSITY_BIN -1;
                if(c >=0 ){
                    im_string.append(INTENSITY_MAP.charAt(c)).append(INTENSITY_MAP.charAt(c));
                }else{
                    im_string.append("  ");
                }
            }
            im_string.append("\n");
        }

        return im_string.toString();
    }

}
