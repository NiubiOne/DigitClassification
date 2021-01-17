package Loaders;
//клас який відкриває форму для вибору файлів і повертає шлях до вибраного файлу
import javax.swing.*;
import java.io.File;


public class FileLoader {
    public static String load(JFrame jf){
        String path=null;
    JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
    int result = fileChooser.showOpenDialog(jf);
        if (result == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
        } else{
            System.out.println("Canceled");
        }
        return path;
    }

}
