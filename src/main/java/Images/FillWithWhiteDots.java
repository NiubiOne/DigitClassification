package Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FillWithWhiteDots {
    public static void fill(BufferedImage image) {
        int a = (image.getHeight()*image.getWidth()) / 20;

        for (int i = 0; i < a; i++) {
            int x = (int)(Math.random()*image.getWidth());
            int y = (int)(Math.random()*image.getHeight());
            image.setRGB(x,y,new Color(255,255,255).getRGB());
        }
        try {
            ImageIO.write(image, "png", new File("WithWhiteDots.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}