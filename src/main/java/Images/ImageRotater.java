package Images;


import java.awt.image.BufferedImage;

public class ImageRotater {

    public static BufferedImage rotateLeft(BufferedImage image) {
        System.out.println("Розмір"+image.getWidth()+" "+image.getHeight());
        int newHeight= image.getWidth();
        int newWidth= image.getHeight();
        BufferedImage outputImage = new BufferedImage(newWidth,newHeight, image.getType());
        int varX=newHeight-1;
        int varY=0;

        for (int i=0;i<image.getWidth();i++){
            for (int j=0;j<image.getHeight();j++){
                outputImage.setRGB(j,i,image.getRGB(varX,varY));
                varY++;
            }
            varY=0;
            varX--;
        }


        System.out.println("rotated");

        return outputImage;
    }

}
