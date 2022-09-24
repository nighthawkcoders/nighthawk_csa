package com.nighthawk.csa.hacks;

import java.awt.Color;
import java.awt.image.BufferedImage;

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
    private String name;  // name of file
    private String ext;   // extension of file
    private String inFile;
    private String compressFile;
    private String asciiFile;

    // Constructor obtains attributes of picture
    public Pics(String name, String ext) {
        this.name = name;
        this.ext = ext;
        this.inFile = this.inDir + name + "." + ext;
        this.compressFile = this.outDir + name + "." + ext;
        this.asciiFile = this.outDir + name + ".txt";
    }

    // Buffered image contains attributes, namely width and height
    public void imageStats(String msg) {
        BufferedImage img;
        try {
            Path path = Paths.get(this.inFile);
            long bytes = Files.size(path);
            img = ImageIO.read(new File(this.inFile));
            System.out.println(msg + ": " + bytes + " " + img.getWidth() + "x" + img.getHeight());
        } catch (IOException e) {
        }
    }
    
    // Compress the Image
    public void compress() {
        BufferedImage img = null;
        IIOMetadata metadata = null;

        try (ImageInputStream in = ImageIO.createImageInputStream(Files.newInputStream(Paths.get(this.inFile)))) {
            ImageReader reader = ImageIO.getImageReadersByFormatName(this.ext).next();
            reader.setInput(in, true, false);
            img = reader.read(0);
            metadata = reader.getImageMetadata(0);
            reader.dispose();
        } catch (IOException e) {
            return;
        }

        try (ImageOutputStream out = ImageIO.createImageOutputStream(Files.newOutputStream(Paths.get(this.compressFile)))) {
            ImageTypeSpecifier type = ImageTypeSpecifier.createFromRenderedImage(img);
            ImageWriter writer = ImageIO.getImageWriters(type, this.ext).next();

            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.0f); // an integer between 0 and 1
                // 1 specifies minimum compression and maximum quality
            }

            writer.setOutput(out);
            writer.write(null, new IIOImage(img, null, metadata), param);
            writer.dispose();
        } catch (IOException e) {
            return;
        }

        this.inFile = this.compressFile;  // use compressed file vs original file in Class
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
            }
            try {
                asciiPrt.println("");
                asciiPrt.flush();
                asciiWrt.flush();
            } catch (Exception ex) {
            }
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
        monaLisa.imageStats("Original");

        monaLisa.compress();
        monaLisa.imageStats("Compressed");

        monaLisa.convertToAscii();
    }
}
