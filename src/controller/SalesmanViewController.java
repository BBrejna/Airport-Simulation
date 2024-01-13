package controller;

import controller.elementsProperties.SalesmanFlightProperty;
import controller.popups.AdminFlightPopupController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

public class SalesmanViewController implements Observer<ArrayList<Flight>>, Logger {

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

        ControllersHandler.getInstance().setSalesmanViewController(this);

        Admin.getInstance().addObserver(this);

    }

    @Override
    public void observerUpdateState(ArrayList<Flight> flights) {

        log("Adding flights to the Salesman UI");

        Platform.runLater(() -> {

            delayInfo.setText(Admin.getInstance().getCurrentDelayProbability());

            flightsTableView.getItems().clear();

            ArrayList<SalesmanFlightProperty> flightsProperties = new ArrayList<>();
            for(Flight flight: flights) {

                String city = flight.getDestinationPoint().getCity();
                String seats = Integer.toString(flight.getNumOfOccupiedSeats()[0] + flight.getNumOfOccupiedSeats()[1] +
                        flight.getNumOfOccupiedSeats()[2])+"/"+flight.getAirplane().getNumberOfSeats();
                String prices = Double.toString(flight.getTicketPrice()[0])+" / "+Double.toString(flight.getTicketPrice()[1])+" / "+Double.toString(flight.getTicketPrice()[2]);
                if(flight.isArrival()) {
                    continue;
                }



                SalesmanFlightProperty flightProperty = new SalesmanFlightProperty(
                        flight.getFlightNumber(),
                        flight.getHour(),
                        city,
                        seats,
                        prices

                );

                flightsProperties.add(flightProperty);

            }

            flightsTableView.getItems().addAll(flightsProperties);

        });

    }


}
