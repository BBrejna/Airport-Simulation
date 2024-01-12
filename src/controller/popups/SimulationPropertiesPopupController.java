package controller.popups;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Simulation;
import model.classes.simulation.Weather;

import java.util.ArrayList;
import java.util.Objects;


public class SimulationPropertiesPopupController {
    @FXML
    private Slider slider;
    @FXML
    private TextField textFieldTemperature;
    @FXML
    private TextField textFieldWind;
    @FXML
    private TextField textFieldClouds;
    @FXML
    private TextField textFieldRain;
    @FXML
    private TextField textFieldSnow;
    @FXML
    private TextField textFieldFog;
    private Stage stage;
    private int oldTimeDeltaValue;


    public void display(Stage stage, Parent root, int value, int maxValue) {
        this.stage = stage;
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Simulation Settings");

        stage.setOnCloseRequest(e -> {
            e.consume();
            onCancelButtonClicked();
        });

        value = Math.max(1, Math.min(maxValue, value));
        oldTimeDeltaValue = value;
        this.slider.setValue(value);
        this.slider.setMax(maxValue);


        // Set up the scene and show the popup
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/resources/icon.png"));
        stage.showAndWait();
    }

    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }
    @FXML
    private void onOkButtonClicked() {
        ArrayList<String> exceptions = new ArrayList<>();
        if((!Objects.equals(textFieldTemperature.getText(), "")) && ((Integer.parseInt(textFieldTemperature.getText().trim()) > 40 ) || (Integer.parseInt(textFieldTemperature.getText().trim()) < -20))){
            exceptions.add("Temperature value range: -20 - 40");
        }
        if((!Objects.equals(textFieldWind.getText(), "")) && ((Integer.parseInt(textFieldWind.getText().trim()) > 100) || (Integer.parseInt(textFieldWind.getText().trim()) < 0))){
            exceptions.add("Wind value range: 0 - 100");
        }
        if((!Objects.equals(textFieldRain.getText(), "")) && ((Integer.parseInt(textFieldRain.getText().trim()) > 50) || (Integer.parseInt(textFieldRain.getText().trim()) < 0))){
            exceptions.add("Rain value range: 0 - 50");
        }
        if((!Objects.equals(textFieldSnow.getText(), "")) && ((Integer.parseInt(textFieldSnow.getText().trim()) > 10) || (Integer.parseInt(textFieldSnow.getText().trim()) < 0))){
            exceptions.add("Snow value range: 0 - 10");
        }
        if((!Objects.equals(textFieldClouds.getText(), "")) && ((Integer.parseInt(textFieldClouds.getText().trim()) > 5) || (Integer.parseInt(textFieldClouds.getText().trim()) < 1))){
            exceptions.add("Clouds value range: 1 - 5");
        }
        if((!Objects.equals(textFieldFog.getText(), "")) && ((Integer.parseInt(textFieldFog.getText().trim()) > 5) || (Integer.parseInt(textFieldFog.getText().trim()) < 1))){
            exceptions.add("Fog value range: 1 - 5");
        }

        try {
            checkExceptions(exceptions);
        } catch (Weather.ValuesOutOfRangeException e) {
            System.out.println("CAUGHT WEATHER VALUES OUT OF RANGE ERROR: "+e);
        }
    }
    public void checkExceptions(ArrayList<String> exceptions) throws Weather.ValuesOutOfRangeException {
        if(!exceptions.isEmpty()){
            throw new Weather.ValuesOutOfRangeException(exceptions);
        }
        else{
            closeWindow();
        }
    }
    @FXML
    private void onCancelButtonClicked() {
        slider.setValue(oldTimeDeltaValue);
        closeWindow();

    }

    public int getSelectedValue() {
        // Return the selected value to the main controller
        return (int)slider.getValue();
    }

    @FXML
    private void initialize() {
        textFieldTemperature.setPromptText(String.valueOf(Simulation.getInstance().getWeather().getTemperature()));
        textFieldWind.setPromptText(String.valueOf(Simulation.getInstance().getWeather().getWind()));
        textFieldRain.setPromptText(String.valueOf(Simulation.getInstance().getWeather().getRain()));
        textFieldSnow.setPromptText(String.valueOf(Simulation.getInstance().getWeather().getSnow()));
        textFieldClouds.setPromptText(String.valueOf(Simulation.getInstance().getWeather().getClouds()));
        textFieldFog.setPromptText(String.valueOf(Simulation.getInstance().getWeather().getFog()));
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
    }

    public int getTemperatureValue() throws NumberFormatException   {
        return Integer.parseInt(textFieldTemperature.getText().trim());
    }
    public int getWindValue() throws NumberFormatException {
        return Integer.parseInt(textFieldWind.getText().trim());
    }
    public int getCloudsValue() throws NumberFormatException{
        return Integer.parseInt(textFieldClouds.getText().trim());
    }
    public int getRainValue() throws NumberFormatException{
        return Integer.parseInt(textFieldRain.getText().trim());
    }
    public int getSnowValue() throws NumberFormatException{
        return Integer.parseInt(textFieldSnow.getText().trim());
    }
    public int getFogValue() throws NumberFormatException{
        return Integer.parseInt(textFieldFog.getText().trim());
    }

}
