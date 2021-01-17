package Images;


import com.sun.istack.internal.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;


public class ImageResizer {
    @NotNull
  public static BufferedImage resize(BufferedImage image, int scaledWidth, int scaledHeight) {
        BufferedImage outputImage;
      //System.out.println(scaledWidth+" "+scaledHeight);
         outputImage = new BufferedImage(scaledWidth,
                scaledHeight, image.getType());

        Graphics2D g2d = outputImage.createGraphics();
        g2d.fillRect(0, 0,  scaledWidth, scaledHeight);
        g2d.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2d.drawImage(image,0,0,scaledWidth,scaledHeight,null);

      g2d.dispose();

        return outputImage;
    }
}
