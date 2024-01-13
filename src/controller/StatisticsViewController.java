package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Simulation;
import model.classes.Observer;
import model.classes.admin.Flight;
import model.classes.simulation.Weather;
import model.tools.Tools;

import java.util.ArrayList;

public class StatisticsViewController {

    @FXML
    private LineChart<String, Number> temperatureChart;
    @FXML
    private LineChart<String, Number> windChart;
    @FXML
    private LineChart<String, Number> cloudsChart;
    @FXML
    private LineChart<String, Number> rainChart;
    @FXML
    private LineChart<String, Number> snowChart;
    @FXML
    private BarChart<String, Number> arrivingPassengersChart;
    @FXML
    private BarChart<String, Number> departingPassengersChart;
    @FXML
    private VBox passengersBox;
    @FXML
    private VBox flightsBox;
    @FXML
    private HBox buttonsBox;
    @FXML
    private HBox weatherButtonsBox;
    @FXML
    private BarChart<String, Number> arrivingFlightsChart;
    @FXML
    private BarChart<String, Number> departingFlightsChart;
    private XYChart.Series<String, Number> temp_series = new XYChart.Series<>();
    private XYChart.Series<String, Number> wind_series = new XYChart.Series<>();
    private XYChart.Series<String, Number> clouds_series = new XYChart.Series<>();
    private XYChart.Series<String, Number> rain_series = new XYChart.Series<>();
    private XYChart.Series<String, Number> snow_series = new XYChart.Series<>();
    private XYChart.Series<String, Number> arriving_passengers_series = new XYChart.Series<>();
    private XYChart.Series<String, Number> departing_passengers_series = new XYChart.Series<>();
    private XYChart.Series<String, Number> arriving_flights_series = new XYChart.Series<>();
    private XYChart.Series<String, Number> departing_flights_series = new XYChart.Series<>();

    public void initialize() {
        ControllersHandler.getInstance().setStatisticsViewController(this);
        Simulation.getInstance().addObserver(new WeatherObserver());
        temperatureChart.getData().add(temp_series);
        windChart.getData().add(wind_series);
        cloudsChart.getData().add(clouds_series);
        rainChart.getData().add(rain_series);
        snowChart.getData().add(snow_series);
        arrivingPassengersChart.getData().add(arriving_passengers_series);
        departingPassengersChart.getData().add(departing_passengers_series);
        arrivingFlightsChart.getData().add(arriving_flights_series);
        departingFlightsChart.getData().add(departing_flights_series);
    }

    class WeatherObserver implements Observer<Weather>{
        @Override
        public void observerUpdateState(Weather weather) {
            Platform.runLater(() -> {
                String time = Tools.convertMinutesToTime(Simulation.getInstance().getTime());
                Number temperature = weather.getTemperature();
                temp_series.getData().add(new XYChart.Data<>(time, temperature));
                Number wind = weather.getWind();
                wind_series.getData().add(new XYChart.Data<>(time, wind));
                Number clouds = weather.getClouds();
                clouds_series.getData().add(new XYChart.Data<>(time, clouds));
                Number rain = weather.getRain();
                rain_series.getData().add(new XYChart.Data<>(time, rain));
                Number snow = weather.getSnow();
                snow_series.getData().add(new XYChart.Data<>(time, snow));
            });
        }
    }

    public void getNewFlights(ArrayList<Flight> flights) {
        Platform.runLater(() -> {
            String time = Tools.convertMinutesToTime(Simulation.getInstance().getTime());
            int arriving_passengers = 0;
            int departing_passengers = 0;
            int arrivingFlights = 0;
            int departingFlights = 0;
            for (Flight flight : flights) {
                if (flight.isArrival()) {
                    arriving_passengers += flight.getNumOfOccupiedSeats()[0] + flight.getNumOfOccupiedSeats()[1] +
                            flight.getNumOfOccupiedSeats()[2];
                    arrivingFlights++;
                } else {
                    departing_passengers += flight.getNumOfOccupiedSeats()[0] + flight.getNumOfOccupiedSeats()[1] +
                            flight.getNumOfOccupiedSeats()[2];
                    departingFlights++;
                }
            }
            Number arrivingPassengers = arriving_passengers;
            Number departingPassengers = departing_passengers;
            Number arrivingFlightsNumber = arrivingFlights;
            Number departingFlightsNumber = departingFlights;
            arriving_passengers_series.getData().add(new XYChart.Data<>(time, arrivingPassengers));
            departing_passengers_series.getData().add(new XYChart.Data<>(time, departingPassengers));
            arriving_flights_series.getData().add(new XYChart.Data<>(time, arrivingFlightsNumber));
            departing_flights_series.getData().add(new XYChart.Data<>(time, departingFlightsNumber));
        });
    }
    public void clearAllCharts(){
        temp_series.getData().clear();
        wind_series.getData().clear();
        clouds_series.getData().clear();
        rain_series.getData().clear();
        snow_series.getData().clear();
        arriving_passengers_series.getData().clear();
        departing_passengers_series.getData().clear();
        arriving_flights_series.getData().clear();
        departing_flights_series.getData().clear();
    }
    @FXML
    private void temperatureButtonClicked() {
        flightsBox.setVisible(false);
        passengersBox.setVisible(false);
        temperatureChart.setVisible(true);
        windChart.setVisible(false);
        rainChart.setVisible(false);
        snowChart.setVisible(false);
        cloudsChart.setVisible(false);
        weatherButtonsBox.setVisible(false);
        buttonsBox.setVisible(true);
    }

    @FXML
    private void windButtonClicked() {
        flightsBox.setVisible(false);
        passengersBox.setVisible(false);
        temperatureChart.setVisible(false);
        windChart.setVisible(true);
        rainChart.setVisible(false);
        snowChart.setVisible(false);
        cloudsChart.setVisible(false);
        weatherButtonsBox.setVisible(false);
        buttonsBox.setVisible(true);
    }

    @FXML
    private void cloudsButtonClicked() {
        flightsBox.setVisible(false);
        passengersBox.setVisible(false);
        temperatureChart.setVisible(false);
        windChart.setVisible(false);
        rainChart.setVisible(false);
        snowChart.setVisible(false);
        cloudsChart.setVisible(true);
        weatherButtonsBox.setVisible(false);
        buttonsBox.setVisible(true);
    }

    @FXML
    private void rainButtonClicked() {
        flightsBox.setVisible(false);
        passengersBox.setVisible(false);
        temperatureChart.setVisible(false);
        windChart.setVisible(false);
        rainChart.setVisible(true);
        snowChart.setVisible(false);
        cloudsChart.setVisible(false);
        weatherButtonsBox.setVisible(false);
        buttonsBox.setVisible(true);
    }

    @FXML
    private void snowButtonClicked() {
        flightsBox.setVisible(false);
        passengersBox.setVisible(false);
        temperatureChart.setVisible(false);
        windChart.setVisible(false);
        rainChart.setVisible(false);
        snowChart.setVisible(true);
        cloudsChart.setVisible(false);
        weatherButtonsBox.setVisible(false);
        buttonsBox.setVisible(true);
    }
    @FXML
    private void passengersButtonClicked(){
        flightsBox.setVisible(false);
        passengersBox.setVisible(true);
        temperatureChart.setVisible(false);
        windChart.setVisible(false);
        rainChart.setVisible(false);
        snowChart.setVisible(false);
        cloudsChart.setVisible(false);
        weatherButtonsBox.setVisible(false);
        buttonsBox.setVisible(true);
    }
    @FXML
    private void flightsButtonClicked(){
        flightsBox.setVisible(true);
        passengersBox.setVisible(false);
        temperatureChart.setVisible(false);
        windChart.setVisible(false);
        rainChart.setVisible(false);
        snowChart.setVisible(false);
        cloudsChart.setVisible(false);
        weatherButtonsBox.setVisible(false);
        buttonsBox.setVisible(true);
    }
    @FXML
    private void weatherButtonClicked(){
        weatherButtonsBox.setVisible(true);
        buttonsBox.setVisible(false);
    }



    public XYChart.Series<String, Number> getTemp_series() {
        return temp_series;
    }
    public XYChart.Series<String, Number> getWind_series() {
        return wind_series;
    }
    public XYChart.Series<String, Number> getArriving_passengers_series() {
        return arriving_passengers_series;
    }
    public XYChart.Series<String, Number> getDeparting_passengers_series() {
        return departing_passengers_series;
    }
    public XYChart.Series<String, Number> getArriving_flights_series() {
        return arriving_flights_series;
    }
    public XYChart.Series<String, Number> getDeparting_flights_series() {
        return departing_flights_series;
    }
    public XYChart.Series<String, Number> getClouds_series() {
        return clouds_series;
    }
    public XYChart.Series<String, Number> getRain_series() {
        return rain_series;
    }
    public XYChart.Series<String, Number> getSnow_series() {
        return snow_series;
    }
}
