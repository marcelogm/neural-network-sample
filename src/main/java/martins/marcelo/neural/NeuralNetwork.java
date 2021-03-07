package martins.marcelo.neural;

import lombok.Getter;
import lombok.Setter;
import martins.marcelo.data.Matrix;
import martins.marcelo.data.MatrixMath;
import martins.marcelo.function.DerivativeSigmoid;
import martins.marcelo.function.Sigmoid;

import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Optional.of;
import static martins.marcelo.data.MatrixMath.*;

@Getter
@Setter
public class NeuralNetwork {

    private final int inputNodes;
    private final int hiddenNodes;
    private final int outputNodes;

    private Matrix weightsIH;
    private Matrix weightsHO;

    private Matrix biasH;
    private Matrix biasO;

    private final double learningRate;
    private final Function<Double, Double> activationFunction = new Sigmoid();
    private final Function<Double, Double> derivativeFunction = new DerivativeSigmoid();

    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes) {
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        this.weightsIH = Matrix.randomize(this.hiddenNodes, this.inputNodes);
        this.weightsHO = Matrix.randomize(this.outputNodes, this.hiddenNodes);

        this.biasH = new Matrix(this.hiddenNodes, 1);
        this.biasH.map(it -> 1.0);
        this.biasO = new Matrix(this.outputNodes, 1);
        this.biasO.map(it -> 1.0);

        this.learningRate = 0.05;
    }

    public Matrix feedForward(Matrix input) {
        return of(input)
                .map(this::getHiddenOutput)
                .map(this::getOutput)
                .get();
    }

    public void train(Matrix input, Matrix target) {
        Matrix hidden = getHiddenOutput(input);
        Matrix output = getOutput(hidden);

        Matrix outputError = getError(target, output);
        adjust(hidden, output, outputError, this.weightsHO, this.biasO,  this::setWeightsHO, this::setBiasO);

        Matrix hiddenError = getHiddenError(this.weightsHO, outputError);
        adjust(input, hidden, hiddenError, this.weightsIH, this.biasH,  this::setWeightsIH, this::setBiasH);
    }

    private void adjust(Matrix input, Matrix output, Matrix error,
                        Matrix weightToAdjust, Matrix biasToAdjust,
                        Consumer<Matrix> weightConsumer, Consumer<Matrix> biasConsumer) {
        Matrix gradient = getGradient(output, error);
        Matrix delta = getDelta(gradient, input);
        weightConsumer.accept(add(weightToAdjust, delta));
        biasConsumer.accept(add(biasToAdjust, gradient));
    }

    private Matrix getHiddenOutput(Matrix input) {
        return getOutputByLayer(input, this.weightsIH, this.biasH);
    }

    private Matrix getOutput(Matrix hidden) {
        return getOutputByLayer(hidden, this.weightsHO, this.biasO);
}

    private Matrix getOutputByLayer(Matrix input,
                               Matrix weights,
                               Matrix bias) {
        return of(input)
                .map(it -> multiply(weights, it))
                .map(output -> add(output, bias))
                .map(output -> apply(output, this.activationFunction))
                .get();
    }

    private Matrix getError(Matrix target, Matrix output) {
        return subtract(target, output);
    }

    private Matrix getHiddenError(Matrix hiddenWeights, Matrix outputError) {
        Matrix transposedWeight = MatrixMath.transpose(hiddenWeights);
        return multiply(transposedWeight, outputError);
    }

    private Matrix getGradient(Matrix output, Matrix error) {
        return of(output)
                .map(it -> apply(it, this.derivativeFunction))
                .map(it -> hadamardProduct(it, error))
                .map(it -> scalarProduct(it, this.learningRate))
                .get();
    }

    private Matrix getDelta(Matrix gradient, Matrix input) {
        return of(input)
                .map(MatrixMath::transpose)
                .map(transposed -> multiply(gradient, transposed))
                .get();
    }
}
