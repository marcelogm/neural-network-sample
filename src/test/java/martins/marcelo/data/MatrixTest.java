package martins.marcelo.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatrixTest {

    @Test
    @DisplayName("must create a matrix")
    void create() {
        // given
        Matrix matrix = new Matrix(2, 3);

        // when
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                matrix.setAt(i, j, i + j);
            }
        }

        // then
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                assertEquals(i + j, matrix.getAt(i, j));
            }
        }
    }

    @Test
    @DisplayName("must map a function for each element")
    void map() {
        // given
        Matrix matrix = new Matrix(2, 3);

        // when
        matrix.map(it -> 1.0);

        // then
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                assertEquals(1.0, matrix.getAt(i, j));
            }
        }
    }

    @Test
    @DisplayName("must map a function given the element index")
    void mapIndex() {
        // given
        Matrix matrix = new Matrix(2, 3);

        // when
        matrix.map((i, j) -> (double) (i * j));

        // then
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                assertEquals(i * j, matrix.getAt(i, j));
            }
        }
    }


    @Test
    @DisplayName("must have a tabulated toString")
    void tabulated() {
        // given
        final String EXPECTED = "0.0 1.0 2.0 \n" +
                                "3.0 4.0 5.0 \n";

        // and
        Matrix matrix = new Matrix(2, 3);
        AtomicReference<Double> i = new AtomicReference<>(0.0);

        // when
        matrix.map(it -> i.getAndSet(i.get() + 1));

        // then
        assertEquals(EXPECTED, matrix.toString());
    }

    @Test
    @DisplayName("must obtain a randomized matrix")
    public void randomize() {
        // when
        Matrix a = Matrix.randomize(3, 3);

        // then
        a.map(it -> {
            assertTrue(-1.0 <= it && it <= 1.0);
            return it;
        });
    }
}
