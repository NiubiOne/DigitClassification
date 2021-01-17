package Images;

import java.awt.image.BufferedImage;

public class ImageFromImage {


    public static BufferedImage createNewImage(BufferedImage image, int x, int y, int x1, int y1) {

        int width = Math.abs(x1 - x);
        int height = Math.abs(y1 - y);
        BufferedImage newImage = null;
        // System.out.println(x+" "+x1+" /"+y+" "+y1+" /"+ width+" "+height);
        if (height > 0 && width > 0) {
            newImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            int varX = x, varY = y;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    newImage.setRGB(j, i, image.getRGB(varX, y));
                    varX++;
                }
                varX = x;
                y++;
            }
        }
        return newImage;

    }
}
