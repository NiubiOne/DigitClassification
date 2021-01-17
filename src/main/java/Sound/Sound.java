package Sound;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import javax.sound.sampled.*;

public class Sound
        extends JFrame{

    boolean stopCapture = false;
    ByteArrayOutputStream
            byteArrayOutputStream;
    AudioFormat audioFormat;
    TargetDataLine targetDataLine;
    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;

    public Sound(){
        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
        final JButton captureBtn =
                new JButton("Запис");
        final JButton stopBtn =
                new JButton("Зупинити");
        final JButton playBtn =
                new JButton("Відтворити");


        final JButton saveBtn =
                new JButton("Зберегти");

        captureBtn.setAlignmentX(CENTER_ALIGNMENT);
        stopBtn.setAlignmentX(CENTER_ALIGNMENT);
        playBtn.setAlignmentX(CENTER_ALIGNMENT);
        saveBtn.setAlignmentX(CENTER_ALIGNMENT);

        captureBtn.setMaximumSize(new Dimension(150, 40));
        stopBtn.setMaximumSize(new Dimension(150, 40));
        playBtn.setMaximumSize(new Dimension(150, 40));
        saveBtn.setMaximumSize(new Dimension(150, 40));

        captureBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        playBtn.setEnabled(false);

        container.add(stopBtn);
        container.add(captureBtn);
        container.add(playBtn);
        container.add(saveBtn);




        captureBtn.addActionListener(
                e -> {
            captureBtn.setEnabled(false);
            stopBtn.setEnabled(true);
            playBtn.setEnabled(false);
            captureAudio();
        }
        );


        stopBtn.addActionListener(
                e -> {
            captureBtn.setEnabled(true);
            stopBtn.setEnabled(false);
            playBtn.setEnabled(true);

            stopCapture = true;
        }
        );


        playBtn.addActionListener(
                e -> playAudio()
        );
        saveBtn.addActionListener(e -> {
            byte[] array = byteArrayOutputStream.toByteArray();

            try {
                FileWriter fileW = new FileWriter("sound.txt");
                for (int i=0;i<array.length;i++){
                    fileW.write(String.format("%08d",new BigInteger(Integer.toBinaryString(array[i])))+"\n");
                }
                fileW.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });


        setDefaultCloseOperation(
                DISPOSE_ON_CLOSE);
        setSize(300,200);
        setVisible(true);
    }

    private void captureAudio(){
        try{


            audioFormat = getAudioFormat();
            DataLine.Info dataLineInfo =
                    new DataLine.Info(
                            TargetDataLine.class,
                            audioFormat);
            targetDataLine = (TargetDataLine)
                    AudioSystem.getLine(
                            dataLineInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            Thread captureThread =
                    new Thread(
                            new CaptureThread());
            captureThread.start();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private void playAudio() {
        try{
            byte[] audioData =
                    byteArrayOutputStream.
                            toByteArray();


            InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
            AudioFormat audioFormat = getAudioFormat();
            audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat, audioData.length/audioFormat.getFrameSize());
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
            sourceDataLine = (SourceDataLine)
                    AudioSystem.getLine(
                            dataLineInfo);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

            Thread playThread =
                    new Thread(new PlayThread());
            playThread.start();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }



    private AudioFormat getAudioFormat(){
        float sampleRate = 8000.0F;

        int sampleSizeInBits = 16;

        int channels = 1;

        boolean signed = true;

        boolean bigEndian = false;

        return new AudioFormat(
                sampleRate,
                sampleSizeInBits,
                channels,
                signed,
                bigEndian);
    }

    class CaptureThread extends Thread{

        byte tempBuffer[] = new byte[10000];
        public void run(){
            byteArrayOutputStream =
                    new ByteArrayOutputStream();
            stopCapture = false;
            try{


                while(!stopCapture){


                    int cnt = targetDataLine.read(
                            tempBuffer,
                            0,
                            tempBuffer.length);
                    if(cnt > 0){

                        byteArrayOutputStream.write(
                                tempBuffer, 0, cnt);
                    }
                }
                byteArrayOutputStream.close();
            }catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }
        }
    }

    class PlayThread extends Thread{
        byte tempBuffer[] = new byte[10000];

        public void run(){
            try{
                int cnt;


                while((cnt = audioInputStream.
                        read(tempBuffer, 0,
                                tempBuffer.length)) != -1){
                    if(cnt > 0){

                        sourceDataLine.write(
                                tempBuffer, 0, cnt);
                    }
                }

                sourceDataLine.drain();
                sourceDataLine.close();
            }catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }
        }
    }

}