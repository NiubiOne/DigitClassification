package Images;
// клас який зберігає зображення та місце положення на формі
import java.awt.image.BufferedImage;

public class ImageClass{
 private   BufferedImage image;
 private   BufferedImage imageCopy;
 private String name=null;
  private  int x=0, y=0;
   private int x0=0, y0=0;

    public ImageClass(BufferedImage image) {
        this.imageCopy = image;
        this.image = image;
    }

    public ImageClass(BufferedImage image, int x, int y) {
        this.imageCopy = image;
        this.image = image;
        this.x = x;
        this.y = y;
        this.x0 = x;
        this.y0 = y;
    }

    public ImageClass(BufferedImage image, String name) {
        this.imageCopy = image;
        this.image = image;
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX0() {
        return x0;
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public int getY0() {
        return y0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImageCopy() {
        return imageCopy;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
            if (name==null){
                name = "CreatedImg" + (int) (Math.random() * Integer.MAX_VALUE);
            }
        return name;

    }
}