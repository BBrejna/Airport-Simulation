package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import model.Simulation;
import model.classes.Observer;
import model.classes.simulation.Weather;

public class StatisticsViewController implements Observer<Weather> {
    @FXML
    private LineChart<String, Number> temperatureChart;
    private XYChart.Series<String, Number> series = new XYChart.Series<>();

    public void initialize() {
        Simulation.getInstance().addObserver(this);
        temperatureChart.getData().add(series);
    }

    @Override
    public void observerUpdateState(Weather weather) {
        Platform.runLater(() -> {
            String time = String.valueOf(Simulation.getInstance().getTime());
            Number temperature = weather.getTemperature();
            series.getData().add(new XYChart.Data<>(time, temperature));
        });
    }
}

