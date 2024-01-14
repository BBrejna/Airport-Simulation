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

    public static int convertTimeToMinutes(String time) {
        int minutes = 0;
        minutes += Integer.parseInt(time.substring(0,2)) * 60;
        minutes += Integer.parseInt(time.substring(3,5));
        if(minutes < 0 || minutes >= 1440) return -1;
        return minutes;
    }

}
