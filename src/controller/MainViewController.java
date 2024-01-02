package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import model.Admin;
import model.Simulation;
import model.classes.admin.Flight;
import model.classes.logging.Log;

import java.util.ArrayList;

public class MainViewController {
    @FXML
    private ListView<Flight> arrivalsList;
    @FXML
    private ListView<Flight> departuresList;
    @FXML
    private HBox RoleMenuHBox;
    @FXML
    private Button simulationButton;
    @FXML
    private Button adminButton;
    @FXML
    private Button salesmanButton;
    @FXML
    private Button physicalWorkerButton;
    @FXML
    private BorderPane bodyBorderPane;
    @FXML
    private HBox timeBarHBox;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button rerunButton;
    @FXML
    private ListView adminLogsList;
    @FXML
    private ListView salesmanLogsList;
    @FXML
    private ListView physicalWorkerLogsList;
    @FXML
    private ListView<Log> simulationLogsList;

    public void updateCurrentTimeLabel(String newText) {
        currentTimeLabel.setText(newText);
    }
    ObservableList<Flight> departures = null;
    ObservableList<Flight> arrivals = null;
    ObservableList<Log> simulationLogs = null;
    public void updateTimeTables() {
        this.departures.setAll(Admin.getInstance().getDepartures());
        this.arrivals.setAll(Admin.getInstance().getArrivals());
    }
    public void updateLogs() {
        this.simulationLogs.setAll(Simulation.getInstance().getLogs());

        int lastIndexSimulation = this.simulationLogs.size()-1;
        this.simulationLogsList.scrollTo(lastIndexSimulation);
        this.simulationLogsList.getSelectionModel().select(lastIndexSimulation);
    }


    public void initialize() {
        Simulation.getInstance().setMainViewController(this);
        Simulation.getInstance().addObserver(Admin.getInstance());

        departures = FXCollections.observableArrayList(Admin.getInstance().getDepartures());
        departuresList.setItems(departures);
        arrivals = FXCollections.observableArrayList(Admin.getInstance().getArrivals());
        arrivalsList.setItems(arrivals);

        simulationLogs = FXCollections.observableArrayList(Simulation.getInstance().getLogs());
        simulationLogsList.setItems(simulationLogs);
    }

    public void handlePlayButtonClick() {
        if (Simulation.getInstance().isSimulationStarted()) {
            Simulation.getInstance().setTimeStopped(false);
        } else {
            Simulation.getInstance().start();
        }
        pauseButton.setDisable(false);
        playButton.setDisable(true);
    }
    public void handlePauseButtonClick() {
        if (Simulation.getInstance().isSimulationStarted()) {
            if (!Simulation.getInstance().isTimeStopped()) {
                pauseButton.setDisable(true);
                playButton.setDisable(false);
            }
            Simulation.getInstance().setTimeStopped(true);
        }
    }

    public void handleRerunButtonClick() {
        System.out.println("You shall not pass");
    }

}
