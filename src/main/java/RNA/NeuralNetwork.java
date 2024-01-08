/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RNA;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

/**
 *
 * @author kelvyn valle
 */
public class NeuralNetwork {

    public double[][][] synapsis;
    public double error = 0;
    public double major = 0;

    public NeuralNetwork(int[] neurons) {
        synapsis = new double[neurons.length][][];
        synapsis[0] = new double[neurons[0]][];
        for (int i = 0; i < neurons[0]; i++) {
            synapsis[0][i] = new double[]{1};
        }
        for (int i = 1; i < neurons.length; i++) {
            synapsis[i] = new double[neurons[i]][];
            for (int j = 0; j < neurons[i]; j++) {
                synapsis[i][j] = new double[neurons[i - 1] + 1];
                for (int k = 0; k < neurons[i - 1] + 1; k++) {
                    synapsis[i][j][k] = ThreadLocalRandom.current().nextInt(-1000000, 1000000 + 1) / 1000000.0;
                }
            }
        }
    }

    //get the layer neuron count
    public int[] getLayers() {
        int[] layers = new int[this.synapsis.length];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = this.synapsis[i].length;
        }
        return layers;
    }

    //mix two neural networks
    public static NeuralNetwork mix(NeuralNetwork n1, NeuralNetwork n2) {
        Random rand = new Random();
        NeuralNetwork n3 = new NeuralNetwork(n1.getLayers());
        for (int i = 0; i < n1.synapsis.length; i++) {
            for (int j = 0; j < n1.synapsis[i].length; j++) {
                for (int k = 0; k < n1.synapsis[i][j].length; k++) {
                    n3.synapsis[i][j][k] = (rand.nextInt(1000) < 500 ? n1.synapsis[i][j][k] : n2.synapsis[i][j][k]) + (ThreadLocalRandom.current().nextInt(-1000, 1000) < 990 ? 0 : ThreadLocalRandom.current().nextInt(-1000000, 1000000 + 1) / 10000000.0);
                }
            }
        }
        return n3;
    }

    //get value from a neural network
    public double[] getValue(double[] inputs) {
        double[] outputs = new double[]{};
        for (int i = 1; i < synapsis.length; i++) {
            outputs = new double[synapsis[i].length];
            for (int j = 0; j < synapsis[i].length; j++) {
                for (int k = 0; k < synapsis[i][j].length - 1; k++) {
                    outputs[j] += synapsis[i][j][k] * inputs[k];
                }
                outputs[j] += 1 * synapsis[i][j][synapsis[i][j].length - 1];
                outputs[j] = Math.min(Math.max(0, outputs[j]), 1);
            }
            inputs = outputs.clone();
        }
        return outputs;
    }
}
