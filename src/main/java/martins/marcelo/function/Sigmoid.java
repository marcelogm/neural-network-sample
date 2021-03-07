package martins.marcelo.function;

import java.util.function.Function;

public class Sigmoid implements Function<Double, Double> {

    @Override
    public Double apply(Double x) {
        return (1 / (1 + Math.exp(-x)));
    }
}
