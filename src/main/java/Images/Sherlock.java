package Images;

import NN1.NeuralNetwork;
import NN1.Samples;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public  class  Sherlock {
   BufferedImage image;
    ArrayList<Point> points = new ArrayList<>();
     ArrayList<Point> pointsForRectangle = new ArrayList<>();
    int[][] marks;


    public Sherlock(BufferedImage image) {
        this.image = image;
        marks = new int[image.getHeight()][image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (new Color(image.getRGB(x, y)).equals(Color.BLACK)) marks[y][x] = 0;
                else
                    marks[y][x] = 1;
            }
        }
        hideEdges();
        setMarks();

//                for (int i = 0; i < marks.length; i++) {
//            System.out.println(Arrays.toString(marks[i]));
//        }

    }

     int k;
    public void hideEdges() {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < 25; x++) {
                if (marks[y][x]==1) {
                    searchEdges(y,x);
                    k=0;
                }
            }
        }

        for (int x = image.getWidth() - 1; x > image.getWidth() - 25; x--) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (marks[y][x]==1) {
                    searchEdges(y, x);
                    k=0;

                }
            }
        }

        for (int y = image.getHeight() - 1; y > image.getHeight()-25 ; y--) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (marks[y][x]==1) {
                    searchEdges(y, x);
                    k=0;

                }
            }
        }

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (marks[y][x]==0){
                    image.setRGB(x,y,Color.BLACK.getRGB());
                }
                else
                    image.setRGB(x,y,Color.WHITE.getRGB());
            }
        }


    }


    public void setMarks() {

        //System.out.println(image.getWidth() + ":" + image.getHeight());


//        for (int i = 0; i < marks.length; i++) {
//            System.out.println(Arrays.toString(marks[i]));
//        }
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
               if (marks[y][x]==1){
                   search(y,x);
                   //System.out.println(points.size());
                   if (points.size()<35) {
                       for (Point i:points)
                      image.setRGB(i.getX(),i.getY(),Color.BLACK.getRGB());
                       points.clear();
                   }
                   k=0;
               }
               if (points.size()>0 && points.size()<4000) {
                   points.sort(Point.byX);
                   pointsForRectangle.add(points.get(0));
                   pointsForRectangle.add(points.get(points.size() - 1));
                 //  System.out.println(points);
                   points.sort(Point.byY);
                   pointsForRectangle.add(points.get(0));
                   pointsForRectangle.add(points.get(points.size() - 1));
                   //System.out.println(points);
               }
                points.clear();
            }
        }
//                for (int i = 0; i < marks.length; i++) {
//            System.out.println(Arrays.toString(marks[i]));
//        }


    }

    void search(int y, int x) {
        points.add(new Point(x, y));
        marks[y][x]=2;
        k++;
        if (k<5000) {
            try {
                if (x + 1 < image.getWidth() - 1 && y < image.getHeight() - 1) {
                    if (marks[y][x + 1] == 1) {
                        search(y, x + 1);
                    }
                    if (marks[y + 1][x] == 1) {
                        search(y + 1, x);
                    }
                }
                if (x - 1 >= 0 && y - 1 >= 0) {
                    if (marks[y][x - 1] == 1) {
                        search(y, x - 1);
                    }
                    if (marks[y - 1][x] == 1) {
                        search(y - 1, x);
                    }
                }
            } catch (Exception e) {
                System.out.println("Object os too big");
            }
        }

    }

    void searchEdges(int y, int x) {
        k++;
        marks[y][x] = 0;
       if (k<6200){
           if (x + 1 < image.getWidth() - 1 && y < image.getHeight() - 1) {
               if (marks[y][x + 1] == 1) {
                   searchEdges(y, x + 1);
               }
               if (marks[y + 1][x] == 1) {
                   searchEdges(y + 1, x);
               }
           }
           if (x - 1 >= 0 && y - 1 >= 0) {
               if (marks[y][x - 1] == 1) {
                   searchEdges(y, x - 1);
               }
               if (marks[y - 1][x] == 1) {
                   searchEdges(y - 1, x);
               }
           }
       }

    }

   public BufferedImage drawRectangles(BufferedImage image) {
       for (int i = 0; i < pointsForRectangle.size(); i += 4) {
           try {
               for (int x = pointsForRectangle.get(i).getX() - 5; x < pointsForRectangle.get(i + 1).getX() + 5; x++) {
                   image.setRGB(x, pointsForRectangle.get(i + 2).getY() - 5, Color.RED.getRGB());
               }
           } catch (Exception e) {
               for (int x = pointsForRectangle.get(i).getX(); x < pointsForRectangle.get(i + 1).getX(); x++) {
                   image.setRGB(x, pointsForRectangle.get(i + 2).getY(), Color.RED.getRGB());
               }
           }
           try {
               for (int y = pointsForRectangle.get(i + 2).getY() - 5; y < pointsForRectangle.get(i + 3).getY() + 5; y++) {
                   image.setRGB(pointsForRectangle.get(i).getX() - 5, y, Color.RED.getRGB());
               }
           } catch (Exception e) {
               for (int y = pointsForRectangle.get(i + 2).getY(); y < pointsForRectangle.get(i + 3).getY(); y++) {
                   image.setRGB(pointsForRectangle.get(i).getX(), y, Color.RED.getRGB());
               }
           }
           try {
               for (int y = pointsForRectangle.get(i + 2).getY() - 5; y < pointsForRectangle.get(i + 3).getY() + 5; y++) {
                   image.setRGB(pointsForRectangle.get(i + 1).getX() + 5, y, Color.RED.getRGB());
               }
           }catch (Exception e) {
               for (int y = pointsForRectangle.get(i + 2).getY(); y < pointsForRectangle.get(i + 3).getY(); y++) {
                   image.setRGB(pointsForRectangle.get(i + 1).getX(), y, Color.RED.getRGB());
               }
           }
           try {
               for (int x = pointsForRectangle.get(i).getX() - 5; x < pointsForRectangle.get(i + 1).getX() + 5; x++) {
                   image.setRGB(x, pointsForRectangle.get(i + 3).getY() + 5, Color.RED.getRGB());
               }
           }catch (Exception e){
               for (int x = pointsForRectangle.get(i).getX(); x < pointsForRectangle.get(i + 1).getX(); x++) {
                   image.setRGB(x, pointsForRectangle.get(i + 3).getY(), Color.RED.getRGB());
               }

           }
       }
       return image;
   }





    public  void saveObjects() throws IOException {
        int k = 0;
        Classification classification = new Classification();
            for (int i = 0; i < pointsForRectangle.size(); i += 4) {
                k++;
                int x0 = pointsForRectangle.get(i).getX() - 2;
                int x1 = pointsForRectangle.get(i + 1).getX() + 2;
                int y0 = pointsForRectangle.get(i + 2).getY() - 2;
                int y1 = pointsForRectangle.get(i + 3).getY() + 2;

                try {
                    BufferedImage object = ImageFromImage.createNewImage(image, x0, y0, x1, y1);
                    object = ImageResizer.resize(object, 60, 40);
                    System.out.print(k);
                    int j = classification.toClass(object);
                    System.out.println("number "+j);
                    switch (j) {
                        case 0: ImageIO.write(object, "png", new File("FoundObjects/Zero.png"));
                          break;
                        case 1: ImageIO.write(object, "png", new File("FoundObjects/One.png"));
                            break;
                        case 2: ImageIO.write(object, "png", new File("FoundObjects/Two.png"));
                            break;
                        case 3: ImageIO.write(object, "png", new File("FoundObjects/Three.png"));
                            break;
                        case 4: ImageIO.write(object, "png", new File("FoundObjects/Four.png"));
                            break;
                        case 5: ImageIO.write(object, "png", new File("FoundObjects/Five.png"));
                            break;
                        case 6: ImageIO.write(object, "png", new File("FoundObjects/Six.png"));
                            break;
                        case 7: ImageIO.write(object, "png", new File("FoundObjects/Seven.png"));
                            break;
                        case 8: ImageIO.write(object, "png", new File("FoundObjects/Eight.png"));
                            break;
                        case 9: ImageIO.write(object, "png", new File("FoundObjects/Nine.png"));
                            break;
                        case 10: ImageIO.write(object, "png", new File("FoundObjects/Unknown"+ (int) (Math.random() * 50) +1+".png"));
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Failed to save");
                }

            }
    }

    public BufferedImage drawClass(BufferedImage image) {
        Classification classification = new Classification();

        for (int i = 0; i < pointsForRectangle.size(); i += 4) {
            int x0 = pointsForRectangle.get(i).getX() - 2;
            int x1 = pointsForRectangle.get(i + 1).getX() + 2;
            int y0 = pointsForRectangle.get(i + 2).getY() - 2;
            int y1 = pointsForRectangle.get(i + 3).getY() + 2;
            int xScaled = (x1-x0)/2;
            int yScaled = (y1-y0)/3;
            int j=10;
            try {
                BufferedImage object = ImageFromImage.createNewImage(this.image, x0, y0, x1, y1);
                object = ImageResizer.resize(object, 60, 40);
                 j = classification.toClass(object);
            } catch (Exception e){
                System.out.println("object out of bounds");
            }

            try {
                File file = new File("Standards/E" + j + ".png");
               BufferedImage image1 = ImageResizer.resize(ImageIO.read(file),xScaled,yScaled);
                Graphics g = image.getGraphics();
                g.drawImage(image1,x0,y0,null);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
        return image;
    }

    public BufferedImage drawClassFromNN(BufferedImage image,NeuralNetwork nn) {
        for (int i = 0; i < pointsForRectangle.size(); i += 4) {
            int x0 = pointsForRectangle.get(i).getX() - 2;
            int x1 = pointsForRectangle.get(i + 1).getX() + 2;
            int y0 = pointsForRectangle.get(i + 2).getY() - 2;
            int y1 = pointsForRectangle.get(i + 3).getY() + 2;
            int xScaled = (x1-x0)/2;
            int yScaled = (y1-y0)/3;
            int j=10;
            try {
                BufferedImage object = ImageFromImage.createNewImage(this.image, x0, y0, x1, y1);
                object = ImageResizer.resize(object, 60, 40);
                j = NeuralNetwork.classification(object,nn);
            } catch (Exception e){
                System.out.println("object out of bounds");
            }

            try {
                File file = new File("Standards/E" + j + ".png");
                BufferedImage image1 = ImageResizer.resize(ImageIO.read(file),xScaled,yScaled);
                Graphics g = image.getGraphics();
                g.drawImage(image1,x0,y0,null);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return image;
    }
}
