package org.timgroup.dojo;

import fj.F;
import fj.F2;
import fj.data.Stream;

public class WeatherMunging {
    private final Stream<String> lines;

    public WeatherMunging(Stream<String> lines) {
        this.lines = lines;
    }

    public int dayWithTheSmallestTemperatureSpread() {
        
        Stream<String> linesWithData = lines.filter(dayLine());
        
        Stream<DayTemperature> dayTemperatures = linesWithData.map(toDayTemperatures());
        final DayTemperature minimumSpread = dayTemperatures.foldLeft1(minDifference());
        
        return minimumSpread.day;
    }

    public static F2<DayTemperature, DayTemperature, DayTemperature> minDifference() {
        return new F2<DayTemperature, DayTemperature, DayTemperature>() {
            @Override
            public DayTemperature f(DayTemperature a, DayTemperature b) {
                return DayTemperature.minSpread(a, b);
            } 
        };
    }
    
    public static final class DayTemperature {
        public final int day;
        public final int max;
        public final int min;
        
        public DayTemperature(int day, int max, int min) {
            this.day = day;
            this.max = max;
            this.min = min;
        }
        
        public int difference() {
            return max - min;
        }
        
        public static DayTemperature minSpread(DayTemperature first, DayTemperature second) {
            return first.difference() < second.difference() ? first : second;
        }
    }

    public static F<String, Boolean> dayLine() {
        return new F<String, Boolean>() {
            @Override
            public Boolean f(String a) {
                return a.trim().matches("\\d.*");
            }
        };
    }

    public static F<String, DayTemperature> toDayTemperatures() {
        return new F<String, DayTemperature>() {
            @Override
            public DayTemperature f(String input) {
                String[] values = input.trim().replaceAll("\\*", "").split(" +");
                int element1 = Integer.valueOf(values[0]);
                int element2 = Integer.valueOf(values[1]);
                int element3 = Integer.valueOf(values[2]);
                    
                return new DayTemperature(element1, element2, element3);
            }
        };
    }
}
