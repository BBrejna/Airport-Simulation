package controller.popups;

import controller.AdminViewController;
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
import model.Admin;
import model.classes.admin.Airline;
import model.classes.admin.Airport;
import model.classes.simulation.Weather;

import java.util.ArrayList;

public class AdminFlightPopupController {
    @FXML
    private RadioButton arrivalRadioButton;
    @FXML
    private RadioButton departureRadioButton;
    @FXML
    private TextField hourField;
    @FXML
    private TextField minutesField;
    @FXML
    private TextField delayField;
    @FXML
    private ComboBox<String> cityComboBox;
    @FXML
    private ComboBox<String> airlineComboBox;
    private Stage stage;
    private boolean isCreate;

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
        isCreate = false;
        closeWindow();
    }


    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }


    public void onCreateButtonClicked() {
        isCreate = true;

        boolean isReady = true;
        try {
            checkExceptions();
        } catch (Weather.ValuesOutOfRangeException e) {
            isReady = false;
            System.out.println("Incorrect values exception.");
        }

        if (isReady){
            closeWindow();
        }


    }


    public boolean checkIfInt(String str) {
        return str.matches("\\d+");
    }


    public void checkExceptions() throws Weather.ValuesOutOfRangeException {
        ArrayList<String> exceptions = new ArrayList<>();
        int i = 0;

        if (checkIfInt(hourField.getText())){
            int h = Integer.parseInt(hourField.getText());
            if (h > 23 || h < 0){
                exceptions.add("Hour field must be a number beetwen 0 and 23.");
            } else {
                i++;
            }
        } else {
            exceptions.add("Hour field must be a number beetwen 0 and 23.");
        }

        if (checkIfInt(minutesField.getText())){
            int m = Integer.parseInt(minutesField.getText());
            if (m > 59 || m < 0){
                exceptions.add("Minutes field must be a number beetwen 0 and 59.");
            } else {
                i++;
            }
        } else {
            exceptions.add("Minutes field must be a number beetwen 0 and 59.");
        }

        if (checkIfInt(delayField.getText())){
            int d = Integer.parseInt(delayField.getText());
            if (d > 359 || d < 0){
                exceptions.add("Delay field must be a number beetwen 0 and 359.");
            } else {
                i++;
            }
        } else {
            exceptions.add("Delay field must be a number beetwen 0 and 359.");
        }

        if (cityComboBox.getValue() == null){
            exceptions.add("Choose a city.");
        }

        if (airlineComboBox.getValue() == null){
            exceptions.add("Choose an airline.");
        }


        if (i == 3){
            int hour = Integer.parseInt(hourField.getText()) * 60  + Integer.parseInt(minutesField.getText()) + Integer.parseInt(delayField.getText());
            int runwayNumber = Admin.getInstance().getRunway(hour);
            if (runwayNumber == -1){
                exceptions.add("There is no free runway at this time.");
            }
        }


        if(!exceptions.isEmpty()){
            throw new Weather.ValuesOutOfRangeException(exceptions);
        }
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

        arrivalRadioButton.setSelected(true);
    }




    public ArrayList<Object> getComoponents() throws ClassCastException{
        ArrayList<Object> components = new ArrayList<>();
        components.add(arrivalRadioButton.isSelected());
        components.add(Integer.parseInt(hourField.getText()));
        components.add(Integer.parseInt(minutesField.getText()));
        components.add(Integer.parseInt(delayField.getText()));
        components.add(cityComboBox.getValue());
        components.add(airlineComboBox.getValue());
        return components;
    }

    public boolean getIsCreate(){
        return isCreate;
    }

}
