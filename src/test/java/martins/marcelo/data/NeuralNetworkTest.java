package martins.marcelo.data;

import martins.marcelo.neural.NeuralNetwork;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static javax.swing.UIManager.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NeuralNetworkTest {

    @Test
    @DisplayName("must start with the bias 1.0 for each hidden node")
    void biasH() {
        // when
        NeuralNetwork nn = getNeuralNetwork();

        // then
        Matrix biasH = nn.getBiasH();
        assertEquals(1, biasH.getCols());
        assertEquals(nn.getHiddenNodes(), biasH.getRows());

        // and
        biasH.map(it -> {
            assertEquals(1.0, it);
            return it;
        });
    }

    @Test
    @DisplayName("must starts with the bias 1.0 for each output node")
    void biasO() {
        // when
        NeuralNetwork nn = getNeuralNetwork();

        // then
        Matrix biasO = nn.getBiasO();
        assertEquals(1, biasO.getCols());
        assertEquals(nn.getOutputNodes(), biasO.getRows());

        // and
        biasO.map(it -> {
            assertEquals(1.0, it);
            return it;
        });
    }

    @Test
    @DisplayName("must randomize the 'input to hidden' weights")
    void randomizeIH() {
        // when
        NeuralNetwork nn = getNeuralNetwork();

        // then
        nn.getWeightsIH().map(it -> {
            assertTrue(-1.0 <= it && it <= 1.0);
            return it;
        });
    }

    @Test
    @DisplayName("must randomize the 'input to hidden' weights")
    void randomizeHO() {
        // when
        NeuralNetwork nn = getNeuralNetwork();

        // then
        nn.getWeightsHO().map(it -> {
            assertTrue(-1.0 <= it && it <= 1.0);
            return it;
        });
    }

    @Test
    @DisplayName("must calculate the output")
    void feedforward() {
        // given
        NeuralNetwork nn = getNeuralNetwork();
        nn.setWeightsIH(getWeightsFromInputToHidden());
        nn.setWeightsHO(getWeightsFromHiddenToOutput());

        // and
        Matrix input = getInput();

        // when
        Matrix output = nn.feedForward(input);

        // then
        assertEquals(0.8681792940402613, output.getAt(0, 0), 0.000000001);
        assertEquals(0.8964204696844218, output.getAt(1, 0), 0.000000001);
    }

    @Test
    @DisplayName("should train and calculate guesses for an XOR problem")
    void xor() {
        // given
        NeuralNetwork nn = new NeuralNetwork(2, 2, 1);
        HashMap<Matrix, Matrix> trainingData = getTrainingData();

        // when
        for (int i = 0; i < 100000; i++) {
            trainingData.forEach(nn::train);
        }

        // then
        assertEquals(1.0, guess(nn, 0, 1), 0.1);
        assertEquals(1.0, guess(nn, 1, 0), 0.1);
        assertEquals(0.0, guess(nn, 0, 0), 0.1);
        assertEquals(0.0, guess(nn, 1, 1), 0.1);
    }

    private Double guess(NeuralNetwork nn, Integer x, Integer y) {
        return nn.feedForward(getProblem(x, y)).getAt(0,0);
    }

    private HashMap<Matrix, Matrix> getTrainingData() {
        return new HashMap<>() {{
            put(getProblem(0, 1), getTarget(1));
            put(getProblem(1, 0), getTarget(1));
            put(getProblem(0, 0), getTarget(0));
            put(getProblem(1, 1), getTarget(0));
        }};
    }

    private Matrix getProblem(double x, double y) {
        Matrix matrix = new Matrix(2, 1);
        matrix.setAt(0, 0, x);
        matrix.setAt(1, 0, y);
        return matrix;
    }

    private Matrix getTarget(double target) {
        Matrix matrix = new Matrix(1, 1);
        matrix.setAt(0, 0, target);
        return matrix;
    }

    private Matrix getInput() {
        Matrix matrix = new Matrix(2, 1);
        matrix.setAt(0, 0, 0);
        matrix.setAt(1, 0, 1);
        return matrix;
    }

    private Matrix getWeightsFromInputToHidden() {
        Matrix matrix = new Matrix(3, 2);
        matrix.setAt(0, 0, 0.2);
        matrix.setAt(0, 1, 0.8);
        matrix.setAt(1, 0, 0.1);
        matrix.setAt(0, 1, 0.9);
        matrix.setAt(2, 0, 0.4);
        matrix.setAt(0, 1, 0.2);
        return matrix;
    }

    private Matrix getWeightsFromHiddenToOutput() {
        Matrix matrix = new Matrix(2, 3);
        matrix.setAt(0, 0, 0.01);
        matrix.setAt(0, 1, 0.4);
        matrix.setAt(0, 2, 0.8);
        matrix.setAt(1, 0, 0.08);
        matrix.setAt(1, 1, 0.8);
        matrix.setAt(1, 2, 0.7);
        return matrix;
    }

    private NeuralNetwork getNeuralNetwork() {
        return new NeuralNetwork(2, 3, 2);
    }
}
