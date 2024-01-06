package model.tools;

public class Tools {

    // POTRZEBUJE INFORMACJI W JAKICH JEDNOSTKACH GENEROWANY JEST CZAS, PRZUJALEM ZE GENERUJEMY GO W MINUTACH
    // To dobrze przyjales XD
    public static String convertMinutesToTime(int minutes) {
        minutes = (minutes+1440)%1440;
        if (minutes < 0) {
            return "Nieprawidłowa liczba minut";
        }

        int hours = minutes / 60;
        int mins = minutes % 60;

        String hoursStr = (hours < 10) ? "0" + hours : String.valueOf(hours);
        String minsStr = (mins < 10) ? "0" + mins : String.valueOf(mins);

        return hoursStr + ":" + minsStr;
    }
}
