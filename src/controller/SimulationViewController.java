package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Admin;
import model.Salesman;
import model.Simulation;
import model.Workman;
import model.classes.admin.Flight;
import model.classes.logging.Log;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationViewController {
    @FXML
    public VBox BottomVBox;
    @FXML
    public Button settingsButton;
    @FXML
    private ListView<Flight> arrivalsList;
    @FXML
    private ListView<Flight> departuresList;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button rerunButton;
    @FXML
    private ListView<Log> adminLogsList;
    @FXML
    private ListView<Log> salesmanLogsList;
    @FXML
    private ListView<Log> workmanLogsList;
    @FXML
    private ListView<Log> simulationLogsList;

    public void updateCurrentTimeLabel(String newText) {
        currentTimeLabel.setText(newText);
    }
    ObservableList<Flight> departures = null;
    ObservableList<Flight> arrivals = null;
    ObservableList<Log> simulationLogs = null;
    ObservableList<Log> adminLogs = null;
    ObservableList<Log> salesmanLogs = null;
    ObservableList<Log> workmanLogs = null;
    public void updateTimeTables() {
        this.departures.setAll(Admin.getInstance().getDepartures());
        this.arrivals.setAll(Admin.getInstance().getArrivals());
    }
    public void updateLogs() {
        this.simulationLogs.setAll(Simulation.getInstance().getLogs());

        int lastIndexSimulation = this.simulationLogs.size()-1;
        this.simulationLogsList.scrollTo(lastIndexSimulation);
        this.simulationLogsList.getSelectionModel().select(lastIndexSimulation);


        this.adminLogs.setAll(Admin.getInstance().getLogs());

        int lastIndexAdmin = this.adminLogs.size()-1;
        this.adminLogsList.scrollTo(lastIndexAdmin);
        this.adminLogsList.getSelectionModel().select(lastIndexAdmin);


        this.salesmanLogs.setAll(Salesman.getInstance().getLogs());

        int lastIndexSalesman = this.salesmanLogs.size()-1;
        this.salesmanLogsList.scrollTo(lastIndexSalesman);
        this.salesmanLogsList.getSelectionModel().select(lastIndexSalesman);

        this.workmanLogs.setAll(Workman.getInstance().getLogs());

        int lastIndexWorkman = this.workmanLogs.size()-1;
        this.workmanLogsList.scrollTo(lastIndexWorkman);
        this.workmanLogsList.getSelectionModel().select(lastIndexWorkman);
    }
    public void handleSimulationFinish() {
        playButton.setDisable(false);
        pauseButton.setDisable(true);
        rerunButton.setDisable(true);
        ControllersHandler.getInstance().getMainViewController().lockButtonsOnSimulationRunning(false);
    }

    private void setLineBreaker(ListView<Log> listView) {
        listView.setCellFactory(param -> new ListCell<Log>() {
            private Text text = new Text();

            @Override
            protected void updateItem(Log item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    // Use a Text node for wrapping
                    text.setText(item.toString());
                    text.setWrappingWidth(listView.getWidth() - 30); // Adjust the padding as needed
                    setGraphic(text);
                }
            }
        });
    }

    public void initialize() {
        ControllersHandler.getInstance().setSimulationViewController(this);
        Simulation.getInstance().addObserver(Admin.getInstance());

        departures = FXCollections.observableArrayList(Admin.getInstance().getDepartures());
        departuresList.setItems(departures);
        arrivals = FXCollections.observableArrayList(Admin.getInstance().getArrivals());
        arrivalsList.setItems(arrivals);

        simulationLogs = FXCollections.observableArrayList(Simulation.getInstance().getLogs());
        simulationLogsList.setItems(simulationLogs);

        adminLogs = FXCollections.observableArrayList(Admin.getInstance().getLogs());
        adminLogsList.setItems(adminLogs);

        salesmanLogs = FXCollections.observableArrayList(Salesman.getInstance().getLogs());
        salesmanLogsList.setItems(salesmanLogs);

        workmanLogs = FXCollections.observableArrayList(Workman.getInstance().getLogs());
        workmanLogsList.setItems(workmanLogs);

        setLineBreaker(simulationLogsList);
        setLineBreaker(adminLogsList);
        setLineBreaker(salesmanLogsList);
        setLineBreaker(workmanLogsList);

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
        ControllersHandler.getInstance().getMainViewController().lockButtonsOnSimulationRunning(true);
    }
    public void handlePauseButtonClick() {
        if (Simulation.getInstance().isSimulationStarted()) {
            if (!Simulation.getInstance().isTimeStopped()) {
                playButton.setDisable(false);
                pauseButton.setDisable(true);
                rerunButton.setDisable(false);
            }
            Simulation.getInstance().setTimeStopped(true);
            ControllersHandler.getInstance().getMainViewController().lockButtonsOnSimulationRunning(false);
        }
    }

    public void handleRerunButtonClick() {
        Simulation.getInstance().rerun();
        playButton.setDisable(true);
        pauseButton.setDisable(false);
        rerunButton.setDisable(true);
        ControllersHandler.getInstance().getMainViewController().lockButtonsOnSimulationRunning(true);
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
