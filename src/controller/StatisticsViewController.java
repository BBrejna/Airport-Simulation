package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import model.Simulation;
import model.classes.Observer;
import model.classes.simulation.Weather;
import model.tools.Tools;

public class StatisticsViewController implements Observer<Weather> {
    @FXML
    private LineChart<String, Number> temperatureChart;
    @FXML
    private LineChart<String, Number> windChart;
    private XYChart.Series<String, Number> temp_series = new XYChart.Series<>();
    private XYChart.Series<String, Number> wind_series = new XYChart.Series<>();

    public void initialize() {
        Simulation.getInstance().addObserver(this);
        temperatureChart.getData().add(temp_series);
        windChart.getData().add(wind_series);
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
}

