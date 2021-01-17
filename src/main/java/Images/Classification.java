package Images;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class Classification {
    int[][][]  marks = new int[10][40][60];

    public Classification() {
        for (int i = 0; i < 10; i++) {
            BufferedImage image = null;
            try {
                File file = new File("Standards/E" + i + ".png");
                image = ImageIO.read(file);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            if (image != null) {
                for (int y = 0; y < image.getHeight(); y++) {
                    for (int x = 0; x < image.getWidth(); x++) {
                        if (new Color(image.getRGB(x, y)).equals(Color.BLACK)) marks[i][y][x] = 0;
                        else
                            marks[i][y][x] = 2;
                    }
                }
            }
        }
    }

    public int toClass(BufferedImage image) {
        double max=0;
        int klas=0;
        double[] rating = new double[10];
            int[][] m = new int[image.getHeight()][image.getWidth()];
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    if (new Color(image.getRGB(x, y)).equals(Color.BLACK)) m[y][x] = 0;
                    else
                        m[y][x] = 1;
                }
            }
        for (int i = 0;i<10;i++) {
            int sum = 0 ;
            int sum1 =0 ;
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    sum += m[y][x];
                    sum1 += Math.abs(m[y][x] - marks[i][y][x]);
                }
            }
            rating[i] = (float) sum / sum1;
            if(max<rating[i]){
                max = rating[i];
                klas = i;
            }
        }
            System.out.println(Arrays.toString(rating));
       if (max>=0.70) return klas;
       else return 10;
    }

}
