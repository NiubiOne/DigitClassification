package Filters;

import java.awt.*;
import java.awt.image.BufferedImage;



public class LinearContrast {

    public static BufferedImage linear(BufferedImage image,int ymax1,int ymin1){
        BufferedImage bi = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);

        int xmax=0,xmin=255;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int red = image.getRGB(x, y) >> 16 & 0xFF;
                int blue = image.getRGB(x, y) >> 8 & 0xFF;
                int green = image.getRGB(x, y) & 0xFF;
                // перетворення зображення у gray scale
                int grey =(int)(0.2126*red+0.7152*green+0.0722*blue);


                if (grey<xmin) xmin =grey;
                if (grey>xmax) xmax = grey;


            }
        }
        //System.out.println(xmax+"  "+ xmin);

        for (int y = 0; y < image.getHeight() ; y++) {
            for (int x = 0; x < image.getWidth() ; x++) {
                int red = image.getRGB(x, y) >> 16 & 0xFF;
                int blue = image.getRGB(x, y) >> 8 & 0xFF;
                int green = image.getRGB(x, y) & 0xFF;
                double grey =(0.2126*red+0.7152*green+0.0722*blue);
                // формула лінійного контрастування
                int newgray = (int) (((grey-xmin)/(xmax-xmin)*(ymax1 - ymin1)+ ymin1));
                if (newgray>255){
                    newgray =255;
                }
                if (newgray<0) newgray=0;
               int col = new Color( (newgray),(newgray),(newgray)).getRGB();
            bi.setRGB(x,y,col);
               // System.out.println(newgray);
            }
        }

        return bi;
    }

}

