package NN1;

import java.awt.image.BufferedImage;

public class NeuralNetwork  {
    private Layer[] layers;
    private  double n = 0.01;


    private double Sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public NeuralNetwork(int inNeurones,int outNeurones) {
        layers = new Layer[2];
        layers[0] = new Layer(inNeurones,outNeurones);
        layers[1] = new Layer(outNeurones,0);
            for (int j = 0; j < inNeurones; j++) {
                for (int k = 0; k < outNeurones; k++) {
                    layers[0].weight[j][k] = 0.00001;
                }
            }
        }

    public double[] feedForward(double[] image) {
//Завантаження зображення та розрахунок нейронів
        System.arraycopy(image, 0, layers[0].neurons, 0, image.length);
//        int l=0;
//            for (int j=0;j<image.length;j++){
//                if (image[j] == 1.0)
//                    System.out.print("1 ");
//                else System.out.print("0 ");
//                l++;
//                if (l>=60) {System.out.println(); l=0;}
//            }
//        System.out.println();

            Layer input = layers[0];
            Layer out = layers[1];
            for (int j = 0; j < out.layerSize; j++) {
                out.neurons[j] = 0;
                for (int k = 0; k < input.layerSize; k++) {
                    out.neurons[j] += input.neurons[k] * input.weight[k][j];
                }
                out.neurons[j] = Sigmoid(out.neurons[j]);

            }
        return layers[1].neurons;
    }
    public void errorCorrection(double[] targets, int number) {
        double[] errors = new double[layers[1].layerSize];
        for (int i = 0; i < layers[1].layerSize; i++) {
            errors[i] = targets[i] - layers[1].neurons[i];
        }
            Layer input = layers[0];
            Layer output = layers[1];
            double delta=0;


            for (int i=0;i<input.layerSize;i++){
                    if (input.neurons[i] == 1.0) {
                        delta = n * output.neurons[number] * errors[number];
                    } else
                        delta = -n * output.neurons[number] * errors[number];
                    input.weight[i][number] = input.weight[i][number] + delta;
            }
            // корекція ваг
            for (int i=0;i<input.layerSize;i++){
                for (int j=0;j<output.layerSize;j++){
                    delta = errors[j] * output.neurons[j] * n;
                    input.weight[i][j] = input.weight[i][j] + delta;

                }
            }

        }


    public static void learning(double[][] samples,int[] digits, NeuralNetwork nn) {
        for (int i = 0; i < 10; i++) {
            int right = 0;
            double errorSum = 0;
            for (int j=0;j<100;j++) {
                int pos = (int)(Math.random()*digits.length);
                int digit = digits[pos];
                double[] outputs = nn.feedForward(samples[pos]);
                double[] targets = new double[10];
                targets[digit] = 1;
                int maxDigit = 0;
                double maxDigitWeight = -1;
                for (int k = 0; k < 10; k++) {
                    if (outputs[k] > maxDigitWeight) {
                        maxDigitWeight = outputs[k];
                        maxDigit = k;
                    }
                }
                if (digit == maxDigit) right++;
                for (int k = 0; k < 10; k++) {
                    errorSum += (targets[k] - outputs[k]) * (targets[k] - outputs[k]);
                }
                nn.errorCorrection(targets,digit);
            }
            System.out.println("epoch: " + i + ". correct: " + right + ". error: " + errorSum);
            }
        }



    public static   int classification(BufferedImage digit, NeuralNetwork nn){
        double[] input = Samples.transformToInput(digit);
     double[] output =  nn.feedForward(input);
        int maxDigit = 0;
        double maxDigitWeight = -1;
        for (int k = 0; k < 10; k++) {
            if (output[k] > maxDigitWeight) {
                maxDigitWeight = output[k];
                maxDigit = k;
            }
        }
        if(maxDigitWeight>0.4) {
            System.out.println("W: " + maxDigitWeight + " D: " + maxDigit);
            return maxDigit;
        }
        else{
            System.out.println("W: "+maxDigitWeight+" D: ?");
            return 10;
        }

    }

}

