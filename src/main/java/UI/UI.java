package UI;

//головне меню

import Camera.LoadFromCamera;
import Images.Transform;
import Loaders.FileLoader;
import Loaders.ImageForm;
import Loaders.TextLoader;
import Sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

public  class  UI extends JFrame {
  private final static   TextArea textArea= new TextArea("");


  public   UI() {
      textArea.setBounds(0,0,150,100);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setMinimumSize(new Dimension(400,350));
      setTitle("MyProgram");
      Container pane = this.getContentPane();
      add(textArea);
      pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
      JButton jb1 = createButton("Відкрити текстовий файл");
      JButton jb2 = createButton("Редактор зображень");
      JButton jb3 = createButton("Перетворити в текст");
      JButton jb5 = createButton("Перетворити в зображення");
      JButton jb4 = createButton("Зображення з камери");
      JButton jb6 = createButton("Запис звуку");

      pane.add(jb1);
      pane.add(jb2);
      pane.add(jb3);
      pane.add(jb5);
      pane.add(jb4);
      pane.add(jb6);

      pack();
      setVisible(true);

        textEvent(jb1);
        imageEvent(jb2);
        transformFromImage(jb3);
        transformFromText(jb5);
        cameraEvent(jb4);

        jb6.addActionListener(e -> {
            new Sound();
            textArea.append("Запуск запису звуку\n");
        });

    }
    //функція для створення нових кнопок
private JButton createButton(String text){
      JButton jButton = new JButton(text);
    jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    jButton.setMaximumSize(new Dimension(200,100));

return jButton;
}



    private void textEvent(final JButton btn) {
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                textArea.append("Вибір текстового файлу\n");
                TextLoader textLoader = new TextLoader();
                       textLoader.loadText(FileLoader.load(UI.this));
            }
        });

    }
    private void imageEvent(final JButton btn) {
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                textArea.append("Запуск форми для зображень\n");
                ImageForm imageForm = new ImageForm();
                imageForm.loadImage();

            }
        });

    }

    private void transformFromImage(final JButton btn) {
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                try {
                    Transform.transformImage(FileLoader.load(UI.this));
                    textArea.append("Перетворено\n");
                } catch (IOException e) {
                    textArea.append("Перетворення зображення у txt не вдалось\n");
                    e.printStackTrace();
                }
            }
        });

    }
    private void transformFromText(final JButton btn) {
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                try {
                    Transform.transformTxt(FileLoader.load(UI.this));
                    textArea.append("Перетворено\n");
                } catch (IOException e) {
                    textArea.append("Перетворення текстового файлу у зображення не вдалось\n");
                    e.printStackTrace();
                }
            }
        });

    }

    private void cameraEvent(final JButton btn) {
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                textArea.append("Запуск камери\n");
                LoadFromCamera loadFromCamera = new LoadFromCamera();
               loadFromCamera.start();



            }
        });

    }
}

