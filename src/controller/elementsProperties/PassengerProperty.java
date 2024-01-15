package controller.elementsProperties;

import javafx.scene.image.ImageView;
import model.classes.admin.Flight;
import model.tools.Tools;

public class PassengerProperty {


        private String name;
        private String surname;
        private String PESEL;
        private String flightClass;
        private String luggage;
        private ImageView deleteImage;

        public PassengerProperty(String name, String surname, String PESEL, String flightClass, String luggage,ImageView deleteImage) {
            this.name = name;
            this.surname = surname;
            this.PESEL = PESEL;
            this.flightClass = flightClass;
            this.luggage = luggage;
            this.deleteImage = deleteImage;
        }
        public PassengerProperty(String name, String surname, String PESEL, String flightClass, String luggage) {
            this.name = name;
            this.surname = surname;
            this.PESEL = PESEL;
            this.flightClass = flightClass;
            this.luggage = luggage;
        }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public String getLuggage() {
        return luggage;
    }

    public void setLuggage(String luggage) {
        this.luggage = luggage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ImageView getDeleteImage() {
        return deleteImage;
    }

    public void setDeleteImage(ImageView deleteImage) {
        this.deleteImage = deleteImage;
    }
}
