package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainViewController {
    @FXML
    private Label label;

    public void initialize() {
        if (label != null) {
            label.setText("Hello, JavaFX!");
        } else {
            System.out.println("Label is null. Check FXML loading and controller initialization.");
        }
    }
}
