package martins.marcelo.data;

import java.util.function.Function;

public class MatrixMath {

    public static Matrix hadamardProduct(Matrix a, Matrix b) {
        if (a.getCols() != b.getCols() || b.getRows() != b.getRows()) {
            throw new RuntimeException();
        }
        Matrix newest = (Matrix) a.clone();
        newest.map((i, j) -> a.getAt(i, j) * b.getAt(i, j));
        return newest;
    }

    public static Matrix scalarProduct(Matrix a, Double scale) {
        Matrix newest = (Matrix) a.clone();
        newest.map(it -> it * scale);
        return newest;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getCols() != b.getRows()) {
            throw new RuntimeException();
        }
        Matrix c = new Matrix(a.getRows(), b.getCols());
        for (int i = 0; i < c.getRows(); i++) {
            for (int j = 0; j < c.getCols(); j++) {
                double sum = 0.0;
                for (int k = 0; k < a.getCols(); k++) {
                    sum += a.getAt(i, k) * b.getAt(k, j);
                }
                c.setAt(i, j, sum);
            }
        }
        return c;
    }

    public static Matrix add(Matrix a, Matrix b) {
        Matrix newest = (Matrix) a.clone();
        newest.map((i, j) -> a.getAt(i, j) + b.getAt(i, j));
        return newest;
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        Matrix newest = new Matrix(a.getRows(), a.getCols());
        newest.map((i, j) -> a.getAt(i, j) - b.getAt(i, j));
        return newest;
    }

    public static Matrix transpose(Matrix a) {
        Matrix b = new Matrix(a.getCols(), a.getRows());
        for (int i = 0; i < b.getRows(); i++) {
            for (int j = 0; j < b.getCols(); j++) {
                b.setAt(i, j, a.getAt(j, i));
            }
        }
        return b;
    }

    public static Matrix apply(Matrix a, Function<Double, Double> function) {
        Matrix b = (Matrix) a.clone();
        b.map(function);
        return b;
    }
}
