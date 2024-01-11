package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import model.Simulation;
import model.classes.Observer;
import model.classes.StatisticsObserver;
import model.classes.StatisticsSubject;
import model.classes.admin.Flight;
import model.classes.simulation.Weather;
import model.tools.Tools;

import java.util.ArrayList;

public class StatisticsViewController implements Observer<Weather>, StatisticsObserver {

    @FXML
    private LineChart<String, Number> temperatureChart;
    @FXML
    private LineChart<String, Number> windChart;
    @FXML
    private BarChart<String, Number> arrivingPassengersChart;
    @FXML
    private BarChart<String, Number> departingPassengersChart;
    @FXML
    private VBox passengersBox;
    private XYChart.Series<String, Number> temp_series = new XYChart.Series<>();
    private XYChart.Series<String, Number> wind_series = new XYChart.Series<>();
    private XYChart.Series<String, Number> arriving_passengers_series = new XYChart.Series<>();
    private XYChart.Series<String, Number> departing_passengers_series = new XYChart.Series<>();

    public void initialize() {
        ControllersHandler.getInstance().setStatisticsViewController(this);
        Simulation.getInstance().addObserver(this);
        StatisticsSubject.getInstance().addObserver(this);
        temperatureChart.getData().add(temp_series);
        windChart.getData().add(wind_series);
        arrivingPassengersChart.getData().add(arriving_passengers_series);
        departingPassengersChart.getData().add(departing_passengers_series);
    }

    @Override
    public void observerUpdateState(Weather weather) {
        Platform.runLater(() -> {
            String time = Tools.convertMinutesToTime(Simulation.getInstance().getTime());
            Number temperature = weather.getTemperature();
            temp_series.getData().add(new XYChart.Data<>(time, temperature));
            Number wind = weather.getWind();
            wind_series.getData().add(new XYChart.Data<>(time, wind));
        });
    }

    @Override
    public void observerUpdateState(ArrayList<Flight> flights) {
        Platform.runLater(() ->{
            String time = Tools.convertMinutesToTime(Simulation.getInstance().getTime());
            int arriving_passengers = 0;
            int departing_passengers = 0;

            for(Flight flight: flights) {
                if(flight.isArrival()){
                    arriving_passengers += flight.getNumOfOccupiedSeats()[0] + flight.getNumOfOccupiedSeats()[1] +
                            flight.getNumOfOccupiedSeats()[2];
                }
                else{
                    departing_passengers += flight.getNumOfOccupiedSeats()[0] + flight.getNumOfOccupiedSeats()[1] +
                            flight.getNumOfOccupiedSeats()[2];
                }
            }
            Number arrivingPassengers = arriving_passengers;
            Number departingPassengers = departing_passengers;

            arriving_passengers_series.getData().add(new XYChart.Data<>(time, arrivingPassengers));
            departing_passengers_series.getData().add(new XYChart.Data<>(time, departingPassengers));
        });
    }


    @FXML
    private void temperatureButtonClicked() {
        passengersBox.setVisible(false);
        temperatureChart.setVisible(true);
        windChart.setVisible(false);
    }

    @FXML
    private void windButtonClicked() {
        passengersBox.setVisible(false);
        temperatureChart.setVisible(false);
        windChart.setVisible(true);
    }
    @FXML
    private void passengersButtonClicked(){
        temperatureChart.setVisible(false);
        windChart.setVisible(false);
        passengersBox.setVisible(true);
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
}
