package martins.marcelo.sample;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import martins.marcelo.neural.NeuralNetwork;
import martins.marcelo.xor.XORNeuralNetwork;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.plot3d.primitives.Shape;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


public class Sample extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        final int EACH_EPOCH = 50;
        XORNeuralNetwork nn = new XORNeuralNetwork();
        NeuralNetworkToShape drawer = new NeuralNetworkToShape();
        AtomicReference<Shape> surface = new AtomicReference<>(drawer.apply(nn));

        AtomicLong epoch = new AtomicLong();
        AWTChart chart = ChartFactory.build(stage, surface.get());

        Timeline timer = new Timeline(
                new KeyFrame(Duration.millis(150),
                    event -> {
                        nn.train(EACH_EPOCH);
                        updateEpoch(stage, epoch, EACH_EPOCH);
                        updateChart(chart, surface, drawer, nn);
                    }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void updateEpoch(Stage stage, AtomicLong epoch, int toAdd) {
        epoch.addAndGet(toAdd);
        stage.setTitle("XOR PROBLEM - Epoch " + epoch);
    }

    private void updateChart(AWTChart chart,
                             AtomicReference<Shape> surface,
                             NeuralNetworkToShape drawer,
                             NeuralNetwork nn) {
        removeSurface(chart, surface);
        surface.set(drawer.apply(nn));
        addSurface(chart, surface);
    }

    private void removeSurface(AWTChart chart, AtomicReference<Shape> toRemove) {
        chart.getScene().getGraph().remove(toRemove.get());
    }

    private void addSurface(AWTChart chart, AtomicReference<Shape> toRemove) {
        chart.getScene().getGraph().add(toRemove.get());
    }
}