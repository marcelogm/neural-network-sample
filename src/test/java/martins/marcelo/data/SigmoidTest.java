package martins.marcelo.data;

import martins.marcelo.function.Sigmoid;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SigmoidTest {

    @Test
    @DisplayName("must calculate the sigmoid of 0.1")
    void sigmoid() {
        // given
        Function<Double, Double> sigmoid = new Sigmoid();

        // when
        Double result = sigmoid.apply(0.1);

        // then
        System.out.println(result);
        assertEquals(0.524979187478939986099, result, 0.000000001);
    }

}
