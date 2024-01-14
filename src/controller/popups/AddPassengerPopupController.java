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
import model.classes.admin.Flight;
import model.classes.people.Passenger;
import model.classes.salesman.Ticket;
import model.classes.simulation.Weather;

import java.util.ArrayList;
import java.util.List;

public class AddPassengerPopupController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField PESELField;
    @FXML
    private ComboBox<String> classComboBox;
    @FXML
    private TextField luggageField;
    private Stage stage;
    private Flight flight;
    private boolean isCreate;
    ObservableList<String> items = FXCollections.observableArrayList();
    public void displayPopup(Stage stage, Parent root, Flight flight) {
        this.flight=flight;
        this.stage = stage;
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Create a new passenger");

        stage.setOnCloseRequest(e -> {
            e.consume();
            onCancelButtonClicked();
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/resources/icon.png"));
        stage.showAndWait();

        if(flight.getNumOfOccupiedSeats()[0]!=flight.getAirplane().getNumberOfSeatsClasses()[0]) classComboBox.getItems().add("0");
        if(flight.getNumOfOccupiedSeats()[1]!=flight.getAirplane().getNumberOfSeatsClasses()[1]) classComboBox.getItems().add("1");
        if(flight.getNumOfOccupiedSeats()[2]!=flight.getAirplane().getNumberOfSeatsClasses()[2]) classComboBox.getItems().add("2");
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
//        try {
//            checkExceptions();
//        } catch (Weather.ValuesOutOfRangeException e) {
//            isReady = false;
//            System.out.println("Incorrect values exception.");
//        }

        if (isReady){
            closeWindow();
        }


    }


    public boolean checkIfInt(String str) {
        return str.matches("\\d+");
    }


//    public void checkExceptions() throws Weather.ValuesOutOfRangeException {
//        ArrayList<String> exceptions = new ArrayList<>();
//        int i = 0;
//
//        if (checkIfInt(hourField.getText())){
//            int h = Integer.parseInt(hourField.getText());
//            if (h > 23 || h < 0){
//                exceptions.add("Hour field must be a number beetwen 0 and 23.");
//            } else {
//                i++;
//            }
//        } else {
//            exceptions.add("Hour field must be a number beetwen 0 and 23.");
//        }
//
//        if (checkIfInt(minutesField.getText())){
//            int m = Integer.parseInt(minutesField.getText());
//            if (m > 59 || m < 0){
//                exceptions.add("Minutes field must be a number beetwen 0 and 59.");
//            } else {
//                i++;
//            }
//        } else {
//            exceptions.add("Minutes field must be a number beetwen 0 and 59.");
//        }
//
//        if (checkIfInt(delayField.getText())){
//            int d = Integer.parseInt(delayField.getText());
//            if (d > 359 || d < 0){
//                exceptions.add("Delay field must be a number beetwen 0 and 359.");
//            } else {
//                i++;
//            }
//        } else {
//            exceptions.add("Delay field must be a number beetwen 0 and 359.");
//        }
//
//        if (cityComboBox.getValue() == null){
//            exceptions.add("Choose a city.");
//        }
//
//        if (airlineComboBox.getValue() == null){
//            exceptions.add("Choose an airline.");
//        }
//
//
//        if (i == 3){
//            int hour = Integer.parseInt(hourField.getText()) * 60  + Integer.parseInt(minutesField.getText()) + Integer.parseInt(delayField.getText());
//            int runwayNumber = Admin.getInstance().getRunway(hour);
//            if (runwayNumber == -1){
//                exceptions.add("There is no free runway at this time.");
//            }
//        }
//
//
//        if(!exceptions.isEmpty()){
//            throw new Weather.ValuesOutOfRangeException(exceptions);
//        }
//    }



    @FXML
    private void initialize(){
    }




    public Passenger getPassenger() throws ClassCastException{
        Ticket ticket = new Ticket(flight.getFlightNumber(), Integer.parseInt(classComboBox.getValue()));
        Passenger passenger = new Passenger(nameField.getText(),surnameField.getText(),true,Integer.parseInt(luggageField.getText()),flight.getDestinationPoint().getCity(),ticket);

        return passenger;
    }

    public boolean getIsCreate(){
        return isCreate;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
