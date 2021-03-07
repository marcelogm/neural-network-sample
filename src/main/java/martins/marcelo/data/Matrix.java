package martins.marcelo.data;

import lombok.Data;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

@Data
public class Matrix implements Cloneable {

    private final int rows;
    private final int cols;
    private double[][] data;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    public void setAt(int row, int col, double data) {
        this.data[row][col] = data;
    }

    public double getAt(int row, int col) {
        return data[row][col];
    }

    public void map(Function<Double, Double> callback) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                this.data[i][j] = callback.apply(this.data[i][j]);
            }
        }
    }

    public void map(BiFunction<Integer, Integer, Double> callback) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                this.data[i][j] = callback.apply(i, j);
            }
        }
    }

    public static Matrix randomize(int rows, int cols) {
        Matrix matrix = new Matrix(rows, cols);
        matrix.map(it -> new Random().nextDouble() * 2 - 1);
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (double[] row : data) {
            for (double item : row) {
                builder.append(item);
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public Object clone() {
        Matrix newest = new Matrix(this.getRows(), this.getCols());
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getCols(); j++) {
                newest.setAt(i, j, this.data[i][j]);
            }
        }
        return newest;
    }

}
