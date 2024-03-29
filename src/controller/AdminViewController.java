package controller;

import controller.elementsProperties.FlightProperty;
import controller.popups.AdminFlightPopupController;
import controller.popups.DeleteFlightPopupController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
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
    @FXML
    private TableColumn flightNumberColumn;
    @FXML
    private TableColumn hourColumn;
    @FXML
    private TableColumn delayColumn;
    @FXML
    private TableColumn typeColumn;
    @FXML
    private TableColumn deleteColumn;
    @FXML
    private Label currentTimeLabel;


    public void initialize() {
        createFlightButton.setDisable(true);

        ControllersHandler.getInstance().setAdminViewController(this);
        ControllersHandler.getInstance().getSimulationViewController().addObserver(new TimeObserver());
        Admin.getInstance().addObserver(this);

        flightsTableView.setEditable(true);
//        flightNumberColumn.setEditable(true);
//        hourColumn.setEditable(true);
//        delayColumn.setEditable(true);
//        typeColumn.setEditable(true);
//        deleteColumn.setEditable(true);
        for(Object column : flightsTableView.getColumns()){
            if(((TableColumn)column).isEditable()){
                ((TableColumn)column).getStyleClass().addAll("clickable");
            }
        }

        flightNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        hourColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        delayColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        deleteColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        flightNumberColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FlightProperty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FlightProperty, String> flightStringCellEditEvent) {
                String oldFlightNumber = flightStringCellEditEvent.getRowValue().getFlightNumber();
                String newFlightNumber = flightStringCellEditEvent.getNewValue();
                Admin.getInstance().modifyFlightNumber(oldFlightNumber, newFlightNumber);
                Admin.getInstance().notifyObservers(Admin.getInstance().getFlights());
            }
        });

        hourColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FlightProperty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FlightProperty, String> flightPropertyStringCellEditEvent) {
                String flightNumber = flightPropertyStringCellEditEvent.getRowValue().getFlightNumber();
                String newHour = flightPropertyStringCellEditEvent.getNewValue();
                Admin.getInstance().modifyHour(flightNumber, newHour);
                Admin.getInstance().notifyObservers(Admin.getInstance().getFlights());
            }
        });

        typeColumn.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<FlightProperty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FlightProperty, String> flightPropertyStringCellEditEvent) {
                String flightNumber = flightPropertyStringCellEditEvent.getRowValue().getFlightNumber();
                Admin.getInstance().modifyType(flightNumber);
                Admin.getInstance().notifyObservers(Admin.getInstance().getFlights());
            }
        });

        delayColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FlightProperty, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FlightProperty, String> flightPropertyStringCellEditEvent) {
                String flightNumber = flightPropertyStringCellEditEvent.getRowValue().getFlightNumber();
                String newDelay = flightPropertyStringCellEditEvent.getNewValue();
                Admin.getInstance().modifyDelay(flightNumber, newDelay);
                Admin.getInstance().notifyObservers(Admin.getInstance().getFlights());
                //observerUpdateState(Admin.getInstance().getFlights());
            }
        });

        deleteColumn.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<FlightProperty, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<FlightProperty, String> flightPropertyStringCellEditEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/popups/DeleteFlightPopup.fxml"));
                    Parent root = loader.load();
                    Stage popupStage = new Stage();
                    DeleteFlightPopupController popupController = loader.getController();
                    if(popupController.displayPopup(popupStage, root)) {
                        String flightNumber = flightPropertyStringCellEditEvent.getRowValue().getFlightNumber();
                        Admin.getInstance().deleteFlight(flightNumber);
                        Admin.getInstance().notifyObservers(Admin.getInstance().getFlights());
                    }
                } catch (IOException ignored) {}

//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Delete flight");
//                alert.setContentText("Are you sure you want to delete the flight?");
//
//                Optional<ButtonType> result = alert.showAndWait();
//
//                if(result.get() == ButtonType.OK) {
//                    String flightNumber = flightPropertyStringCellEditEvent.getRowValue().getFlightNumber();
//
//                    Admin.getInstance().deleteFlight(flightNumber);
//                    Admin.getInstance().notifyObservers(Admin.getInstance().getFlights());
//                }

            }
        });
        //UI FEATURES
        Font digitalFont = Font.loadFont(getClass().getResourceAsStream("/resources/E1234.ttf"),30);
        currentTimeLabel.setFont(digitalFont);
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
                        delay,
                        new ImageView(new Image("/resources/delete.png", 22, 20, false, false))
                        //new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/delete.png"))))
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

    class TimeObserver implements Observer<String> {
        @Override
        public void observerUpdateState(String time) {
            Platform.runLater(() -> {
                currentTimeLabel.setText(time);
            });
        }
    }

    private void handleCreateFlight(AdminFlightPopupController popupController){
        Admin.getInstance().createFlight(popupController.getComoponents());

    }

    public Button getCreateFlightButton() {
        return createFlightButton;
    }

}
