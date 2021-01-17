package Filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TemporalFilter {
    public static  BufferedImage[]  temporalFilter(BufferedImage[] images) {
        BufferedImage[] transformed=new BufferedImage[images.length];
        BufferedImage TransformedImage;
        for (int p=0;p<images.length;p++) {
            TransformedImage = new BufferedImage(images[p].getWidth(), images[p].getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            for (int y = 0; y < images[p].getHeight(); y++) {
                for (int x = 0; x < images[p].getWidth(); x++) {
                    int ared=0,ablue=0,agreen=0;
                    int b;
                    if (p+3>=images.length) b= images.length-p;
                    else b=3;

                    for (int count=0;count<b;count++){
                        ared +=images[count+p].getRGB(x , y ) >> 16 & 0xFF;
                        ablue+=images[count+p].getRGB(x , y ) >> 8 & 0xFF;
                        agreen+=images[count+p].getRGB(x , y ) & 0xFF;
                    }



                    int color = new Color(ared/b, ablue/b, agreen/b).getRGB();
                    TransformedImage.setRGB( x,  y, color);


                }
            }
            transformed[p] = TransformedImage;
        }
        return transformed;
    }
}
