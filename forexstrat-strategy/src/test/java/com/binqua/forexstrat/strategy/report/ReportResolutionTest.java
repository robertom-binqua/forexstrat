package com.binqua.forexstrat.strategy.report;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReportResolutionTest {

    @Test
    public void resolutionFrom() {
        assertThat(ReportResolution.resolutionFrom("1"), is(ReportResolution.ONE));
    }

    @Test
    public void displayValues() {
        assertThat(ReportResolution.displayValues(), is(new String[]{"1", "2", "5", "10", "25"}));
    }
}
