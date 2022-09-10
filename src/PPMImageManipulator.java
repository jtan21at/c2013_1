import java.io.File;
import java.util.Scanner;
/*public class hello {


}*/

      /*  public final String magicNumber = "P6";
*/


/*        public void PPMImage(String ppmDirectory) throws IOException {
            int imageWidth;
            int imageHeight;
            int maxColorValue;
            char raster[];

            File file = new File(ppmDirectory);
            BufferedImage image = ImageIO.read(file);
          //  imageWidth = ImageIO.read();

            System.out.println(ppmDirectory);

        }*/

    public  class PPMImageManipulator {

        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
            System.out.println(System.getProperty("user.dir"));
            System.out.print("Enter absolute path of a PPM file: ");
            String filepath = input.nextLine().trim();

                try {
                    PPMImage img = new PPMImage(filepath);
                    File file = new File(filepath);
                    if (!file.exists()) {
                        System.out.println("file not found try" + System.getProperty("user.dir") + "\\" + filepath);
                        filepath = System.getProperty("user.dir") + "\\" + filepath;
                        img = new PPMImage(filepath);
                        if (!file.exists()) {
                            System.out.println("file not found try again");
                            System.exit(11);
                        }
                    }





            System.out.println(System.getProperty("user.dir"));
            System.out.println("How would you like to manipulate this file?");
            System.out.println("\t[G] Convert to grayscale.");
            System.out.println("\t[S] Convert it sepia.");
            System.out.println("\t[N] Convert it to its negative.");
            System.out.println("\t[C] Copy without altering.");

            char operation = input.nextLine().charAt(0);
            operation = Character.toUpperCase(operation);

            switch (operation) {
                case 'G':
                    img.grayscale();
                    break;
                case 'S':
                    img.sepia();
                    break;
                case 'N':
                    img.negative();
                    break;
                case 'C': /* do nothing... */
                    break;
                default:
                    System.out.println(operation + " is not a recognized command.");
                    System.out.println("Exiting.");
                    System.exit(1);
                    System.out.print("Enter filepath for the manipulated image: ");
                    filepath = input.nextLine().trim();
                    img.writeFile(filepath);

            }
                }/*catch(Exception e)
                {}*//*catch(FileNotFoundException e){
                                 e.printStackTrace();
                             } catch(IOException e){
                                 e.printStackTrace();
                             }*/ finally{
                                 System.out.println("Thank you for using hello.PPMImageManipulator!");
                                 System.exit(0);
                             }

        }
        }
/*}
}*/
  /*  public class PPMImage {

        public final String magicNumber = "P6";

        public PPMImage() {}
        public PPMImage(String ppmDirectory) throws IOException {
            int imageWidth;
            int imageHeight;
            int maxColorValue;
            char raster[];

          //  File file = new File(ppmDirectory);
           // BufferedImage image = ImageIO.read(file);


            System.out.println(ppmDirectory);

        }
    }
}
*/