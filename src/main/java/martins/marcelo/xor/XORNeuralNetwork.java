package martins.marcelo.xor;

import martins.marcelo.data.Matrix;
import martins.marcelo.neural.NeuralNetwork;

import java.util.Map;

public class XORNeuralNetwork extends NeuralNetwork {

    public XORNeuralNetwork() {
        super(2, 3, 1);
    }

    public void train(int epoch) {
        Map<Matrix, Matrix> matrix = TrainingData.getTrainingData();
        for (int i = 0; i < epoch; i++) {
            matrix.forEach(super::train);
        }
    }
}
