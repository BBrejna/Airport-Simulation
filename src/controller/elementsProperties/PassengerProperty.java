package controller.elementsProperties;

import model.classes.admin.Flight;
import model.tools.Tools;

public class PassengerProperty {


        private String name;
        private String surname;
        private String PESEL;
        private String Class;
        private String luggage;

        public PassengerProperty(String name, String surname, String PESEL, String Class, String luggage) {
            this.name = name;
            this.surname = surname;
            this.PESEL = PESEL;
            this.Class = Class;
            this.luggage = luggage;
        }

    }
