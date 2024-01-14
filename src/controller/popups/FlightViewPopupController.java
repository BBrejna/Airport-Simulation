package controller.popups;

import controller.elementsProperties.PassengerProperty;
import controller.elementsProperties.SalesmanFlightProperty;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Admin;
import model.classes.Observer;
import model.classes.admin.Flight;
import model.classes.logging.Log;
import model.classes.logging.Logger;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.classes.people.Passenger;

import java.io.IOException;
import java.util.ArrayList;

public class FlightViewPopupController implements Logger {

    private ArrayList<Log> logs = new ArrayList<>();

    public ArrayList<Log> getLogs() {
        return logs;
    }

    @FXML
    private TableView<PassengerProperty> PassengersTableView;
    private ArrayList<Passenger> passengers = new ArrayList<>();
    public void initialize() {
    }
    public void display(Stage stage, Parent root, ArrayList<Passenger> passengers)
    {
        stage.setScene(new Scene(root));
        stage.show();
        PassengersTableView.getItems().clear();
        ArrayList<PassengerProperty> passengersProperties = new ArrayList<>();
        for (Passenger passenger : passengers) {
            PassengerProperty passengerProperty = new PassengerProperty(
                    passenger.getName(),
                    passenger.getSurname(),
                    passenger.getPesel(),
                    Integer.toString(passenger.getTicket().getFlightClass()),
                    Integer.toString(passenger.getLuggageWeight())
            );
            passengersProperties.add(passengerProperty);
        }
        PassengersTableView.getItems().addAll(passengersProperties);
        System.out.println("A");
    }

}


