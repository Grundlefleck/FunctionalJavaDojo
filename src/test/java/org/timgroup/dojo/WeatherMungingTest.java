package org.timgroup.dojo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;
import static org.timgroup.dojo.Files.readLinesFrom;
import static org.timgroup.dojo.WeatherMunging.minDifference;

import org.junit.Test;
import org.timgroup.dojo.WeatherMunging.DayTemperature;

import fj.data.Stream;

public class WeatherMungingTest {

    
    @Test
    public void filtersOutLinesWhichDontContainRelevantData() throws Exception {
        Stream<String> lines = Stream.stream("not me", "08 pick me", "   9 with spaces", "    ");
        assertThat(lines.filter(WeatherMunging.dayLine()), contains( "08 pick me", "   9 with spaces"));
    }
    
    
    @Test
    public void transformsLineIntoDayTemperature() throws Exception {
        String line = "    1  88    59    74          53.8       0.00 F       280  9.6 270  17  1.6  93 23 1004.5";
        
        DayTemperature day = WeatherMunging.toDayTemperatures().f(line);
        
        assertThat(day.day, is(1));
        assertThat(day.max, is(88));
        assertThat(day.min, is(59));
    }
    
    @Test
    public void extractsMinDifference() throws Exception {
        Stream<DayTemperature> temperatures = Stream.stream(new DayTemperature(1, 34, 17), new DayTemperature(2, 33, 17));
        
        assertThat(temperatures.foldLeft1(minDifference()).day, is(2));
    }
    
    @Test
    public void weatherIsFine() {
        Stream<String> weatherLines = readLinesFrom("weather.dat");
        assertThat(new WeatherMunging(weatherLines).dayWithTheSmallestTemperatureSpread(), is(14));
    }
    
    @Test
    public void isImmutable() throws Exception {
        assertInstancesOf(DayTemperature.class, areImmutable());
    }
}
