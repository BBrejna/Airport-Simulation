import controller.ControllersHandler;
import controller.MainViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setOnCloseRequest(e -> {
            e.consume();
            if(ControllersHandler.getInstance().getMainViewController().getCurrentContent().equals(ControllersHandler.getInstance().getMainViewController().getSimulationContent()) &&
                    !ControllersHandler.getInstance().getSimulationViewController().getPauseButton().isDisabled()) {
                ControllersHandler.getInstance().getSimulationViewController().handlePauseButtonClick();
                try {
                    if(((MainViewController)loader.getController()).handleCloseButtonClick()){
                        Platform.exit();
                        System.exit(0);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                Platform.exit();
                System.exit(0);
            }
        });
        stage.getIcons().add(new Image("/resources/icon.png"));
        stage.setTitle("Lotnisko");
        stage.setScene(scene);
        stage.show();
    }
}
