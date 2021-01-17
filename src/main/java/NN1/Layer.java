package NN1;

public class Layer {
    int layerSize;
    double[][] weight;
    double[] neurons;

    public Layer(int layerSize,int nextLayerSize) {
        this.layerSize = layerSize;
        this.weight = new double[layerSize][nextLayerSize] ;
        this.neurons = new double[layerSize] ;
    }
}
