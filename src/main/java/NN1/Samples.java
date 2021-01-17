package NN1;


import Images.ImageResizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Samples extends Thread {
    public double[][] images;
    public int[] digits;
    public static NeuralNetwork nn;

    public Samples()  {
        init();
    }

    private void init(){
        File folder = new File("Samples");
        File[] listOfFiles = folder.listFiles();
        images = new double[listOfFiles.length][40*60];
        digits = new int[listOfFiles.length];

        for (int i=0;i<listOfFiles.length;i++) {
            if (listOfFiles[i].isFile()) {
                try {
                    digits[i] = Integer.parseInt(listOfFiles[i].getName().charAt(1) + "");
                    System.out.println(digits[i]);
                    BufferedImage sample = ImageIO.read(listOfFiles[i]);
                    images[i] = transformToInput(sample) ;

                } catch (Exception e){
                    System.out.println("Not an image");
                }
            }
        }
    }

    public static double[] transformToInput(BufferedImage image){
    if (image.getHeight()!=40 && image.getWidth()!=60) {
        ImageResizer.resize(image, 60, 40);
    }
        double[] neurones = new double[2400];
        int k=0;
        for (int y = 0; y < 40; y++) {
            for (int x = 0; x < 60; x++) {
                if (new Color(image.getRGB(x, y)).equals(Color.BLACK)) {
                    neurones[k] = 0;
                    //System.out.print(0+" ");
                }
                else {
                    neurones[k] = 1.0;
                    //System.out.print(1+ " ");
                }
                k++;
//             neurones[y+x*40]  = (image1.getRGB(x, y) & 0xff) / 255.0;
//                System.out.print((image1.getRGB(x, y) & 0xff) / 255.0+" ");
            }
          //  System.out.println();


        }
        return neurones;
    }

    @Override
    public void run() {
        Samples s =  new Samples();
        nn = new NeuralNetwork(2400,10);
        NeuralNetwork.learning(s.images, s.digits,nn);
    }

//    public static void main(String[] args) {
//        BufferedImage image1=null;
//        try {
//            //File file = new File("Standards/E8.png");
//            File file = new File("Samples/A1.png");
//            image1 = ImageIO.read(file);
//
//
//        } catch (Exception e){
//            System.out.println("Not an image");
//        }
//        Samples s =  new Samples();
//        nn = new NeuralNetwork(2400,10);
//        NeuralNetwork.learning(s.images, s.digits,nn);
//       int j= NeuralNetwork.classification(image1,nn);
//        System.out.println("digit: 1"+" NN: "+j);
//    }


}
