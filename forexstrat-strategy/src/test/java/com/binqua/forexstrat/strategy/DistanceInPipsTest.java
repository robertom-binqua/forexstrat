package com.binqua.forexstrat.strategy;

import org.junit.Test;

import static com.binqua.forexstrat.strategy.DistanceInPips.aDistanceInPipsOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DistanceInPipsTest {
    @Test
    public void isMultipleOf() {
        assertThat("distance should be multiple", aDistanceInPipsOf(10).isMultipleOf(2), is(true));
        assertThat("distance should be multiple", aDistanceInPipsOf(10).isMultipleOf(5), is(true));
        assertThat("distance should be multiple", aDistanceInPipsOf(20).isMultipleOf(1), is(true));
        assertThat("distance should be multiple", aDistanceInPipsOf(20).isMultipleOf(10), is(true));
        assertThat("distance should be multiple", aDistanceInPipsOf(10).isMultipleOf(3), is(false));
        assertThat("distance should be multiple", aDistanceInPipsOf(10).isMultipleOf(4), is(false));
    }
}
