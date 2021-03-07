package martins.marcelo.sample;

import martins.marcelo.neural.NeuralNetwork;
import martins.marcelo.xor.TrainingData;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.primitives.Shape;

import java.util.function.Function;

public class NeuralNetworkToShape implements Function<NeuralNetwork, Shape> {

    @Override
    public Shape apply(NeuralNetwork neuralNetwork) {
        final int STEPS = 50;
        Mapper mapper = getMapper(neuralNetwork);
        Range range = new Range(0, 1);
        final Shape surface = Builder.buildOrthonormal(mapper, range, STEPS);
        surface.setColorMapper(getColor(surface));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);
        return surface;
    }

    private ColorMapper getColor(Shape surface) {
        return  new ColorMapper(
                new ColorMapRainbow(),
                surface.getBounds().getZmin(),
                surface.getBounds().getZmax(),
                new Color(1, 1, 1, .5f)
        );
    }

    private Mapper getMapper(NeuralNetwork neuralNetwork) {
        return new Mapper() {
            @Override
            public double f(double x, double y) {
                return -neuralNetwork.feedForward(TrainingData.getProblem(x, y)).getAt(0, 0);
            }
        };
    }
}
