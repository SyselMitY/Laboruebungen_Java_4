package observer.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import observer.ObservableTreeSet;
import observer.TreeSetEvent;
import observer.TreeSetObserver;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ObserverGuiController implements Initializable, TreeSetObserver<Integer> {

    public static final String OBSERVER_LOG_FILE = "observer.log";
    @FXML
    private Button buttonDeregisterGui;

    @FXML
    private Button buttonRegisterGui;

    @FXML
    private Button buttonDeregisterLog;

    @FXML
    private Button buttonRegisterLog;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonRemove;

    @FXML
    private Button buttonPollFIrst;

    @FXML
    private Button buttonPollLast;

    @FXML
    private Button buttonRetainAll;

    @FXML
    private Button buttonAddAll;

    @FXML
    private Button buttonRemoveAll;

    @FXML
    private Button buttonClear;

    @FXML
    private ListView<String> listView;

    private ObservableTreeSet<Integer> observableTreeSet;

    private final TreeSetObserver<Integer> fileLogger;

    public ObserverGuiController() {

        fileLogger = event -> {
            try (FileWriter writer = new FileWriter(OBSERVER_LOG_FILE,true)) {
                writer.append(getMessageString(event, observableTreeSet));
                writer.append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableTreeSet = new ObservableTreeSet<>();
        buttonAdd.setOnAction(actionEvent -> observableTreeSet.add(getRandomNumber()));
        buttonAddAll.setOnAction(actionEvent -> observableTreeSet.addAll(getRandomCollection()));
        buttonDeregisterGui.setOnAction(actionEvent -> observableTreeSet.unsubscribe(this));
        buttonRegisterGui.setOnAction(actionEvent -> observableTreeSet.subscribe(this));
        buttonDeregisterLog.setOnAction(actionEvent -> observableTreeSet.unsubscribe(fileLogger));
        buttonRegisterLog.setOnAction(actionEvent -> observableTreeSet.subscribe(fileLogger));
        buttonRemove.setOnAction(actionEvent -> observableTreeSet.remove(getRandomNumber()));
        buttonRemoveAll.setOnAction(actionEvent -> observableTreeSet.removeAll(getRandomCollection()));
        buttonRetainAll.setOnAction(actionEvent -> observableTreeSet.retainAll(getRandomCollection()));
        buttonPollFIrst.setOnAction(actionEvent -> observableTreeSet.pollFirst());
        buttonPollLast.setOnAction(actionEvent -> observableTreeSet.pollLast());
        buttonClear.setOnAction(actionEvent -> observableTreeSet.clear());

    }

    private int getRandomNumber() {
        return ThreadLocalRandom.current().nextInt(1, 100);
    }

    private Set<Integer> getRandomCollection() {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 6); i++) {
            set.add(getRandomNumber());
        }
        return set;
    }

    @Override
    public void notifyObserver(TreeSetEvent<Integer> event) {
        String message = getMessageString(event, observableTreeSet);
        listView.getItems().add(message);
    }

    private String getMessageString(TreeSetEvent<Integer> event, ObservableTreeSet<Integer> observableTreeSet) {
        return String.format("%s: %s\n%s",
                event.getTimestamp().toString(),
                event.getInfo(),
                observableTreeSet.toString());
    }
}
