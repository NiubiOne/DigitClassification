package Loaders;

import java.io.*;
import javax.swing.*;

public class TextLoader {

  private  BufferedReader bufferedReader;

    public void loadText(String path){
        try {
            if (path!=null) {
                FileReader reader = new FileReader(path);
                bufferedReader = new BufferedReader(reader);
                TextForm textForm = new TextForm();
                textForm.createUI(path);
                bufferedReader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(" An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private class TextForm extends JFrame {
        private  void createUI(String title) throws IOException {
            setTitle(title);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JTextArea jtxt = new JTextArea();
            setSize(615,835);
            setLayout(null);
            setVisible(true);
            add(jtxt);
            jtxt.read(bufferedReader,null);
            JScrollPane scroll = new JScrollPane(jtxt);
            scroll.setBounds(0, 0, 600, 800);

            getContentPane().add(scroll);
            setLocationRelativeTo(null);
        }

    }
}

