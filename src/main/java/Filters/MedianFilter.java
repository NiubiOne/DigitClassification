package Filters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class MedianFilter {


    public  static BufferedImage apply(BufferedImage img) {
        BufferedImage TransformedImage;
        TransformedImage = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                ArrayList<Integer> red = new ArrayList<>();
                ArrayList<Integer> blue = new ArrayList<>();
                ArrayList<Integer> green = new ArrayList<>();
                int h=3,w=3;
                if (y+h>=img.getHeight()) h= img.getHeight()-y;
                if (x+w>=img.getWidth()) w= img.getWidth()-x;

                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
                        red.add(img.getRGB(x + j, y + i) >> 16 & 0xFF);
                        blue.add(img.getRGB(x + j, y + i) >> 8 & 0xFF);
                        green.add(img.getRGB(x + j, y + i) & 0xFF);
                    }
                }

                Collections.sort(red);
                Collections.sort(blue);
                Collections.sort(green);

                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
                        int color = new Color(red.get(red.size()/2), blue.get(blue.size()/2), green.get(green.size()/2)).getRGB();
                        TransformedImage.setRGB(j + x, i + y, color);
                    }
                }

            }
        }

        return TransformedImage;
    }

}
