package Camera;

import Filters.Binarization;
import Filters.BredliBinarization;
import Filters.LinearContrast;
import Images.Sherlock;
import NN1.Samples;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class LoadFromCamera extends Thread {
    private  Webcam webcam = Webcam.getDefault();
    private Webcam[] cams = Webcam.getWebcams().toArray(new Webcam[0]);
    private Dimension[] dimensions = Webcam.getDefault().getViewSizes();
    private    WebcamPanel webcamPanel;
    private boolean drawClas;
    private boolean neuralNetwork;
    private Dimension choosedDimension;
    private Webcam choosedCamera = Webcam.getDefault();

    public void run() {
        CameraUI cameraUI = new CameraUI();
        cameraUI.createUI();

    }




    private class CameraUI extends JFrame {
        void createUI() {
            setTitle("WebCam");
            webcam.setViewSize(new Dimension(320, 240));
            setMinimumSize(new Dimension(800, 600));
            webcamPanel = camPanelInit(new WebcamPanel(webcam));

            JPanel webCamChooser = new JPanel(new FlowLayout());

            JComboBox jComboBox = new JComboBox(cams);
            JComboBox jComboBox1 = new JComboBox(dimensions);
            jComboBox.setMaximumSize(new Dimension(400, 80));

            JButton jButton = new JButton("Знімок з камери");
            JButton jButton1 = new JButton("Змінити");
            JCheckBox sObj = new JCheckBox("Пошук об'єктів");
            JCheckBox sClas = new JCheckBox("Клас об'єктів");
            JCheckBox sNN = new JCheckBox("Клас за НМ");
            JButton jButton2  = new JButton("Зберегти об'єкти");
            sObj.setSelected(false);


            webcamPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            jButton2.setAlignmentX(Component.CENTER_ALIGNMENT);
           jButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            sObj.setAlignmentX(Component.CENTER_ALIGNMENT);
            sClas.setAlignmentX(Component.CENTER_ALIGNMENT);
            sNN.setAlignmentX(Component.CENTER_ALIGNMENT);
            jComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel options = new JPanel();
            options.setLayout(new BoxLayout(options,BoxLayout.Y_AXIS));

            webCamChooser.add(jComboBox);
            webCamChooser.add(jComboBox1);
            webCamChooser.add(jButton1);

            Container pane = this.getContentPane();
            pane.setLayout(new BorderLayout());



            pane.add(webCamChooser,BorderLayout.PAGE_START);
            pane.add(webcamPanel,BorderLayout.CENTER);


            options.add(jButton);
            options.add(Box.createVerticalStrut(5));
            options.add(sObj);
            options.add(sClas);
            options.add(sNN);
            options.add(Box.createVerticalStrut(5));
            options.add(jButton2);
            options.add(Box.createVerticalStrut(10));

            pane.add(options,BorderLayout.LINE_END);

            pack();
            setVisible(true);

            buttonEvent(jButton);

            jButton2.addActionListener(e -> {
                Sherlock s = new Sherlock(webcamPanel.getImage());
                try {
                    s.saveObjects();
                    System.out.println("saved");

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });



            sObj.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    webcam.close();
                    pane.remove(1);
                    webcamPanel = camPanelInit(new CamPanel(webcam));
                    pane.add(webcamPanel,1);

                }
                else {
                    webcam.close();
                    pane.remove(1);
                    webcamPanel = camPanelInit(new WebcamPanel(webcam));
                    pane.add(webcamPanel,1);
                }
                pack();

            });
            sClas.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    drawClas = true;
                    neuralNetwork = false;
                }
                else drawClas =false;
            });

            sNN.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    neuralNetwork = true;
                    drawClas =false;
                }
                else neuralNetwork = false;
            });

            jButton1.addActionListener(e -> {
                webcam.close();
                webcam = choosedCamera;
                webcam.setViewSize(choosedDimension);
                pane.remove(1);
                webcamPanel = camPanelInit(new WebcamPanel(webcam));
                pane.add(webcamPanel,1);
                pack();

            });

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Frame frame = (Frame) e.getSource();
                    frame.dispose();
                    webcam.close();
                }
            });

            jComboBox.addActionListener(e -> {
                choosedCamera = (Webcam) jComboBox.getItemAt(jComboBox.getSelectedIndex());
            });
            jComboBox1.addActionListener(e -> {
               choosedDimension = (Dimension) jComboBox1.getItemAt(jComboBox1.getSelectedIndex());

            });
        }


        private void buttonEvent(final JButton btn) {
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent a) {
                    WebcamUtils.capture(webcam, "ScreenShot", "bmp");
                }
            });

        }




    }
    private WebcamPanel camPanelInit(WebcamPanel panel){
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setFPSDisplayed(true);
        panel.setImageSizeDisplayed(true);
        return panel;
    }

    private  class CamPanel extends WebcamPanel {
        public CamPanel(Webcam webcam) {
            super(webcam);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (this.getImage() == null) {
                this.getPainter().paintPanel(this, (Graphics2D) g);
            } else {

                BufferedImage clone = this.getImage();
                BredliBinarization b = new BredliBinarization(this.getImage());
                Sherlock s = new Sherlock(b.binarization());
                if (drawClas)
                this.getPainter().paintImage(this, s.drawClass(s.drawRectangles(clone)), (Graphics2D) g);
                else this.getPainter().paintImage(this, s.drawRectangles(clone), (Graphics2D) g);
                if (neuralNetwork)
                    this.getPainter().paintImage(this, s.drawClassFromNN(s.drawRectangles(clone),Samples.nn), (Graphics2D) g);
                else this.getPainter().paintImage(this, s.drawRectangles(clone), (Graphics2D) g);
                }
            }
        }
    }



