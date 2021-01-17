package Loaders;

import Filters.*;
import Images.*;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ImageForm {
    private  ImageClass selectedImageFormList;
    private  final ArrayList<ImageClass> images = new ArrayList<>();
    private DefaultListModel<ImageClass> imageList;
    private  boolean moveImage = false;
    private  boolean cut = false;

    int saveImgFromX;
    int saveImgFromY;
    int saveImgToX;
    int saveImgToY;

    int posX;
    int posY;


    public void loadImage() {
        ImageFrame form = new ImageFrame();
        form.initialize();
    }



    class ImageFrame extends JFrame {

        JPanel paneImg = new ImagePanel();

        int jvalue, jvalue1;

        JPanel pane1 = new JPanel();
        JPanel pane2 = new JPanel();
        JPanel pane3 = new JPanel();

        JSlider minValueSlider = new JSlider(0, 255, 0);
        JSlider maxValueSlider = new JSlider(0, 255, 0);

        JLabel label = new JLabel();

        JButton openImgBtn = new JButton("Відкрити зображення");
        JButton medianFilterBtn = new JButton("Застосувати медіанний фільтр");
        JButton contrastBtn = new JButton("Лінійне контрастування");
        JButton binarBtn = new JButton("Бінаризація");
        JButton BredliBinarBtn = new JButton("мет. Бредлі");
        JButton findObjBtn = new JButton("Пошук об'єктів");
        JButton delete = new JButton("Видалити");
        JButton save = new JButton("Save");

        void initialize() {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
            setResizable(true);
            Container container;
            container = getContentPane();
//Панель інструментів
            JToolBar jToolBar = new JToolBar();
            jToolBar.setFloatable(false);
            jToolBar.setRollover(true);
            JButton toolButton1 = new JButton("Move");
            JButton toolButton2 = new JButton("Cut");
            JButton toolButton4 = new JButton("Rotate");
            //URL imageURL = ImageForm.class.getResource("src/resources/Images/move.png");
            // toolButton1.setIcon(new ImageIcon(imageURL));
            jToolBar.add(toolButton1);
            jToolBar.add(toolButton2);
            jToolBar.add(toolButton4);



 //
 //Список відкритих зображень
            imageList = new DefaultListModel<>();
            JList<ImageClass> jList = new JList<>(imageList);
            jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jList.setLayoutOrientation(JList.VERTICAL);
            jList.setFixedCellHeight(30);
            jList.setFixedCellWidth(120);
            JScrollPane listScroller = new JScrollPane(jList);
            listScroller.setPreferredSize(new Dimension(250, 80));
//

            pane3.add(jToolBar);

            paneImg.setAlignmentX(Component.CENTER_ALIGNMENT);
            paneImg.setBackground(Color.WHITE);

            JLabel pos = new JLabel(posX+" "+posY);
            pos.setMaximumSize(new Dimension(50,20));
//Створення макету
            pane1.setLayout(new BoxLayout(pane1, BoxLayout.Y_AXIS));
            pane2.setLayout(new BoxLayout(pane2, BoxLayout.Y_AXIS));
            pane3.setLayout(new FlowLayout(FlowLayout.LEFT));
            container.setLayout(new BorderLayout());

//
            openImgBtn.setAlignmentX(CENTER_ALIGNMENT);
            medianFilterBtn.setAlignmentX(CENTER_ALIGNMENT);
            contrastBtn.setAlignmentX(CENTER_ALIGNMENT);
            delete.setAlignmentX(CENTER_ALIGNMENT);
            save.setAlignmentX(CENTER_ALIGNMENT);
            findObjBtn.setAlignmentX(CENTER_ALIGNMENT);
            binarBtn.setAlignmentX(LEFT_ALIGNMENT);
            BredliBinarBtn.setAlignmentX(RIGHT_ALIGNMENT);

            openImgBtn.setMaximumSize(new Dimension(200, 55));
            medianFilterBtn.setMaximumSize(new Dimension(200, 55));
            contrastBtn.setMaximumSize(new Dimension(200, 55));
            delete.setMaximumSize(new Dimension(120, 30));
            save.setMaximumSize(new Dimension(120, 30));
            findObjBtn.setMaximumSize(new Dimension(150, 35));
            binarBtn.setMaximumSize(new Dimension(150, 35));
            BredliBinarBtn.setMaximumSize(new Dimension(150, 35));

            JPanel binarizationButtons = new JPanel();
            binarizationButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
            binarizationButtons.add(binarBtn);
            binarizationButtons.add(BredliBinarBtn);

            pane1.add(openImgBtn);
            pane1.add(Box.createVerticalStrut(10));
            pane1.add(medianFilterBtn);
            pane1.add(Box.createVerticalStrut(10));
            pane1.add(contrastBtn);
            pane1.add(Box.createVerticalStrut(10));
            pane1.add(minValueSlider);
            pane1.add(maxValueSlider);
            pane1.add(Box.createVerticalStrut(10));
            pane1.add(binarizationButtons);
            pane1.add(Box.createVerticalStrut(10));
            pane1.add(findObjBtn);


           // pane1.add(Box.createVerticalStrut(0));
            pane1.add(label);
            pane1.add(Box.createVerticalStrut(10));
            pane1.add(jList);
            pane1.add(Box.createVerticalStrut(10));
            pane1.add(delete);
            pane1.add(Box.createVerticalStrut(10));
            pane1.add(save);


            add(pane1, BorderLayout.EAST);
            add(paneImg, BorderLayout.CENTER);
            add(pane3, BorderLayout.PAGE_START);
            add(pos, BorderLayout.PAGE_END);
            pack();

            openImgBtn.addActionListener(e -> {
                try {
                    BufferedImage image;
                    File file= new File(FileLoader.load(this));
                    image = ImageIO.read(file);
                    System.out.println(image.getColorModel() + ":  " + image.getType());
                    ImageClass obj = new ImageClass(image,file.getName());
                    images.add(obj);
                    imageList.addElement(obj);
                    paneImg.repaint();
                   //FillWithWhiteDots.fill(obj.getImage());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            });

            medianFilterBtn.addActionListener(e -> {
                if (selectedImageFormList !=null)
                selectedImageFormList.setImage(MedianFilter.apply(selectedImageFormList.getImage()));
                paneImg.repaint();
                try {
                    ImageIO.write(selectedImageFormList.getImage(), "png", new File("medianfilter.png"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            contrastBtn.addActionListener(e -> {
            if (selectedImageFormList !=null)
                selectedImageFormList.setImage(LinearContrast.linear(selectedImageFormList.getImage(), jvalue, jvalue1));
                paneImg.repaint();
                try {
                    ImageIO.write(selectedImageFormList.getImage(), "png", new File("contrast.png"));
                    System.out.println("writed");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            findObjBtn.addActionListener(e -> {
                BufferedImage img = selectedImageFormList.getImage();
                ImageClass obj = new ImageClass(Binarization.binarization(LinearContrast.linear(img,255,0)));
                images.add(obj);
                imageList.addElement(obj);
                Sherlock s = new Sherlock(obj.getImage());
                s.drawRectangles(selectedImageFormList.getImage());
                paneImg.repaint();
            });

            binarBtn.addActionListener(e -> {
                if (selectedImageFormList !=null) {
                    selectedImageFormList.setImage(Binarization.binarization(selectedImageFormList.getImage()));
                    paneImg.repaint();
                }
            });

            BredliBinarBtn.addActionListener(e -> {
                if (selectedImageFormList !=null) {
                    BredliBinarization b = new BredliBinarization(selectedImageFormList.getImage());
                    selectedImageFormList.setImage(b.binarization());
                    paneImg.repaint();
                }
            });

            toolButton1.addActionListener(e -> {
                moveImage = !moveImage;
                cut = false;
                if (moveImage)
                toolButton1.setText("Move: On");
                else
                    toolButton1.setText("Move: Off");
                toolButton2.setText("Cut: Off");
            });

            toolButton2.addActionListener(e -> {
                cut = !cut;
                moveImage = false;
                if (cut)
                    toolButton2.setText("Cut: On");
                else toolButton2.setText("Cut: Off");
                toolButton1.setText("Move: Off");

            });

            toolButton4.addActionListener(e -> {
                selectedImageFormList.setImage(ImageRotater.rotateLeft(selectedImageFormList.getImage()));
                paneImg.repaint();
            });

            minValueSlider.addChangeListener(e -> jvalue = minValueSlider.getValue());
            maxValueSlider.addChangeListener(e -> jvalue1 = maxValueSlider.getValue());

            jList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1) {
                        int index = jList.getSelectedIndex();
                            ImageClass item = jList.getSelectedValue();
                        label.setText("Зображення "+(index+1)+"");
                            selectedImageFormList = item;
                            System.out.println(selectedImageFormList);
                    }
                }
            });

            delete.addActionListener(e -> {
                images.remove(selectedImageFormList);
                imageList.removeElement(selectedImageFormList);
                selectedImageFormList = null;
                paneImg.repaint();
            });
            save.addActionListener(e -> {
                    try {
                        ImageIO.write(selectedImageFormList.getImage(), "png", new File(selectedImageFormList.getName()+".png"));
                        System.out.println("ImageSaved");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
            });
        }

        class ImagePanel extends JPanel {
            int width = 1000, height = 600;
            int xOnClick;
            int yOnClick;
            int X;
            int Y;
            boolean isCoordinateInImage = false;
            boolean isCoordinateInEdge = false;
            ImagePanel() {
                setPreferredSize(new Dimension(width, height));
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
                System.out.println(moveImage);

               moveOnPanel();
                selectZone();
                resize();





            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (ImageClass obj : images) {
                    if (obj.getImage() != null) {
                        g.drawImage(obj.getImage(), obj.getX(), obj.getY(), this);
                     //   System.out.println(obj.getX()+" "+ obj.getY());
                    }
                }
            }



            private boolean checkCoordinates(int x, int y, int startX, int startY, BufferedImage image) {
                return x > startX && x < image.getWidth()-10 + startX && y > startY && y < image.getHeight()-10 + startY;
            }

            private boolean checkCoordinatesInEdge(int x, int y, int startX, int startY, BufferedImage image) {
                return x > image.getWidth()-10+startX && x < image.getWidth() + startX && y > image.getHeight()-10+startY && y < image.getHeight() + startY;
            }

           

            private void moveOnPanel() {
                System.out.println(moveImage);
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (moveImage) {
                            xOnClick = e.getX();
                            yOnClick = e.getY();
                            isCoordinateInImage = checkCoordinates(xOnClick, yOnClick, selectedImageFormList.getX(), selectedImageFormList.getY(), selectedImageFormList.getImage());
                        }
                    }
                });

                addMouseMotionListener(new MouseAdapter() {
                    public void mouseDragged(MouseEvent e) {
                       if (moveImage) {
                           if (isCoordinateInImage) {
                               selectedImageFormList.setX(selectedImageFormList.getX0() + (e.getX() - xOnClick));
                               selectedImageFormList.setY(selectedImageFormList.getY0() + (e.getY() - yOnClick));
                               repaint();

                           }
                       }
                    }
                });


                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (cut){
                            BufferedImage newImage;
                        BufferedImage image = new BufferedImage(paneImg.getWidth(), paneImg.getHeight(), BufferedImage.TYPE_INT_RGB);
                        Graphics g = image.createGraphics();
                        g.fillRect(0, 0, saveImgToX - saveImgFromX, saveImgToY - saveImgFromY);
                        paneImg.print(g);
                        g.dispose();

                        newImage = ImageFromImage.createNewImage(image, saveImgFromX, saveImgFromY, saveImgToX, saveImgToY);
                        ImageClass obj = new ImageClass(newImage, saveImgFromX, saveImgFromY);

                        images.add(obj);
                        imageList.addElement(obj);
                        paneImg.repaint();
                    }
                }
                });
            }


            private void selectZone() {
                System.out.println(cut);

                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (cut) {
                            saveImgFromX = e.getX();
                            saveImgFromY = e.getY();
                        }
                    }
                });

                addMouseMotionListener(new MouseAdapter() {
                    public void mouseDragged(MouseEvent e) {
                        if (cut) {
                            saveImgToX = e.getX();
                            saveImgToY = e.getY();
                        }
                    }
                });

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (moveImage) {
                            selectedImageFormList.setX0(selectedImageFormList.getX());
                            selectedImageFormList.setY0(selectedImageFormList.getY());
                        }
                    }
                });
            }

            private void resize() {
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (selectedImageFormList !=null) {
                            xOnClick = e.getX();
                            yOnClick = e.getY();
                            isCoordinateInEdge = checkCoordinatesInEdge(xOnClick, yOnClick, selectedImageFormList.getX(), selectedImageFormList.getY(), selectedImageFormList.getImage());
                        }
                    }
                });

                addMouseMotionListener(new MouseAdapter() {
                    public void mouseDragged(MouseEvent e) {
                        if(isCoordinateInEdge){
                            X = e.getX();
                            Y = e.getY();


                            selectedImageFormList.setImage(ImageResizer.resize(selectedImageFormList.getImageCopy(), selectedImageFormList.getImage().getWidth()+(X-xOnClick), selectedImageFormList.getImage().getHeight()+(Y-yOnClick)));
                           xOnClick =X;
                           yOnClick =Y;
                            repaint();

                        }

                        }
                });

            }

            }
        }



}



