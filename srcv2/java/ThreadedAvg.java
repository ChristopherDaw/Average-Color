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

            ArrayList<ArrayList<Integer>> rgblists = convertToRGBLists(bi);

            int[] average = compute(rgblists);

            long endTime = System.nanoTime();

            System.out.println(Arrays.toString(average));
            System.out.println(String.format("Time elapsed: %f seconds",
                        (endTime - startTime)/1000000000.0));
        }else{
            System.out.println("failed to load file");
        }
    }

    public static int[] compute(ArrayList<ArrayList<Integer>> rgblists) throws InterruptedException {

        int[] average = new int[3];

        Thread[] threads = new Thread[3];
        for(int j = 0; j < 3; j++){
            final int COLOR = j;

            threads[j] = new Thread(
                new Runnable(){
                    public void run(){
                        ArrayList<Integer> color = rgblists.get(COLOR);

                        double numpixels = color.size();
                        double sum = 0;
                       for(Integer value : color){
                           if(value == null)
                               continue;
                           sum += value;
                       }
                       int avg = (int) (sum / numpixels);
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

    public static ArrayList<ArrayList<Integer>> convertToRGBLists(BufferedImage bi) throws InterruptedException {
        int numThreads = 10;
        Thread[] threads = new Thread[numThreads];

        final int width = bi.getWidth();
        final int height = bi.getHeight();
        double size = width * height;

        ArrayList<Integer> red = new ArrayList<Integer>();
        ArrayList<Integer> green = new ArrayList<Integer>();
        ArrayList<Integer> blue = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> rgblists = new ArrayList<ArrayList<Integer>>();

        final int chunk = height / numThreads;
        final int rem = height % numThreads;
        //int section = (int) size / numThreads;
        //int remainder = (int) (size % section);
        for(int t = 0; t < numThreads; t++){
            final int index = t;
            //int startx = (int)(t * size) % width;
            //int starty = (int)Math.floor((t * section) / width);

            threads[t] = new Thread(
                new Runnable(){
                    public void run(){
                        //int x = startx;
                        //int y = starty;
                        //for(int i = 0; i < section; i++){
                            //int[] pixelcolors;
                            //pixelcolors = bi.getRaster().getPixel(x, y, new int[3]);
                            //for(Integer color : pixelcolors){
                                //if(color == null)
                                    //continue;
                                    //red.add(pixelcolors[0]);
                                    //green.add(pixelcolors[1]);
                                    //blue.add(pixelcolors[2]);
                                //}
                            //x++;
                            //if(x == width){
                                //x = 0;
                                //y ++;
                            //}
                        //}
                        for(int y = index * chunk; y < index+1 * chunk; y++){
                            for(int x = 0; x < width; x++){
                                int[] pixelcolors;
                                pixelcolors = bi.getRaster().getPixel(x, y, new int[3]);
                                for(Integer color : pixelcolors){
                                    if(color == null)
                                        continue;
                                    red.add(pixelcolors[0]);
                                    green.add(pixelcolors[1]);
                                    blue.add(pixelcolors[2]);
                                }
                            }
                        }
                    }
                });
            threads[t].start();
        }

        for(int j = 0; j < numThreads; j++){
            threads[j].join();
        }

        rgblists.add(red);
        rgblists.add(green);
        rgblists.add(blue);


        return rgblists;
    }
}
