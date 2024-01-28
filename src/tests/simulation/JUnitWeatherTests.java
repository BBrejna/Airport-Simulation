package tests.simulation;

import model.classes.simulation.Weather;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnitWeatherTests {
    Weather weather = new Weather();
    @RepeatedTest(10000)
    public void generateRainDeltaTest(){
        assertEquals(0, weather.generateRainDelta(10, 2));
        assertTrue(weather.generateRainDelta(50,2)<-1);
        assertTrue(weather.generateRainDelta(50,2)>=-2);
        assertTrue(weather.generateRainDelta(0,0)<-1);
        assertTrue(weather.generateRainDelta(0,0)>=-2);
        assertTrue(weather.generateRainDelta(0,5)<0.7);
        assertTrue(weather.generateRainDelta(0,5)>=-0.3);
        assertTrue(weather.generateRainDelta(0,2.5)<0.5);
        assertTrue(weather.generateRainDelta(0,2.5)>=-0.5);
        assertTrue(weather.generateRainDelta(-20,2.5)<-0.5);
        assertTrue(weather.generateRainDelta(-20,2.5)>=-1);
    }
}
