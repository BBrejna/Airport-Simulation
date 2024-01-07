package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Simulation;

public class SimulationSetWeatherController {
    private Stage stage;

    @FXML
    private TextField textField;

    public void display(Stage stage, Parent root) {
        this.stage = stage;
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Set Weather");
        // Set up the scene and show the popup
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }
    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }

    public int getValue() throws NumberFormatException {
        return Integer.parseInt(textField.getText().trim());
    }
    @FXML
    private void onOkButtonClicked() {
        try {
            int value = getValue(); // Get the value
            // Perform operations with the value here if needed
            Simulation.getInstance().getWeather().setTemperature(value);
            closeWindow();
        } catch (NumberFormatException e) {
            // Show an error message if the input is not a valid integer
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid number.");
            alert.showAndWait();
        }
    }


}
