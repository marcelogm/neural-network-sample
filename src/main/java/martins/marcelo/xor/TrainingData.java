package martins.marcelo.xor;

import martins.marcelo.data.Matrix;

import java.util.HashMap;

public class TrainingData {

    public static HashMap<Matrix, Matrix> getTrainingData() {
        return new HashMap<>() {{
            put(getProblem(0, 1), getTarget(1));
            put(getProblem(1, 0), getTarget(1));
            put(getProblem(0, 0), getTarget(0));
            put(getProblem(1, 1), getTarget(0));
        }};
    }

    public static Matrix getProblem(double x, double y) {
        Matrix matrix = new Matrix(2, 1);
        matrix.setAt(0, 0, x);
        matrix.setAt(1, 0, y);
        return matrix;
    }

    private static Matrix getTarget(double target) {
        Matrix matrix = new Matrix(1, 1);
        matrix.setAt(0, 0, target);
        return matrix;
    }

}
