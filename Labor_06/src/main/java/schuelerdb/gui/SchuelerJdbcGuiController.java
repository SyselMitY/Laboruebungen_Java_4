package schuelerdb.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import schuelerdb.JdbcSchuelerRepository;
import schuelerdb.SchuelerRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SchuelerJdbcGuiController implements Initializable {

    public static final int N_THREADS = 3;
    public static final int QUERY_TIMEOUT = 10;
    @FXML
    private ListView<String> lvResult;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnKlasse;
    @FXML
    private Button btnGeschlecht;
    @FXML
    private Button btnAll;
    @FXML
    private ProgressBar pbProgress;

    private SchuelerRepository repository;
    private ExecutorService threadPool;

    private void initRepository() throws IOException, SQLException {
        InputStream in = this.getClass().getResourceAsStream("/connection.properties");
        Properties props = new Properties();
        props.load(in);
        String url = props.getProperty("jdbc_url");
        String user = props.getProperty("jdbc_user");
        String pwd = props.getProperty("jdbc_pwd");
        Connection con = DriverManager.getConnection(url, user, pwd);
        repository = new JdbcSchuelerRepository(con);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initRepository();
        } catch (IOException | SQLException e) {
            showErrorAlert("Ein Fehler trat bei der Verbindung der Datenbank auf!");
            Platform.exit();
        }
        threadPool = Executors.newFixedThreadPool(N_THREADS);
        resetListView();
        btnKlasse.setOnAction(this::searchKlasse);
        btnGeschlecht.setOnAction(this::searchGeschlecht);
        btnAll.setOnAction(this::getKlassen);
    }

    private void searchKlasse(ActionEvent event) {
        resetListView();
        disableInput(true);
        Callable<List<String>> callable =
                () -> repository
                        .findSchuelerByKlasse(tfSearch.getText())
                        .stream()
                        .map(Objects::toString)
                        .collect(Collectors.toList());
        processListFuture(callable);
    }

    private void searchGeschlecht(ActionEvent event) {
        if (tfSearch.getText().length() != 1)
            showErrorAlert("Geschlecht ist nur 1 Zeichen!");
        else {
            resetListView();
            disableInput(true);
            Callable<List<String>> callable =
                    () -> repository
                            .findSchuelerByGeschlecht(tfSearch.getText().charAt(0))
                            .stream()
                            .map(Objects::toString)
                            .collect(Collectors.toList());
            processListFuture(callable);
        }
    }

    private void getKlassen(ActionEvent event) {
        resetListView();
        disableInput(true);
        Callable<List<String>> callable =
                () -> repository
                        .getKlassen()
                        .entrySet()
                        .stream()
                        .map(entry -> String.format("Klasse %s hat %d Schueler", entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList());
        processListFuture(callable);
    }

    private void processListFuture(Callable<List<String>> callable) {
        Future<List<String>> result = threadPool.submit(callable);
        pbProgress.setProgress(0);
        threadPool.submit(new ProgressAnimator<>(result, pbProgress.progressProperty()));
        threadPool.submit(() -> {
            try {
                List<String> c = result.get(QUERY_TIMEOUT, TimeUnit.SECONDS);
                Platform.runLater(() -> lvResult.getItems().addAll(c));
                disableInput(false);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                showErrorAlert("Ein Fehler trat bei der Abfrage auf");
            }
        });
    }

    private void resetListView() {
        lvResult.getItems().clear();
    }

    private void disableInput(boolean b) {
        btnAll.setDisable(b);
        btnGeschlecht.setDisable(b);
        btnKlasse.setDisable(b);
        tfSearch.setDisable(b);
    }

    private void showErrorAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setContentText(s);
        alert.showAndWait();
    }

    public void stop() throws IOException {
        repository.close();
        threadPool.shutdownNow();
    }
}
