package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Admin;
import model.Simulation;
import model.classes.admin.Flight;
import model.classes.logging.Log;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController {
    @FXML
    public VBox BottomVBox;
    @FXML
    public Button settingsButton;
    @FXML
    private ListView<Flight> arrivalsList;
    @FXML
    private ListView<Flight> departuresList;
    @FXML
    private Button simulationButton;
    @FXML
    private Button adminButton;
    @FXML
    private Button salesmanButton;
    @FXML
    private Button physicalWorkerButton;
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
    public void handleSimulationFinish() {
        playButton.setDisable(false);
        pauseButton.setDisable(true);
        rerunButton.setDisable(true);
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
        if (Simulation.getInstance().isSimulationStarted() && !Simulation.getInstance().isSimulationFinished()) {
            Simulation.getInstance().setTimeStopped(false);
        } else {
            Simulation.getInstance().start();
        }
        playButton.setDisable(true);
        pauseButton.setDisable(false);
        rerunButton.setDisable(true);
    }
    public void handlePauseButtonClick() {
        if (Simulation.getInstance().isSimulationStarted()) {
            if (!Simulation.getInstance().isTimeStopped()) {
                playButton.setDisable(false);
                pauseButton.setDisable(true);
                rerunButton.setDisable(false);
            }
            Simulation.getInstance().setTimeStopped(true);
        }
    }

    public void handleRerunButtonClick() {
        Simulation.getInstance().rerun();
        playButton.setDisable(true);
        pauseButton.setDisable(false);
        rerunButton.setDisable(true);
    }

    public void handleSettingsButtonClick() throws IOException {
        handlePauseButtonClick();

        // Load the popup FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SimulationSettingsPopup.fxml"));
        Parent root = loader.load();

        // Create a new stage for the popup
        Stage popupStage = new Stage();

        // Set the popup controller and stage
        SimulationSettingsPopupController popupController = loader.getController();
        popupController.display(popupStage, root, Simulation.getInstance().getTimeDelta(), Simulation.getInstance().getMAX_TIME_DELTA());

        // Retrieve the selected value from the popup controller
        int selectedValue = popupController.getSelectedValue();
        Simulation.getInstance().setTimeDelta(selectedValue);
    }
}
