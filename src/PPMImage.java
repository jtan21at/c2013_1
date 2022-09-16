
import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

class PPMImage {
    int imageWidth;
    int imageHeight;
    int maxColorValue;
    char[] raster;

    public PPMImage(String ppmDirectory) {
        readImage(ppmDirectory);
    }

    private void readImage(String ppmDirectory) {
        String currentLine;
        StringTokenizer stringTokenizer;

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(ppmDirectory))));
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(ppmDirectory)));
            currentLine = bufferedReader.readLine();
            if(currentLine.equals("P6")) {
                System.out.println("PPM file confirmed.");
            } else {
                System.out.println("Error: not a PPM file.");
                System.exit(0);
            }
            inputStream.skip((currentLine + "\n").getBytes().length);
            do {
                currentLine = bufferedReader.readLine();
                inputStream.skip((currentLine + "\n").getBytes().length);
            } while (currentLine.charAt(0) == '#');
            stringTokenizer = new StringTokenizer(currentLine);
            imageWidth = Integer.parseInt(stringTokenizer.nextToken());
            imageHeight = Integer.parseInt(stringTokenizer.nextToken());
            currentLine = bufferedReader.readLine();
            inputStream.skip((currentLine + "\n").getBytes().length);
            stringTokenizer = new StringTokenizer(currentLine);
            maxColorValue = Integer.parseInt(stringTokenizer.nextToken());

            raster = new char[imageWidth * imageHeight * 3];
            for (int i = 0; i < (imageWidth * imageHeight * 3); i++) {
                raster[i] = (char) inputStream.read();
                //System.out.println(raster[i]);
            }

            bufferedReader.close();
            inputStream.close();

            //System.out.println(imageWidth + " " + imageHeight + " " + maxColorValue);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: image in " + ppmDirectory + " too big");
        } catch (FileNotFoundException e) {
            System.out.println("Error: file " + ppmDirectory + " not found");
        } catch (IOException e) {
            System.out.println("Error: end of stream encountered when reading " + ppmDirectory);
        }
    }

    void writeFile(String ppmDirectory) {
        File outputImage = new File(ppmDirectory);
            if (!outputImage.isAbsolute()&&outputImage.canWrite()){
             outputImage = new File(System.getProperty("user.dir")+"\\"+ppmDirectory);}
           if( outputImage.isDirectory())
           {System.out.println("Error in path, path is directory instead of file" + ppmDirectory + ".");
               System.exit(5);}
            outputImage.mkdirs();
//           Path ppmDir = Paths.get(ppmDirectory);
      /*  if (!ppmDir.exists(ppmDir)) {
              ppmDir.createDirectory(ppmDir);
          }*/
            try {
                var header = String.format("P6\n%d %d\n255\n", imageWidth, imageHeight);

                if(outputImage.exists() || !outputImage.isDirectory())
                {
                    System.out.println("Overwriting file at " + ppmDirectory + ".");
                    outputImage.delete();
                } else {
                    System.out.println("Creating file at " + ppmDirectory + ".");
                }
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
                DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(outputImage));
                byte[] byteRaster = new byte[raster.length];
                for(int i= 0; i < raster.length; i++) {
                    byteRaster[i] = (byte)(0xFF & (int)raster[i]);
                }
                outputStream.write(header.getBytes(StandardCharsets.US_ASCII));
                outputStream.write(byteRaster);
                bufferedWriter.close();
                outputStream.close();

                //System.out.println(imageWidth + " " + imageHeight + " " + maxColorValue);
            } catch (IOException e) {
                System.out.println("Error");
            }

    }

    void grayscale() {
        char[] grayscale = new char[imageWidth * imageHeight * 3];
        for (int i = 0; i < (imageWidth * imageHeight * 3); i++) {
            if (i % 3 == 0) {
                grayscale[i] = (char) ((raster[i] * 0.299 + raster[i + 1] * 0.587 + raster[i + 2] * 0.114));
            } else if (i % 3 == 1) {
                grayscale[i] = (char) ((raster[i - 1] * 0.299 + raster[i] * 0.587 + raster[i + 1] * 0.114));
            } else {
                grayscale[i] = (char) ((raster[i - 2] * 0.299 + raster[i - 1] * 0.587 + raster[i] * 0.114));
            }
        }
        raster = grayscale;
    }

    void sepia() {
        char[] sepia = new char[imageWidth * imageHeight * 3];
        for (int i = 0; i < (imageWidth * imageHeight * 3); i++) {
            if (i % 3 == 0) {
                if(raster[i] * 0.393 + raster[i + 1] * 0.769 + raster[i + 2] * 0.189 > 255) {
                    sepia[i] = 255;
                } else {sepia[i] = (char) ((raster[i] * 0.393 + raster[i + 1] * 0.769 + raster[i + 2] * 0.189));}
            } else if (i % 3 == 1) {
                if(raster[i - 1] * 0.349 + raster[i] * 0.686 + raster[i + 1] * 0.168 > 255) {
                    sepia[i] = 255;
                } else {sepia[i] = (char) ((raster[i - 1] * 0.349 + raster[i] * 0.686 + raster[i + 1] * 0.168));}
            } else {
                if(raster[i - 2] * 0.272 + raster[i - 1] * 0.534 + raster[i] * 0.131 > 255) {
                    sepia[i] = 255;
                } else {sepia[i] = (char) ((raster[i - 2] * 0.272 + raster[i - 1] * 0.534 + raster[i] * 0.131));}
            }
        }
        raster = sepia;
    }

    void negative() {
        char[] negative = new char[imageWidth * imageHeight * 3];
        for (int i = 0; i < (imageWidth * imageHeight * 3); i++) {
            negative[i] = (char) (255-raster[i]);
        }
        raster = negative;
    }
}