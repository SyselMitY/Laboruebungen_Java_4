package lagersimulation.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lagersimulation.Konsument;
import lagersimulation.Lager;
import lagersimulation.Lieferant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LagerController implements Initializable {

    @FXML
    private ListView<String> listViewLog;
    @FXML
    private Label labelAmount;
    @FXML
    private Button buttonLieferant;
    @FXML
    private Button buttonKonsument;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label labelActiveKonsumenten;
    @FXML
    private Label labelActiveLieferanten;

    private Lager lager;
    private Logger logger;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLogger();
        lager = new Lager(20, logger);
        lager.occupiedSpaceProperty().addListener(observable -> Platform.runLater(this::amountChangeHandler));
        lager.addLieferantKonsumentListener(() -> Platform.runLater(this::updateLieferantKonsumentCounters));
        listViewLog.setCellFactory(TextWrapListCell::new);
        buttonKonsument.setOnAction(actionEvent -> addKonsument());
        buttonLieferant.setOnAction(actionEvent -> addLieferant());
        updateLieferantKonsumentCounters();
    }

    private void initLogger() {
        try {
            logger = Logger.getLogger(LagerGUI.class.getName());
            FileHandler handler = new FileHandler("lager.log");
            handler.setEncoding("UTF-8");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);

            ConsumerLogHandler listHandler = new ConsumerLogHandler(
                    logEntry -> Platform.runLater(() -> listViewLog.getItems().add(logEntry)));
            handler.setEncoding("UTF-8");
            logger.addHandler(listHandler);
        } catch (IOException e) {
            showIOAlert();
        }
    }

    private void showIOAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Schwerer Fehler");
        alert.setHeaderText("Es ist ein Fehler aufgetreten");
        alert.setContentText("Beim lesen/schreiben von Dateien trat ein Fehler auf, der nicht behoben werden kann. Das Programm beendet sich jetzt.");
        alert.showAndWait();
        Platform.exit();
    }

    private void amountChangeHandler() {
        int occupiedSpace = lager.getOccupiedSpace();
        int capacity = lager.getCapacity();
        progressBar.setProgress(((double) occupiedSpace) / capacity);

        labelAmount.setText(String.format("Lagerstand: %dm³ von %d³ verbraucht", occupiedSpace, capacity));
    }


    private void addKonsument() {
        Thread konsument = new Thread(new Konsument(
                7,
                7,
                lager,
                logger,
                3000,
                6000,
                () -> Platform.runLater(() -> logger.log(Level.INFO,"Konsument fertig"))));
        konsument.start();
    }

    private void addLieferant() {
        Thread lieferant = new Thread(new Lieferant(
                5,
                5,
                10,
                lager,
                logger,
                1000,
                5000));
        lieferant.start();
    }

    public void updateLieferantKonsumentCounters() {
        labelActiveKonsumenten.setText("Aktive Konsumenten: "+lager.getKonsumentenCount());
        labelActiveLieferanten.setText("Aktive Lieferanten: "+lager.getLieferantenCount());
    }

    private static class TextWrapListCell extends ListCell<String> {
        private final ListView<String> listview;

        public TextWrapListCell(ListView<String> listview) {
            this.listview = listview;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setMinWidth(listview.getWidth());
                setMaxWidth(listview.getWidth());
                setPrefWidth(listview.getWidth());
                setWrapText(true);
                setText(item);
            }
        }
    }
}
