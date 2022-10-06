package com.nighthawk.csa.hacks;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics2D;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;

public class Pics {
    private final String inDir = System.getProperty("user.dir")+ "/images/"; // location of images
    private final String outDir = System.getProperty("user.dir") + "/images/tmp/";  // location of created files
    private String inFile;
    private String resizedFile;
    private String asciiFile;
    private String ext;   // extension of file
    private long bytes;
    private int width;
    private int height;

    // Constructor obtains attributes of picture
    public Pics(String name, String ext) {
        this.ext = ext;
        this.inFile = this.inDir + name + "." + ext;
        this.resizedFile = this.outDir + name + "." + ext;
        this.asciiFile = this.outDir + name + ".txt";
        this.setStats();
    }

    public void printStats(String msg) {
        System.out.println(msg + ": " + this.bytes + " " + this.width + "x" + this.height + "  " + this.inFile);
    }

    // Buffered image contains attributes, namely width and height
    public void setStats() {
        BufferedImage img;
        try {
            Path path = Paths.get(this.inFile);
            this.bytes = Files.size(path);
            img = ImageIO.read(new File(this.inFile));
            this.width = img.getWidth();
            this.height = img.getHeight();
        } catch (IOException e) {
        }
    }
    
    public static BufferedImage convertToBufferedImage(Image img) {

        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bi = new BufferedImage(
                img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();

        return bi;
    }
    
    public void resize(int scale) {
        BufferedImage img = null;
        Image resizedImg = null;  

        int width = (int) (this.width * (scale/100.0) + 0.5);
        int height = (int) (this.height * (scale/100.0) + 0.5);

        try {
            // read an image to BufferedImage for processing
            img = ImageIO.read(new File(this.inFile));  // set buffer of image data
            // create a new BufferedImage for drawing
            resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            return;
        }

        try {
            ImageIO.write(convertToBufferedImage(resizedImg), this.ext, new File(resizedFile));
        } catch (IOException e) {
            return;
        }
        
        this.inFile = this.resizedFile;  // use scaled file vs original file in Class
        this.setStats();
    }
    
    public void convertToAscii() {
        BufferedImage img = null;
        PrintWriter asciiPrt = null;
        FileWriter asciiWrt = null;

        try {
            File file = new File(this.asciiFile);
            Files.deleteIfExists(file.toPath()); 
        } catch (IOException e) {
            System.out.println("Delete File error: " + e);
        }

        try {
            asciiPrt = new PrintWriter(asciiWrt = new FileWriter(this.asciiFile, true));
        } catch (IOException e) {
            System.out.println("ASCII out file create error: " + e);
        }

        try {
            img = ImageIO.read(new File(this.inFile));
        } catch (IOException e) {
        }

        System.out.println();
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color col = new Color(img.getRGB(j, i));
                double pixVal = (((col.getRed() * 0.30) + (col.getBlue() * 0.59) + (col
                        .getGreen() * 0.11)));
                try {
                    asciiPrt.print(asciiChar(pixVal));
                    asciiPrt.flush();
                    asciiWrt.flush();
                } catch (Exception ex) {
                }
                System.out.print(asciiChar(pixVal));
            }
            try {
                asciiPrt.println("");
                asciiPrt.flush();
                asciiWrt.flush();
            } catch (Exception ex) {
            }
            System.out.println();
        }
    }

    public String asciiChar(double g) {
        String str = " ";
        if (g >= 240) {
            str = " ";
        } else if (g >= 210) {
            str = ".";
        } else if (g >= 190) {
            str = "*";
        } else if (g >= 170) {
            str = "+";
        } else if (g >= 120) {
            str = "^";
        } else if (g >= 110) {
            str = "&";
        } else if (g >= 80) {
            str = "8";
        } else if (g >= 60) {
            str = "#";
        } else {
            str = "@";
        }
        return str;
    }

    public static void main(String[] args) throws IOException {
        Pics monaLisa = new Pics("MonaLisa", "png");
        monaLisa.printStats("Original");
        monaLisa.resize(33);
        monaLisa.printStats("Scaled");
        monaLisa.convertToAscii();

        Pics pumpkin = new Pics("pumpkin", "png");
        pumpkin.printStats("Original");
        pumpkin.resize(33);
        pumpkin.printStats("Scaled");
        pumpkin.convertToAscii();
    }
}
