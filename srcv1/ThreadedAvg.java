import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;

public class ThreadedAvg {

    public static final int NUMTHREADS = 4;

    public static void main(String[] args) throws IOException, InterruptedException{

        if(args.length == 1){
            long startTime = System.nanoTime();
            BufferedImage bi = ImageIO.read(new File(args[0]));

            ArrayList<int[]> rgblists = convertToRGBLists(bi);
            int[] average = compute(rgblists);

            long endTime = System.nanoTime();

            System.out.println(Arrays.toString(average));
            System.out.println(String.format("Time elapsed: %f seconds",
                        (endTime - startTime)/1000000000.0));
        }else{
            System.out.println("failed to load file");
        }
    }

    public static int[] compute(ArrayList<int[]> rgblists) throws InterruptedException {

        int[] average = new int[3];

        Thread[] threads = new Thread[3];
        for(int j = 0; j < 3; j++){
            final int COLOR = j;

            threads[j] = new Thread(
                new Runnable(){
                    public void run(){
                        //System.out.format("New Runnable started on list %d%n", COLOR);
                        int[] color = rgblists.get(COLOR);
                        double numpixels = color.length;
                        double sum = 0;
                        for(int i = 0; i < numpixels; i++){
                            sum += color[i];
                        }
                        //System.out.format("Sum: %f, NumPixels: %f%n", sum, numpixels);
                        int avg = (int)(sum / numpixels);
                        average[COLOR] = avg;
                    }
                });
            threads[j].start();
        }

        for (int j = 0; j < 3; j++){
            threads[j].join();
        }

        return average;
    }

    public static ArrayList<int[]> convertToRGBLists(BufferedImage bi){

        final int width = bi.getWidth();
        final int height = bi.getHeight();
        double area = width * height;

        int[] red = new int[(int)area];
        int[] green = new int[(int)area];
        int[] blue = new int[(int)area];
        ArrayList<int[]> rgblists = new ArrayList<int[]>();

        int pixel = 0;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                int[] pixelcolors = bi.getRaster().getPixel(x, y, new int[3]);
                red[pixel] = pixelcolors[0];
                green[pixel] = pixelcolors[1];
                blue[pixel] = pixelcolors[2];
                pixel++;
            }
        }
        rgblists.add(red);
        rgblists.add(green);
        rgblists.add(blue);

        int[] subarray = Arrays.copyOf(blue, 20000);

        return rgblists;
    }
}
