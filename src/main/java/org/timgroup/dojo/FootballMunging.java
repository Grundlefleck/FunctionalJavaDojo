package org.timgroup.dojo;

import fj.F;
import fj.F2;
import fj.data.Stream;

public final class FootballMunging {

    private final Stream<String> lines;

    public FootballMunging(Stream<String> lines) {
        this.lines = lines;
    }

    public String teamWithSmallestDifferenceBetweenForAndAgainst() {
        Stream<String> linesWithData = lines.filter(isLeagueResult());
        
        Stream<LeagueResult> leagueResults = linesWithData.map(toLeagueResult());
        final LeagueResult result = leagueResults.foldLeft1(minAbsoluteDifference());
        return result.team;
    }
    
    public static F2<LeagueResult, LeagueResult, LeagueResult> minAbsoluteDifference() {
        return new F2<LeagueResult, LeagueResult, LeagueResult>() {
            @Override public LeagueResult f(LeagueResult a, LeagueResult b) { return LeagueResult.minSpread(a, b); }
        };
    }

    public static F<String, Boolean> isLeagueResult() {
        return new F<String, Boolean>() {
            @Override public Boolean f(String a) { return a.trim().matches("\\d.*"); }
        };
    }

    public static F<String, LeagueResult> toLeagueResult() {
        return new F<String, LeagueResult>() {
            @Override public LeagueResult f(String input) {
                String[] values = input.trim().split(" +");
                return new LeagueResult(values[1], Integer.valueOf(values[6]), Integer.valueOf(values[8]));
            }
        };
    }
    
    public static final class LeagueResult {
        public final String team;
        public final int goalsFor;
        public final int goalsAgainst;
        
        public LeagueResult(String team, int goalsFor, int goalsAgainst) {
            this.team = team;
            this.goalsFor = goalsFor;
            this.goalsAgainst = goalsAgainst;
        }

        public int difference() {
            return Math.abs(goalsFor - goalsAgainst);
        }
        
        public static LeagueResult minSpread(LeagueResult first, LeagueResult second) {
            return first.difference() < second.difference() ? first : second;
        }
    }
}
