package controller;

import controller.popups.CloseConfirmPopupController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController {
    @FXML
    private Button simulationButton;
    @FXML
    private Button adminButton;
    @FXML
    private Button salesmanButton;
    @FXML
    private Button workmanButton;
    @FXML
    private Parent simulationContent;
    @FXML
    private Parent adminContent;
    @FXML
    private Parent salesmanContent;
    @FXML
    private Parent workmanContent;
    private Button disabledButton;
    private Parent currentContent;

    public Parent getSimulationContent() {
        return simulationContent;
    }
    public Parent getCurrentContent() {
        return currentContent;
    }

    private void changeView(Button newButton, Parent newContent) {
        disabledButton.setDisable(false);
        disabledButton.getStyleClass().clear();
        disabledButton.getStyleClass().add("RoleButton");
        disabledButton = newButton;
        disabledButton.setDisable(true);
        disabledButton.getStyleClass().add("activeButton");

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

    public void showSimulationContent() {
        changeView(simulationButton, simulationContent);
    }

    public void showAdminContent() {
        changeView(adminButton, adminContent);
    }

    public void showSalesmanContent() {
        changeView(salesmanButton, salesmanContent);
    }

    public void showWorkmanContent() {
        changeView(workmanButton, workmanContent);
    }

    public void initialize() {
        ControllersHandler.getInstance().setMainViewController(this);
        disabledButton = simulationButton;
        disabledButton.getStyleClass().add("activeButton");
        currentContent = simulationContent;
    }
    public boolean handleCloseButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/popups/CloseConfirmPopup.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();

        CloseConfirmPopupController popupController = loader.getController();

        return (popupController.displayPopup(popupStage, root));

    }
}
