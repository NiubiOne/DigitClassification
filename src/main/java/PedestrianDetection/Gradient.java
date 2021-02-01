package PedestrianDetection;

import Images.ImageResizer;
import com.sun.deploy.util.ArrayUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Gradient {

    int height;
    int width;
    int[][] redColor;
    int[][] blueColor;
    int[][] greenColor;
    int[][] gradientMagnitude;
    int[][] gradientDirection;


    public Gradient(BufferedImage image) {
         height = image.getHeight();
         width = image.getWidth();
        System.out.println(height);
        System.out.println(width);
         gradientMagnitude = new int[height][width];
        gradientDirection = new int[height][width];
         redColor=new int[height][width];
         blueColor=new int[height][width];
         greenColor=new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                redColor[y][x] = image.getRGB(x, y) >> 16 & 0xFF;
                blueColor[y][x] =image.getRGB(x, y) >> 8 & 0xFF;
                greenColor[y][x] = image.getRGB(x, y) & 0xFF;
            }
        }
        calculate();

    }

    void calculate(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int x1 = x - 1;
                int x2 = x + 1;
                int y1 = y - 1;
                int y2 = y + 1;

                if (x1 < 0)
                    x1 = 0;
                if (x2 >= width)
                    x2 = width - 1;
                if (y1 < 0)
                    y1 = 0;
                if (y2 >= height)
                    y2 = height - 1;

                int xRedGradient = redColor[y][x2] - redColor[y][x1];
                int yRedGradient = redColor[y2][x] - redColor[y1][x];
                int xBlueGradient = blueColor[y][x2] - blueColor[y][x1];
                int yBlueGradient = blueColor[y2][x] - blueColor[y1][x];
                int xGreenGradient = greenColor[y][x2] - greenColor[y][x1];
                int yGreenGradient = greenColor[y2][x] - greenColor[y1][x];
              int  redMagnitude =(int) Math.sqrt(Math.pow(xRedGradient,2) + Math.pow(yRedGradient,2));
              int  blueMagnitude = (int) Math.sqrt(Math.pow(xBlueGradient,2) + Math.pow(yBlueGradient,2));
              int  greenMagnitude = (int) Math.sqrt(Math.pow(xGreenGradient,2) + Math.pow(yGreenGradient,2));
if (redMagnitude>blueMagnitude){
    if (blueMagnitude>greenMagnitude) gradientMagnitude[y][x] = redMagnitude;
    else if (greenMagnitude>redMagnitude) gradientMagnitude[y][x] = greenMagnitude;
}else gradientMagnitude[y][x] = blueMagnitude;

                int r = (int)Math.toDegrees(Math.atan( (double)yRedGradient/xRedGradient));
                int b = (int) Math.toDegrees(Math.atan((double)yBlueGradient/xBlueGradient));
                int g = (int) Math.toDegrees(Math.atan((double)yGreenGradient/xGreenGradient));
                if (r<0) r+=180;
                if (b<0) b +=180;
                if (g<0) g +=180;

                if (r>b){
                    if (b>g) gradientDirection[y][x] = r;
                    else if (g>r) gradientDirection[y][x] = g;
                }else gradientDirection[y][x] = b;


                System.out.print(gradientDirection[y][x]+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        Gradient g = new Gradient(ImageResizer.resize(ImageIO.read(new File("CreatedImg765022966.png.png")),64,128));

        BufferedImage outputImage = new BufferedImage(64,
                128, BufferedImage.TYPE_3BYTE_BGR);
        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 64; x++) {
                outputImage.setRGB(x,y,new Color(g.gradientMagnitude[y][x],g.gradientMagnitude[y][x],g.gradientMagnitude[y][x]).getRGB());
            }
        }
        ImageIO.write(outputImage,"bmp",new File("abc.bmp"));
        System.out.println((int) Math.toDegrees(Math.atan(1.6)));
        System.out.println(159%20);
       System.out.println(Arrays.toString(HistogramOfGradients.histogramOfGradients(0, 0, g.gradientMagnitude, g.gradientDirection)));

        int[][][] histograms = new int[16][8][];
        int i=1;
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 8; x++) {
               histograms[y][x] = HistogramOfGradients.histogramOfGradients(x*8, y*8, g.gradientMagnitude, g.gradientDirection);
               System.out.println(i+":: "+ Arrays.toString(histograms[y][x]));
               i++;
            }
        }
        ArrayList<Double> vector = new ArrayList<>();
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 7; x++) {
                ArrayList<Double> vectorN = new ArrayList<>();
                long squared=0;
                for (int k=0;k<2;k++) {
                    for (int p=0;p<2;p++) {
                        for (int l = 0; l < 9; l++) {
                            vectorN.add((double)histograms[y+k][x+p][l]);
                            squared+=Math.pow(histograms[y+k][x+p][l],2);
                        }
                    }
                }
                double length = Math.sqrt(squared);
                for (int z=0;z<vectorN.size();z++){
                    vectorN.set(z,(vectorN.get(z)/length));
                }
                vector.addAll(vectorN);
            }
        }
        System.out.println(vector);
        System.out.println(vector.size());
    }

}
