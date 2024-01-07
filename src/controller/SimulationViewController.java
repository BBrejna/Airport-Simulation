package controller;

import controller.elementsProperties.FlightProperty;
import controller.elementsProperties.WeatherProperty;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Admin;
import model.Salesman;
import model.Simulation;
import model.Workman;
import model.classes.Observer;
import model.classes.admin.Flight;
import model.classes.logging.Log;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.classes.simulation.Weather;
import model.tools.Tools;
import model.classes.Subject;
import java.io.IOException;
import java.util.ArrayList;

public class SimulationViewController {
    public TableView<FlightProperty> departuresTable;
    public TableView<FlightProperty> arrivalsTable;
    @FXML
    private TableView<WeatherProperty> weatherTableView;
    @FXML
    public VBox BottomVBox;
    @FXML
    public Button settingsButton;
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

    public Button getPauseButton() {
        return pauseButton;
    }

    public void updateCurrentTimeLabel(String newText) {
        currentTimeLabel.setText(newText);
    }

    ObservableList<Log> simulationLogs = null;
    ObservableList<Log> adminLogs = null;
    ObservableList<Log> salesmanLogs = null;
    ObservableList<Log> workmanLogs = null;

    public void updateLogs() {
        this.simulationLogs.setAll(Simulation.getInstance().getLogs());

        int lastIndexSimulation = this.simulationLogs.size() - 1;
        this.simulationLogsList.scrollTo(lastIndexSimulation);
        this.simulationLogsList.getSelectionModel().select(lastIndexSimulation);


        this.adminLogs.setAll(Admin.getInstance().getLogs());

        int lastIndexAdmin = this.adminLogs.size() - 1;
        this.adminLogsList.scrollTo(lastIndexAdmin);
        this.adminLogsList.getSelectionModel().select(lastIndexAdmin);


        this.salesmanLogs.setAll(Salesman.getInstance().getLogs());

        int lastIndexSalesman = this.salesmanLogs.size() - 1;
        this.salesmanLogsList.scrollTo(lastIndexSalesman);
        this.salesmanLogsList.getSelectionModel().select(lastIndexSalesman);

        this.workmanLogs.setAll(Workman.getInstance().getLogs());

        int lastIndexWorkman = this.workmanLogs.size() - 1;
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
        listView.setCellFactory(param -> new ListCell<>() {
            private Text text = new Text();

            @Override
            protected void updateItem(Log item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    text.setText(null);
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
        Simulation.getInstance().addObserver(new WeatherObserver());
        Admin.getInstance().addObserver(new FlightsObserver());

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

        // Setting new temperature
        double current_temp = Simulation.getInstance().getWeather().getTemperature();
        try{
            int value = popupController.getTemperatureValue();
            Simulation.getInstance().getWeather().setTemperature(value);
        }catch (NumberFormatException e){
            Simulation.getInstance().getWeather().setTemperature(current_temp);
        }
        //Setting new wind
        double current_wind = Simulation.getInstance().getWeather().getWind();
        try{
            int value = popupController.getWindValue();
            Simulation.getInstance().getWeather().setWind(value);
            Simulation.getInstance().notifyObservers(Simulation.getInstance().getWeather());
        }catch (NumberFormatException e){
            Simulation.getInstance().getWeather().setWind(current_wind);
        }catch (Weather.WindValueOutOfRangeException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Wind vale range: 0-100!");
            alert.showAndWait();
        }
        //Setting new rain
        double current_rain = Simulation.getInstance().getWeather().getRain();
        try{
            int value = popupController.getRainValue();
            Simulation.getInstance().getWeather().setRain(value);
        }catch (NumberFormatException e){
            Simulation.getInstance().getWeather().setRain(current_rain);
        }
        //Setting new snow
        double current_snow = Simulation.getInstance().getWeather().getSnow();
        try{
            int value = popupController.getSnowValue();
            Simulation.getInstance().getWeather().setSnow(value);
        }catch (NumberFormatException e){
            Simulation.getInstance().getWeather().setSnow(current_snow);
        }
        //Setting new fog
        double current_fog = Simulation.getInstance().getWeather().getFog();
        try{
            int value = popupController.getFogValue();
            Simulation.getInstance().getWeather().setFog(value);
        }catch (NumberFormatException e){
            Simulation.getInstance().getWeather().setFog(current_fog);
        }
        //Setting new clouds
        double current_clouds = Simulation.getInstance().getWeather().getClouds();
        try{
            int value = popupController.getCloudsValue();
            Simulation.getInstance().getWeather().setClouds(value);
        }catch (NumberFormatException e){
            Simulation.getInstance().getWeather().setClouds(current_clouds);
        }

    }
    /*public void handleSetWeatherB uttonClick() throws IOException {
        handlePauseButtonClick();
        // Load the popup FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SimulationSetWeather.fxml"));
        Parent root = loader.load();

        // Create a new stage for the popup
        Stage popupStage = new Stage();

        // Set the popup controller and stage
        SimulationSetWeatherController popupController = loader.getController();
        popupController.display(popupStage, root);

        // Retrieve the selected value from the popup controller
        int selectedValue = popupController.getValue();
        Simulation.getInstance().getWeather().setTemperature(selectedValue);
        //Simulation.getInstance().setTimeDelta(selectedValue);
    }*/

    class WeatherObserver implements Observer<Weather> {
        @Override
        public void observerUpdateState(Weather weather) {
            Platform.runLater(() -> {
                weatherTableView.getItems().clear();  // Clear previous items

                // Populate TableView with new items
                weatherTableView.getItems().addAll(
                        new WeatherProperty("Temperature [°C]", weather.getTemperature()),
                        new WeatherProperty("Wind [km/h]", weather.getWind()),
                        new WeatherProperty("Rain [mm/h]", weather.getRain()),
                        new WeatherProperty("Snow [mm/h]", weather.getSnow()),
                        new WeatherProperty("Fog [RS]", weather.getFog()),
                        new WeatherProperty("Clouds [RS]", weather.getClouds())
                );
            });
        }
    }
    class FlightsObserver implements Observer<ArrayList<Flight>> {
        @Override
        public void observerUpdateState(ArrayList<Flight> flights) {
            Platform.runLater(() -> {
                departuresTable.getItems().clear();  // Clear previous items
                arrivalsTable.getItems().clear();  // Clear previous items

                flights.forEach(flight -> {
                    if (flight.isArrival()) {
                        arrivalsTable.getItems().add(new FlightProperty(flight.getFlightNumber(), flight.getHour(), flight.getSourcePoint().getCity(), "ARRIVAL", "AIRPLANE", Tools.convertMinutesToTime(flight.getActualHour())));
                    }
                    else {
                        departuresTable.getItems().add(new FlightProperty(flight.getFlightNumber(), flight.getHour(), flight.getDestinationPoint().getCity(), "DEPARTURE", "AIRPLANE", Tools.convertMinutesToTime(flight.getActualHour())));
                    }
                });
            });
        }
    }
}