package controller;

import controller.elementsProperties.FlightProperty;
import controller.popups.AdminFlightPopupController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Admin;
import model.classes.Observer;
import model.classes.admin.Flight;
import model.classes.logging.Log;
import model.classes.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;

public class AdminViewController implements Observer<ArrayList<Flight>>, Logger {

    private ArrayList<Log> logs = new ArrayList<>();
    public ArrayList<Log> getLogs() {
        return logs;
    }

    @FXML
    private HBox adminMenuButtonsBox;
    @FXML
    private Button createFlightButton;
    @FXML
    private HBox delayInfoBox;
    @FXML
    private Label delayInfo;
    @FXML
    private TableView flightsTableView;


    public void createFlightAction() {

    }

    public void initialize() {

        ControllersHandler.getInstance().setAdminViewController(this);

        Admin.getInstance().addObserver(this);

    }

    @Override
    public void observerUpdateState(ArrayList<Flight> flights) {

        log("Adding flights to the Admin UI");

        Platform.runLater(() -> {

            delayInfo.setText(Admin.getInstance().getCurrentDelayProbability());

            flightsTableView.getItems().clear();

            ArrayList<FlightProperty> flightsProperties = new ArrayList<>();
            for(Flight flight: flights) {

                String city = flight.getDestinationPoint().getCity();
                String type = "DEPARTURE";
                if(flight.isArrival()) {
                    city = flight.getSourcePoint().getCity();
                    type = "ARRIVAL";
                }

                String delay = Integer.toString(flight.getDelayMinutes());
                if(delay.length() == 2) delay = "0" + delay;
                else if(delay.length() == 1) delay = "00" + delay;

                FlightProperty flightProperty = new FlightProperty(
                        flight.getFlightNumber(),
                        flight.getHour(),
                        city,
                        type,
                        flight.getAirplane().getAirplaneModel().getModelName(),
                        delay
                );

                flightsProperties.add(flightProperty);

            }

            flightsTableView.getItems().addAll(flightsProperties);

        });

    }

    public void handleCreateFlightButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/popups/AdminFlightPopup.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();

        AdminFlightPopupController popupController = loader.getController();

        popupController.displayPopup(popupStage, root);

        if (popupController.getIsCreate()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Flight created successfully.");
            alert.showAndWait();

            handleCreateFlight(popupController);
        }
    }


    private void handleCreateFlight(AdminFlightPopupController popupController){
        Admin.getInstance().createFlight(popupController.getComoponents());

    }
}
