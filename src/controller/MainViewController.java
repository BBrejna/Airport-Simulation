package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Simulation;

import java.io.IOException;
import java.util.ArrayList;

public class MainViewController {
    public Button simulationButton;
    public Button adminButton;
    public Button salesmanButton;
    public Button workmanButton;
    public Parent simulationContent;
    public Parent adminContent;
    public Parent salesmanContent;
    public Parent workmanContent;
    private Button disabledButton;
    private Parent currentContent;


    public Parent getCurrentContent() {
        return currentContent;
    }

    private void changeView(Button newButton, Parent newContent) {
        disabledButton.setDisable(false);
        disabledButton = newButton;
        disabledButton.setDisable(true);

        currentContent.setVisible(false);
        currentContent.setManaged(false);
        currentContent = newContent;
        currentContent.setVisible(true);
        currentContent.setManaged(true);
    }
    public void lockButtonsOnSimulationRunning(boolean value) {
        adminButton.setDisable(value);
        salesmanButton.setDisable(value);
        workmanButton.setDisable(value);
    }

    public void showSimulationContent(ActionEvent actionEvent) {
        changeView(simulationButton, simulationContent);
    }

    public void showAdminContent(ActionEvent actionEvent) {
        if (Simulation.getInstance().isTimeStopped() || !Simulation.getInstance().isSimulationStarted()) {
            changeView(adminButton, adminContent);
        } else {
            System.out.println("Cannot change to Admin content");
        }
    }

    public void showSalesmanContent(ActionEvent actionEvent) {
        changeView(salesmanButton, salesmanContent);
    }

    public void showWorkmanContent(ActionEvent actionEvent) {
        changeView(workmanButton, workmanContent);
    }

    public void initialize() throws IOException {
        ControllersHandler.getInstance().setMainViewController(this);
        disabledButton = simulationButton;
        currentContent = simulationContent;
    }
    public boolean handleCloseButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CloseConfirmPopup.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();

        CloseConfirmPopupController popupController = loader.getController();

        return (popupController.displayPopup(popupStage, root));

    }
}
