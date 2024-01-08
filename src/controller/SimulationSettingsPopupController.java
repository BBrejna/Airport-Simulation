package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.classes.simulation.Weather;

import java.util.ArrayList;
import java.util.Objects;


public class SimulationSettingsPopupController {

    public Label valueLabel;
    @FXML
    private Slider slider;

    private Stage stage;

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
    private int oldTimeDeltaValue;


    public void display(Stage stage, Parent root, int value, int maxValue) {
        this.stage = stage;
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("SIM settings");

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
        stage.showAndWait();
    }

    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }
    @FXML
    private void onOkButtonClicked() throws Weather.ValuesOutOfRangeException {
        ArrayList<String> exceptions = new ArrayList<>();
        if((!Objects.equals(textFieldTemperature.getText(), "")) && (Integer.parseInt(textFieldTemperature.getText().trim()) > 40)){
            exceptions.add("Temperature value range: -20 - 40");
        }
        if((!Objects.equals(textFieldWind.getText(), "")) && (Integer.parseInt(textFieldWind.getText().trim()) > 100)){
            exceptions.add("Wind value range: 0 - 100");
        }
        if((!Objects.equals(textFieldRain.getText(), "")) && (Integer.parseInt(textFieldRain.getText().trim()) > 50)){
            exceptions.add("Rain value range: 0 - 50");
        }
        if((!Objects.equals(textFieldSnow.getText(), "")) && (Integer.parseInt(textFieldSnow.getText().trim()) > 10)){
            exceptions.add("Snow value range: 0 - 10");
        }
        if((!Objects.equals(textFieldClouds.getText(), "")) && (Integer.parseInt(textFieldClouds.getText().trim()) > 5)){
            exceptions.add("Clouds value range: 1 - 5");
        }
        if((!Objects.equals(textFieldFog.getText(), "")) && (Integer.parseInt(textFieldFog.getText().trim()) > 5)){
            exceptions.add("Fog value range: 1 - 5");
        }
        checkExceptions(exceptions);
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
