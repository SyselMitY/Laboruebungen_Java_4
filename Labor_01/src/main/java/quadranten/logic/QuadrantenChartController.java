package quadranten.logic;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import quadranten.Point;
import quadranten.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class QuadrantenChartController implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private ScatterChart<Double, Double> chartPoints;

    @FXML
    private Button buttonFile;

    @FXML
    private ChoiceBox<String> choiceBoxQuadrant;

    private Set<Point> points;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        points = new HashSet<>();
        initializeChoiceOptions();
        buttonFile.setOnAction(this::openFile);
    }

    private void openFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Punktdatei öffnen");
        File file = fileChooser.showOpenDialog(gridPane.getScene().getWindow());
        if (file == null)
            return;

        points.clear();
        try {
            points.addAll(Tools.getPoints(new FileInputStream(file)));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error opening file");
            alert.setHeaderText("The file could not be opened.");
            alert.show();
        }
        updateChartData();
    }

    private void initializeChoiceOptions() {
        choiceBoxQuadrant.getItems().addAll("1. Quadrant", "2. Quadrant", "3. Quadrant", "4. Quadrant");
        choiceBoxQuadrant.getSelectionModel().select(0);
        choiceBoxQuadrant.setOnAction(actionEvent -> updateChartData());
    }

    private void updateChartData() {
        chartPoints.getData().clear();
        XYChart.Series<Double, Double> newSeries = getSeriesFromPoints();
        chartPoints.getData().add(newSeries);
    }

    @SuppressWarnings("RedundantTypeArguments")
    private XYChart.Series<Double, Double> getSeriesFromPoints() {
        return points.stream()
                .filter(point -> point.getQuadrant() == choiceBoxQuadrant.getSelectionModel().getSelectedIndex() + 1)
                .map(point -> new XYChart.Data<>(point.getX(), point.getY()))
                .collect(XYChart.Series<Double, Double>::new,
                        (series, doubleDoubleData) -> series.getData().add(doubleDoubleData),
                        (series, series2) -> series.getData().addAll(series2.getData()));
    }
}
