package martins.marcelo.function;

import java.util.function.Function;

public class DerivativeSigmoid implements Function<Double, Double> {

    @Override
    public Double apply(Double sigmoidOfX) {
        return sigmoidOfX * (1 - sigmoidOfX);
    }
}
