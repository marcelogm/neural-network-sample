package martins.marcelo.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatrixMathTest {

    @Test
    @DisplayName("must obtain the Hadamard product from two matrices")
    public void hadamardProduct() {
        // given
        Matrix a = get(1, 2, 3, 4);
        Matrix b = get(2, 3, 4, 5);

        // when
        Matrix c = MatrixMath.hadamardProduct(a, b);

        // then
        assertEquals(2, c.getAt(0, 0));
        assertEquals(6, c.getAt(0, 1));
        assertEquals(12, c.getAt(1, 0));
        assertEquals(20, c.getAt(1, 1));
    }

    @Test
    @DisplayName("must obtain the dot product from a matrix")
    public void scalarProduct() {
        // given
        Matrix a = get(1, 2, 3, 4);

        // when
        Matrix b = MatrixMath.scalarProduct(a, 2.0);

        // then
        assertEquals(2, b.getAt(0, 0));
        assertEquals(4, b.getAt(0, 1));
        assertEquals(6, b.getAt(1, 0));
        assertEquals(8, b.getAt(1, 1));
    }


    @Test
    @DisplayName("must obtain a transposed matrix")
    public void transpose() {
        // given
        Matrix a = get(1, 2, 3, 4);
        Matrix b = get(1, 3, 2, 4);

        // when
        Matrix c = MatrixMath.transpose(a);

        // then
        assertEquals(b, c);
    }

    @Test
    @DisplayName("must obtain a transposed matrix from a matrix with different rows and columns")
    public void transposeWithDifferentSize() {
        // given
        Matrix a = new Matrix(2, 3);
        Matrix c = new Matrix(3, 2);

        // when
        Matrix b = MatrixMath.transpose(a);

        // then
        assertEquals(b, c);
    }

    @Test
    @DisplayName("must sum two matrices")
    public void add() {
        // given
        Matrix a = get(1, 2, 3, 4);
        Matrix b = get(2, 3, 4, 5);
        Matrix EXPECTED = get(3, 5, 7, 9);

        // when
        Matrix c = MatrixMath.add(a, b);

        // then
        assertEquals(EXPECTED, c);
    }

    @Test
    @DisplayName("must subtract two matrices")
    public void subtract() {
        // given
        Matrix a = get(2, 3, 4, 5);
        Matrix b = get(1, 2, 3, 4);
        Matrix EXPECTED = get(1, 1, 1, 1);

        // when
        Matrix c = MatrixMath.subtract(a, b);

        // then
        assertEquals(EXPECTED, c);
    }

    @Test
    @DisplayName("must multiply two matrices")
    public void multiply() {
        // given
        Matrix a = get(1, 2, 3, 4, 5, 6);
        Matrix b = get(7, 8, 9, 10);
        Matrix EXPECTED = get(25, 28, 57, 64, 89, 100);

        // when
        Matrix c = MatrixMath.multiply(a, b);

        // then
        assertEquals(EXPECTED, c);
    }

    public Matrix get(double i, double j, double k, double y) {
        Matrix a = new Matrix(2, 2);
        a.setAt(0, 0, i);
        a.setAt(0, 1, j);
        a.setAt(1, 0, k);
        a.setAt(1, 1, y);
        return a;
    }

    public Matrix get(double i, double j, double k, double y, double z, double w) {
        Matrix a = new Matrix(3, 2);
        a.setAt(0, 0, i);
        a.setAt(0, 1, j);
        a.setAt(1, 0, k);
        a.setAt(1, 1, y);
        a.setAt(2, 0, z);
        a.setAt(2, 1, w);
        return a;
    }
}
