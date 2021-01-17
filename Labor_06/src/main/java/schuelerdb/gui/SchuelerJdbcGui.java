package schuelerdb.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import schuelerdb.Schueler;

import java.io.IOException;

public class SchuelerJdbcGui extends Application {

    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws IOException {
        loader = new FXMLLoader(this.getClass().getResource("/layout/layout.fxml"));
        loader.load();
        Scene scene = new Scene(loader.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Schueler JDBC Gui");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        ((SchuelerJdbcGuiController) loader.getController()).stop();
        super.stop();
    }
}
