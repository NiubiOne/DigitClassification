package Filters;

import Images.Transform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class SinghBinarization {

    BufferedImage image;
    int width , height;
    int w;
    int d;
    int[][] summeredTable;

    double k = 0.06;

    public SinghBinarization(BufferedImage image, int w) {
        this.w = w;
       d = (int) Math.round(w/2.0);
        width = image.getWidth();
        height = image.getHeight();
        this.image = image;
        summeredTable =new int[image.getHeight()][image.getWidth()];
        integralImage();
    }

    private void integralImage(){
         int width = image.getWidth(), height = image.getHeight();
         for (int y=0;y<height;y++){
             for (int x=0;x<width;x++){
                 int red = image.getRGB(x, y) >> 16 & 0xFF;
                 int blue = image.getRGB(x, y) >> 8 & 0xFF;
                 int green = image.getRGB(x, y) & 0xFF;

                 int pixel= (red+blue+green)/3;
                 if (y==0 && x>0)
                 summeredTable[y][x] = pixel+ summeredTable[0][x-1];
               else  if (x==0 && y>0) summeredTable[y][x] = pixel+ summeredTable[y-1][0];
               else if (x>0 && y>0)summeredTable[y][x] = pixel+ summeredTable[y][x-1]+ summeredTable[y-1][x] - summeredTable[y-1][x-1];
               else summeredTable[y][x] = pixel;
             }
         }
//         for (int i = 0; i < summeredTable.length; i++) {
//            System.out.println(Arrays.toString(summeredTable[i]));
//        }


     }
     public BufferedImage binarization(){
         for (int y=w;y<height-w;y++){
             for (int x=w;x<width-w;x++){
                 int red = image.getRGB(x, y) >> 16 & 0xFF;
                 int blue = image.getRGB(x, y) >> 8 & 0xFF;
                 int green = image.getRGB(x, y) & 0xFF;

                 int pixel=  (red+blue+green)/3;
                 double  s;

                s= Math.abs(summeredTable[y+d-1][x+d-1] + summeredTable[y-d][x-d]) - Math.abs(summeredTable[y-d][x+d-1] + summeredTable[y+d-1][x-d]);
               double m = s/(w*w);
               double delta = pixel - m;
               double  T = m * Math.abs(1+(k*( (delta/(1-delta))-1)));
                 System.out.println(T);
               if (pixel>=(int )T){
                   image.setRGB(x,y, Color.WHITE.getRGB());
               }else image.setRGB(x,y, Color.BLACK.getRGB());

             }
         }
        return image;
     }
}

