package calculator.logic;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class SimpleCalculatorController implements Initializable {

    @FXML
    private Slider sliderInput;

    @FXML
    private ChoiceBox<Integer> choiceInput;

    @FXML
    private RadioButton radioAddition;

    @FXML
    private ToggleGroup groupOperation;

    @FXML
    private RadioButton radioSubtraction;

    @FXML
    private RadioButton radioMultiplication;

    @FXML
    private RadioButton radioDivision;

    @FXML
    private TextField tfOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initChoiceBox();
        StringBinding resultBinding = Bindings.createStringBinding(
                this::getResult,
                sliderInput.valueProperty(),
                choiceInput.getSelectionModel().selectedItemProperty(),
                groupOperation.selectedToggleProperty());
        tfOutput.textProperty().bind(resultBinding);
    }

    private String getResult() {
        Integer firstNumber = (int) sliderInput.getValue();
        Integer secondNumber = choiceInput.getSelectionModel().getSelectedItem();

        if (groupOperation.getSelectedToggle().equals(radioAddition)) {
            return String.format("%d + %d = %d", firstNumber, secondNumber, firstNumber + secondNumber);
        }
        else if (groupOperation.getSelectedToggle().equals(radioSubtraction)) {
            return String.format("%d - %d = %d", firstNumber, secondNumber, firstNumber - secondNumber);
        }
        else if (groupOperation.getSelectedToggle().equals(radioMultiplication)) {
            return String.format("%d * %d = %d", firstNumber, secondNumber, firstNumber * secondNumber);
        }
        else {
            if (secondNumber == 0) {
                return ("Fehler - Division durch 0 nicht möglich");
            }
            return String.format("%d / %d = %f", firstNumber, secondNumber, (double) firstNumber / secondNumber);
        }
    }

    private void initChoiceBox() {
        IntStream.rangeClosed(0, 10).forEach(entry -> choiceInput.getItems().add(entry));
        choiceInput.getSelectionModel().select(0);
    }
}
