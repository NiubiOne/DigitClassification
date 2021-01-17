package Filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BredliBinarization {
    BufferedImage image;
    int[] src;
    int width, height;
    int S;
    int s2;
    int[] integralImage;
    double k = 0.15;


    public BredliBinarization(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        integralImage = new int[width * height];
        src = new int[width * height];
        S = width / 8;
        s2 = S / 2;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int index = j * width + i;
                int red = image.getRGB(i, j) >> 16 & 0xFF;
                int blue = image.getRGB(i, j) >> 8 & 0xFF;
                int green = image.getRGB(i, j) & 0xFF;
                int pixel = (red + blue + green) / 3;
                src[index] = pixel;
            }
        }
        integralImage();
    }

    private void integralImage() {

        for (int i = 0; i < width; i++) {
            int sum = 0;
            for (int j = 0; j < height; j++) {
                int index = j * width + i;
                sum += src[index];
                if (i == 0) {
                    integralImage[index] = sum;
                } else integralImage[index] = integralImage[index - 1] + sum;
            }
        }
//         for (int i = 0; i < summeredTable.length; i++) {
//            System.out.println(Arrays.toString(summeredTable[i]));
//        }
    }

    public BufferedImage binarization() {
        BufferedImage outputImage = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int index = j * width + i;
                int x1 = i - s2;
                int x2 = i + s2;
                int y1 = j - s2;
                int y2 = j + s2;

                if (x1 < 0)
                    x1 = 0;
                if (x2 >= width)
                    x2 = width - 1;
                if (y1 < 0)
                    y1 = 0;
                if (y2 >= height)
                    y2 = height - 1;

                int count = (x2 - x1) * (y2 - y1);
                int sum = integralImage[y2 * width + x2] - integralImage[y1 * width + x2] -
                        integralImage[y2 * width + x1] + integralImage[y1 * width + x1];

                if ( (src[index] * count) < (sum * (1.0 - k)))
                    outputImage.setRGB(i, j, Color.WHITE.getRGB());
                else
                    outputImage.setRGB(i, j, Color.BLACK.getRGB());

            }
        }
        return outputImage;
    }
}
