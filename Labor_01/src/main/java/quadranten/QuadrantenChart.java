package quadranten;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class QuadrantenChart extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(QuadrantenChart.class.getResource("/layout/quadrant.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Quadrantenchart");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
