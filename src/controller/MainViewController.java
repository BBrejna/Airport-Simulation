package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import model.Simulation;

public class MainViewController {
    @FXML
    private ListView arrivalsList;
    @FXML
    private ListView departuresList;
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
    private ListView simulationLogsList;

    public void initialize() {
        currentTimeLabel.setText(String.valueOf(Simulation.getInstance().getTime()));
    }
}
