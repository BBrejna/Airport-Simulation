package model.classes.people;

import data.NamesAndSurnames;
import model.Salesman;

import java.util.Random;

public class Person {

    private String pesel;
    private String name;
    private String surname;

    public Person(String name, String surname) {
        this.pesel = Salesman.generatePESEL();
        this.name = name;
        this.surname = surname;
    }

    public Person() {

        Random random = new Random();

        this.pesel = generatePESEL();
        this.name = NamesAndSurnames.NAMES[random.nextInt(NamesAndSurnames.NAMES.length)];
        this.surname = NamesAndSurnames.SURNAMES[random.nextInt(NamesAndSurnames.SURNAMES.length)];

    }

    public static String generatePESEL() {
        Random rand = new Random();

        // Generowanie roku i miesiąca urodzenia
        int year = 1900 + rand.nextInt(125); // Losowy rok od 1900 do 2024
        int month = 1 + rand.nextInt(12);

        // Dostosowanie miesiąca dla różnych stuleci
        if (year > 1999) {
            month += 20; // Dodanie 20 do miesiąca dla lat 2000-2024
        }

        // Ustalenie liczby dni w miesiącu
        int daysInMonth = getDaysInMonth(month % 20, year);

        // Generowanie dnia urodzenia
        int day = 1 + rand.nextInt(daysInMonth);

        // Generowanie numeru dla płci (kobieta: parzysta, mężczyzna: nieparzysta)
        int genderNumber = rand.nextInt(10);

        // Generowanie pozostałych losowych cyfr
        int randomDigits = 100 + rand.nextInt(900);

        // Budowanie częściowej sekwencji PESEL bez cyfry kontrolnej
        String partialPESEL =
                (year % 100 < 10 ? "0" : "") + year % 100 +
                        (month < 10 ? "0" : "") + month +
                        (day < 10 ? "0" : "") + day +
                        genderNumber +
                        randomDigits;

        // Obliczanie cyfry kontrolnej
        int controlNumber = calculateControlNumber(partialPESEL);

        // Zwracanie pełnego numeru PESEL
        return partialPESEL + controlNumber;
    }

    private static int getDaysInMonth(int month, int year) {
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                // Sprawdzenie roku przestępnego
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return 31;
        }
    }

    private static int calculateControlNumber(String partialPESEL) {
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;

        for (int i = 0; i < partialPESEL.length(); i++) {
            sum += Character.getNumericValue(partialPESEL.charAt(i)) * weights[i];
        }

        int controlDigit = 10 - (sum % 10);
        return controlDigit == 10 ? 0 : controlDigit;
    }

    /** GETTERS AND SETTERS */
    public String getPesel() {return pesel;}
    public void setPesel(String pesel) {this.pesel = pesel;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getSurname() {return surname;}
    public void setSurname(String surname) {this.surname = surname;}

}
