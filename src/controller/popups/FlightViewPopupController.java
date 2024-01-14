package controller.popups;

import controller.elementsProperties.PassengerProperty;
import controller.elementsProperties.SalesmanFlightProperty;
import javafx.application.Platform;
import controller.SalesmanViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Admin;
import model.Salesman;
import model.classes.Observer;
import model.classes.admin.Flight;
import model.classes.logging.Log;
import model.classes.logging.Logger;
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
    private Flight flight;
    private ArrayList<Passenger> passengers;
    private SalesmanViewController svc;
    public void initialize() {
    }
    public void display(Stage stage, Parent root, ArrayList<Passenger> passengers)
    {
        stage.setScene(new Scene(root));
        stage.show();
        this.passengers = passengers;
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
    }
    public void deletePassenger()
    {
        int selectedIndex = PassengersTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            PassengersTableView.getItems().remove(selectedIndex);
            flight.getPassengers().remove(selectedIndex);
            svc.updateFlightsTableView();
        }
    }
    public void addPassenger ()throws IOException
    {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/popups/AddPassengerPopup.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();

        AddPassengerPopupController popupController = loader.getController();
        popupController.displayPopup(popupStage, root, flight);

        if (popupController.getIsCreate()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Passenger created successfully.");
            alert.showAndWait();

            handleCreatePassenger(popupController);
        }
    }
    private void handleCreatePassenger(AddPassengerPopupController popupController){
        Salesman.getInstance().addPassenger(popupController.getPassenger());
        svc.updateFlightsTableView();
        updateFlightsTableView();
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setSvc(SalesmanViewController svc) {
        this.svc = svc;
    }
    public void updateFlightsTableView() {
        Platform.runLater(() -> {
            PassengersTableView.getItems().clear();
            ArrayList<PassengerProperty> updatedPassengerProperties = getUpdatedPassengerProperties();
            PassengersTableView.getItems().addAll(updatedPassengerProperties );
        });
    }

    private ArrayList<PassengerProperty> getUpdatedPassengerProperties () {
        // Assuming you have a method to fetch or compute the updated list of flights
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
        return passengersProperties;
    }
}


