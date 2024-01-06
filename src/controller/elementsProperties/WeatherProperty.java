package controller.elementsProperties;

public class WeatherProperty {
    private String property;
    private String value;

    public WeatherProperty(String property, double value) {
        this.property = property;
        this.value = String.format("%.3f", value);
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }
}