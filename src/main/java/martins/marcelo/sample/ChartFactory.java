package martins.marcelo.sample;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.javafx.JavaFXChartFactory;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class ChartFactory {

    public static AWTChart build(Stage stage, Shape surface) {
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane);

        stage.setScene(scene);
        stage.show();
        stage.setWidth(800);
        stage.setHeight(800);

        JavaFXChartFactory factory = new JavaFXChartFactory();
        AWTChart chart = getChart(factory);
        chart.getScene().getGraph().add(surface);
        ImageView imageView = factory.bindImageView(chart);

        pane.getChildren().add(imageView);
        factory.addSceneSizeChangedListener(chart, scene);
        return chart;
    }

    private static AWTChart getChart(JavaFXChartFactory factory) {
        return (AWTChart) factory.newChart(Quality.Advanced, "offscreen");
    }

}
