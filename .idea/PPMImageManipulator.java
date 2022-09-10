//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;F\
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

class PPMImage {
    int imageWidth;
    int imageHeight;
    int maxColorValue;
    char[] raster;

    public PPMImage(String ppmDirectory) {
        this.readImage(ppmDirectory);
    }

    private void readImage(String ppmDirectory) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(ppmDirectory))));
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(ppmDirectory)));
            String currentLine = bufferedReader.readLine();
            if (currentLine.equals("P6")) {
                System.out.println("PPM file confirmed.");
            } else {
                System.out.println("Error: not a PPM file.");
                System.exit(0);
            }

            inputStream.skip((long)(currentLine + "\n").getBytes().length);

            do {
                currentLine = bufferedReader.readLine();
                inputStream.skip((long)(currentLine + "\n").getBytes().length);
            } while(currentLine.charAt(0) == '#');

            StringTokenizer stringTokenizer = new StringTokenizer(currentLine);
            this.imageWidth = Integer.parseInt(stringTokenizer.nextToken());
            this.imageHeight = Integer.parseInt(stringTokenizer.nextToken());
            currentLine = bufferedReader.readLine();
            inputStream.skip((long)(currentLine + "\n").getBytes().length);
            stringTokenizer = new StringTokenizer(currentLine);
            this.maxColorValue = Integer.parseInt(stringTokenizer.nextToken());
            this.raster = new char[this.imageWidth * this.imageHeight * 3];

            for(int i = 0; i < this.imageWidth * this.imageHeight * 3; ++i) {
                this.raster[i] = (char)inputStream.read();
            }

            bufferedReader.close();
            inputStream.close();
        } catch (ArrayIndexOutOfBoundsException var7) {
            System.out.println("Error: image in " + ppmDirectory + " too big");
        } catch (FileNotFoundException var8) {
            System.out.println("Error: file " + ppmDirectory + " not found");
        } catch (IOException var9) {
            System.out.println("Error: end of stream encountered when reading " + ppmDirectory);
        }

    }

    void writeFile(String ppmDirectory) {
        try {
            String header = String.format("P6\n%d %d\n255\n", this.imageWidth, this.imageHeight);
            File outputImage = new File(ppmDirectory);
            if (outputImage.exists() && !outputImage.isDirectory()) {
                System.out.println("Overwriting file at " + ppmDirectory + ".");
                outputImage.delete();
            } else {
                System.out.println("Creating file at " + ppmDirectory + ".");
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(outputImage));
            byte[] byteRaster = new byte[this.raster.length];

            for(int i = 0; i < this.raster.length; ++i) {
                byteRaster[i] = (byte)(255 & this.raster[i]);
            }

            outputStream.write(header.getBytes(StandardCharsets.US_ASCII));
            outputStream.write(byteRaster);
            bufferedWriter.close();
            outputStream.close();
        } catch (IOException var8) {
            System.out.println("Error");
        }

    }

    void grayscale() {
        char[] grayscale = new char[this.imageWidth * this.imageHeight * 3];

        for(int i = 0; i < this.imageWidth * this.imageHeight * 3; ++i) {
            if (i % 3 == 0) {
                grayscale[i] = (char)((int)((double)this.raster[i] * 0.299 + (double)this.raster[i + 1] * 0.587 + (double)this.raster[i + 2] * 0.114));
            } else if (i % 3 == 1) {
                grayscale[i] = (char)((int)((double)this.raster[i - 1] * 0.299 + (double)this.raster[i] * 0.587 + (double)this.raster[i + 1] * 0.114));
            } else {
                grayscale[i] = (char)((int)((double)this.raster[i - 2] * 0.299 + (double)this.raster[i - 1] * 0.587 + (double)this.raster[i] * 0.114));
            }
        }

        this.raster = grayscale;
    }

    void sepia() {
        char[] sepia = new char[this.imageWidth * this.imageHeight * 3];

        for(int i = 0; i < this.imageWidth * this.imageHeight * 3; ++i) {
            if (i % 3 == 0) {
                if ((double)this.raster[i] * 0.393 + (double)this.raster[i + 1] * 0.769 + (double)this.raster[i + 2] * 0.189 > 255.0) {
                    sepia[i] = 255;
                } else {
                    sepia[i] = (char)((int)((double)this.raster[i] * 0.393 + (double)this.raster[i + 1] * 0.769 + (double)this.raster[i + 2] * 0.189));
                }
            } else if (i % 3 == 1) {
                if ((double)this.raster[i - 1] * 0.349 + (double)this.raster[i] * 0.686 + (double)this.raster[i + 1] * 0.168 > 255.0) {
                    sepia[i] = 255;
                } else {
                    sepia[i] = (char)((int)((double)this.raster[i - 1] * 0.349 + (double)this.raster[i] * 0.686 + (double)this.raster[i + 1] * 0.168));
                }
            } else if ((double)this.raster[i - 2] * 0.272 + (double)this.raster[i - 1] * 0.534 + (double)this.raster[i] * 0.131 > 255.0) {
                sepia[i] = 255;
            } else {
                sepia[i] = (char)((int)((double)this.raster[i - 2] * 0.272 + (double)this.raster[i - 1] * 0.534 + (double)this.raster[i] * 0.131));
            }
        }

        this.raster = sepia;
    }

    void negative() {
        char[] negative = new char[this.imageWidth * this.imageHeight * 3];

        for(int i = 0; i < this.imageWidth * this.imageHeight * 3; ++i) {
            negative[i] = (char)(255 - this.raster[i]);
        }

        this.raster = negative;
    }
}
