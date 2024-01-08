package controller.popups;

import data.admin.AirlinesSet;
import data.admin.AirportSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.classes.admin.Airline;
import model.classes.admin.Airport;

import java.util.ArrayList;

public class AdminFlightPopupController {
    @FXML
    private RadioButton arrivalRadioButton;
    @FXML
    private RadioButton departureRadioButton;
    @FXML
    private Spinner<Integer> hourSpinner;
    @FXML
    private Spinner<Integer> minutesSpinner;
    @FXML
    private Spinner<Integer> delaySpinner;
    @FXML
    private ComboBox<String> cityComboBox;
    @FXML
    private ComboBox<String> airlineComboBox;
    private Stage stage;


    public void displayPopup(Stage stage, Parent root) {
        this.stage = stage;
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Create a new flight");

        stage.setOnCloseRequest(e -> {
            e.consume();
            onCancelButtonClicked();
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/resources/icon.png"));
        stage.showAndWait();
    }

    @FXML
    private void onCancelButtonClicked() {
        closeWindow();
    }


    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }

    public void onCreateButtonClicked() {
        closeWindow();
    }

    @FXML
    private void initialize(){
        ObservableList<String> cityList = FXCollections.observableArrayList();
        Airport[] airports = AirportSet.AIRPORTS;
        for (int i = 0; i < airports.length; i++) {
            cityList.add(airports[i].getCity());
        }
        cityComboBox.setItems(cityList);

        ObservableList<String> airlineList = FXCollections.observableArrayList();
        Airline[] airlines = AirlinesSet.AIRLINES;
        for (int i = 0; i < airlines.length; i++) {
            airlineList.add(airlines[i].getAirlineName());
        }
        airlineComboBox.setItems(airlineList);

        ToggleGroup toggleGroup = new ToggleGroup();

        arrivalRadioButton.setToggleGroup(toggleGroup);
        departureRadioButton.setToggleGroup(toggleGroup);
    }

    public ArrayList<Object> getComoponents(){
        ArrayList<Object> components = new ArrayList<>();
        components.add(arrivalRadioButton.isSelected());
        components.add(hourSpinner.getValue());
        components.add(minutesSpinner.getValue());
        components.add(delaySpinner.getValue());
        components.add(cityComboBox.getValue());
        components.add(airlineComboBox.getValue());
        return components;
    }

}
