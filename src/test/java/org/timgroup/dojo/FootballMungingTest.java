package org.timgroup.dojo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;
import static org.timgroup.dojo.Files.readLinesFrom;

import org.junit.Ignore;
import org.junit.Test;
import org.timgroup.dojo.FootballMunging.LeagueResult;

import fj.data.Stream;

public class FootballMungingTest {

    @Test
    public void teamWithSmallestDifferenceBetweenForAndAgainstIsAstonVilla() {
        Stream<String> footballLines = readLinesFrom("football.dat");
        assertThat(new FootballMunging(footballLines).teamWithSmallestDifferenceBetweenForAndAgainst(), is("Aston_Villa"));
    }
    
    @Test
    public void leagueResultIsImmutable() throws Exception {
        assertInstancesOf(LeagueResult.class, areImmutable(), provided(String.class).isAlsoImmutable());
    }
    
    @Ignore
    @Test
    public void footballMungingIsImmutable() throws Exception {
        assertInstancesOf(FootballMunging.class, areImmutable(), provided(String.class).isAlsoImmutable());
    }
}
