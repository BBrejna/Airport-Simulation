package controller.popups;

import data.admin.AirlinesSet;
import data.admin.AirportSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.classes.admin.Airline;
import model.classes.admin.Airport;

import java.util.ArrayList;

public class CloseConfirmPopupController {
    private Stage stage;
    private boolean answer;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;


    public boolean displayPopup(Stage stage, Parent root) {
        this.stage = stage;
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Close the program");

        stage.setOnCloseRequest(e -> {
            e.consume();
            onCancelButtonClicked();
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/resources/icon.png"));
        stage.showAndWait();
        return answer;
    }

    @FXML
    private void onCancelButtonClicked() {
        answer=false;
        closeWindow();
    }
    @FXML
    public void onConfirmButtonClicked() {
        answer=true;
        closeWindow();
    }

    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }


    @FXML
    private void initialize(){

    }


}
