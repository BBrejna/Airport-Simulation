package controller.popups;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteFlightPopupController {

    private Stage stage;
    private String flightNumber = "";
    private boolean answer;

    public boolean displayPopup(Stage stage, Parent root) {
        this.flightNumber = flightNumber;
        this.stage = stage;
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setOnCloseRequest(e -> {
            e.consume();
            cancel();
        });

        stage.setScene(new Scene(root));
        stage.setTitle("Delete flight");
        stage.getIcons().add(new Image("/resources/icon.png"));
        stage.showAndWait();

        return answer;

    }

    public void deleteFlight() {
        answer = true;
        closeWindow();
    }

    public void cancel() {
        answer = false;
        closeWindow();
    }

    private void closeWindow() {
        if(stage != null) {
            stage.close();
        }
    }

}
