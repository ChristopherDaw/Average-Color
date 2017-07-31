import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;

public class QuickForLoopAvg {

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

        final byte[] pixels = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        final int width = bi.getWidth();
        final int height = bi.getHeight();

        double[] average = new double[3];

        for(int pixel = 0; pixel < pixels.length; pixel += 3){
            average[2] += pixels[pixel] & 0xFF; //blue
            average[1] += pixels[pixel + 1] & 0xFF; //green
            average[0] += pixels[pixel + 2] & 0xFF; //red
        }

        int[] results = new int[3];
        for(int i = 0; i < average.length; i++){
            average[i] = average[i] / (width * height);
            results[i] = (int) average[i];
        }

        return results;
    }
}
