package Filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Binarization {
    public static BufferedImage binarization(BufferedImage image){
        BufferedImage outputImage = new BufferedImage(image.getWidth(),
                image.getHeight(), image.getType());

        for (int y=0;y<image.getHeight();y++){
            for (int x=0;x<image.getWidth();x++){
                int red = image.getRGB(x, y) >> 16 & 0xFF;
                int blue = image.getRGB(x, y) >> 8 & 0xFF;
                int green = image.getRGB(x, y) & 0xFF;

                int average= (red+blue+green)/3;

                if (average<=127){
                    outputImage.setRGB(x,y, Color.WHITE.getRGB());
                }
                else outputImage.setRGB(x,y, Color.BLACK.getRGB());
            }
        }



        return outputImage;
    }
}
