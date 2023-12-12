package data.admin;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FlightHours {

    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(random.nextGaussian()*15);
    }

    public final int[] hours = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
    public final int[] flightDistribution = {};

    public Map<Integer, Range> getHourRanges() {

        Map<Integer, Range> flightHourFrequency = new HashMap<Integer, Range>();


        flightHourFrequency.put(0, new Range(0, 12));

        Random random = new Random();
        random.nextGaussian();

        return null;

    }



}

