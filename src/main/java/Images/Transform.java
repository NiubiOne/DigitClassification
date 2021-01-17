package Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;


public class Transform {


public static void transformImage(String path) throws IOException {
    BufferedImage img = ImageIO.read(new File(path));
     FileWriter file = new FileWriter("TransformedFile.txt");
file.write(img.getHeight()+"\n"+img.getWidth()+"\n");
int rgb;
   // System.out.println(img.getHeight()+" "+img.getWidth());
    for(int y=0;y<img.getHeight();y++){
        for (int x=0;x<img.getWidth();x++) {
            rgb = img.getRGB(x,y);
            ArrayList<Integer> list = new ArrayList<>();
            list.add ((rgb >> 16) & 0xFF);
            list.add ((rgb >>  8) & 0xFF);
            list.add( (rgb      ) & 0xFF);

            for (int k = 0; k < 3; k++) {
              //  System.out.println(rgb1[x][y][k]+ ": "+ x + ":"+y+" "+k);
                file.write(String.format("%08d",new BigInteger(Integer.toBinaryString(list.get(k))))+"\n");
            }
        }
    }
file.close();

}

public static void transformTxt(String path) throws IOException {
    BufferedImage imageFromTxt;
    FileReader fileReader = new FileReader(path);
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    try {
        int y=Integer.parseInt(bufferedReader.readLine());
        int x=Integer.parseInt(bufferedReader.readLine());
        imageFromTxt = new BufferedImage(x,y,BufferedImage.TYPE_INT_BGR);
        for (int i=0;i<y;i++) {
            for (int j = 0; j < x; j++) {
                ArrayList<Integer> list = new ArrayList<>();

                list.add(Integer.parseInt(bufferedReader.readLine(), 2));
                list.add(Integer.parseInt(bufferedReader.readLine(), 2));
                list.add(Integer.parseInt(bufferedReader.readLine(), 2));
                //System.out.println(i+":"+j+ " " + list.toString());
                int a = new Color(list.get(0),list.get(1),list.get(2)).getRGB();
                imageFromTxt.setRGB(j, i, a);

                list.clear();
            }
        }
        ImageIO.write(imageFromTxt,"bmp",new File("TransformedImageFromTxt.bmp"));

    } catch (FileNotFoundException e) {
        System.out.println(" An error occurred.");
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    bufferedReader.close();

}

}
