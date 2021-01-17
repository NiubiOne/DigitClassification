
import NN1.NeuralNetwork;
import NN1.Samples;
import UI.UI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class Main {

    public static void main(String[] args) {
        new UI();
        Samples s =  new Samples();
        Samples.nn = new NeuralNetwork(2400,10);
        NeuralNetwork.learning(s.images, s.digits,Samples.nn);
    }
}
