import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;

public class ForLoopAvg {

    public static void main(String[] args) throws IOException, InterruptedException{

        if(args.length == 1){
            long startTime = System.nanoTime();
            BufferedImage bi = ImageIO.read(new File(args[0]));

            int[] average = compute(bi);

            long endTime = System.nanoTime();

            System.out.println(Arrays.toString(average));
            System.out.println(String.format("Time elapsed: %f seconds",
                        (endTime - startTime)/1000000000.0));
        }else{
            System.out.println("failed to load file");
        }
    }

    public static int[] compute(BufferedImage bi) throws InterruptedException {

        int height = bi.getHeight();
        int width = bi.getWidth();
        double numPixels = height * width;

        double[] average = new double[3];

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int[] pixel = bi.getRaster().getPixel(x, y, new int[3]);
                for(int i = 0; i < pixel.length; i++){
                   average[i] += pixel[i];
                }
            }
        }

        int[] result = new int[3];
        for(int i = 0; i < average.length; i++){
            average[i] = average[i] / numPixels;
            result[i] = (int) average[i];
        }


        return result;
    }
}
