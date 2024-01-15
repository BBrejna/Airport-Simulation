package controller;

import controller.elementsProperties.SalesmanFlightProperty;
import controller.popups.FlightViewPopupController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Admin;
import model.classes.Observer;
import model.classes.admin.Flight;
import model.classes.logging.Log;
import model.classes.logging.Logger;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private TableView<SalesmanFlightProperty> flightsTableView;
    @FXML
    private Label currentTimeLabel;


    public void createFlightAction() {

    }

    public void initialize() {

        ControllersHandler.getInstance().setSalesmanViewController(this);

        Admin.getInstance().addObserver(this);
        flightsTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (! flightsTableView.getSelectionModel().isEmpty()) ) {
                SalesmanFlightProperty rowData = flightsTableView.getSelectionModel().getSelectedItem();
                openNewWindow(rowData);
            }
        });
        //UI FEATURES
        Font digitalFont = Font.loadFont(getClass().getResourceAsStream("/resources/E1234.ttf"),30);
        currentTimeLabel.setFont(digitalFont);
    }

    @Override
    public void observerUpdateState(ArrayList<Flight> flights) {

        log("Adding flights to the Salesman UI");

        Platform.runLater(() -> {


            flightsTableView.getItems().clear();

            ArrayList<SalesmanFlightProperty> flightsProperties = new ArrayList<>();
            for(Flight flight: flights) {

                String city = flight.getDestinationPoint().getCity();
                String seats = Integer.toString(flight.getPassengers().size())+"/"+flight.getAirplane().getNumberOfSeats();
                String prices = Double.toString(flight.getTicketPrice()[0])+" / "+Double.toString(flight.getTicketPrice()[1])+" / "+Double.toString(flight.getTicketPrice()[2]);
                if(flight.isArrival()) {
                    continue;
                }
                SalesmanFlightProperty flightProperty = new SalesmanFlightProperty(
                        flight.getFlightNumber(),
                        flight.getHour(),
                        city,
                        seats,
                        prices,
                        flight
                );

                flightsProperties.add(flightProperty);

            }

            flightsTableView.getItems().addAll(flightsProperties);

        });

    }
    private void openNewWindow(SalesmanFlightProperty data) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/popups/FlightViewPopup.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();
            // Get the controller of the new window and pass the data
            FlightViewPopupController controller = loader.getController();
            controller.setFlight(data.getFlight());
            controller.display(popupStage,root,data.getFlight().getPassengers());
            controller.setSvc(this);
            // Set up the stage (i.e., the new window)
            popupStage.setTitle("Lotnisko - Lot: " + data.getFlightNumber());
            popupStage.getIcons().add(new Image("/resources/icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
    public void updateFlightsTableView() {
        Platform.runLater(() -> {
            flightsTableView.getItems().clear();
            ArrayList<SalesmanFlightProperty> updatedFlightsProperties = getUpdatedFlightsProperties();
            flightsTableView.getItems().addAll(updatedFlightsProperties);
        });
    }

    private ArrayList<SalesmanFlightProperty> getUpdatedFlightsProperties() {
        // Assuming you have a method to fetch or compute the updated list of flights
        ArrayList<Flight> updatedFlights = Admin.getInstance().getFlights();
        ArrayList<SalesmanFlightProperty> flightsProperties = new ArrayList<>();
        for (Flight flight : updatedFlights) {
            String city = flight.getDestinationPoint().getCity();
            String seats = Integer.toString(flight.getPassengers().size())+"/"+flight.getAirplane().getNumberOfSeats();
            String prices = Double.toString(flight.getTicketPrice()[0])+" / "+Double.toString(flight.getTicketPrice()[1])+" / "+Double.toString(flight.getTicketPrice()[2]);
            if(flight.isArrival()) {
                continue;
            }
            SalesmanFlightProperty flightProperty = new SalesmanFlightProperty(
                    flight.getFlightNumber(),
                    flight.getHour(),
                    city,
                    seats,
                    prices,
                    flight
            );

            flightsProperties.add(flightProperty);
        }
        return flightsProperties;
    }


}
