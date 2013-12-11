package com.binqua.forexstrat.feedreader.core.model.impl;

import org.joda.time.DateTime;
import org.junit.Test;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCandlePeriodicity.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EnumCandlePeriodicityTest {

    @Test
    public void intervalIdentifierInCaseOfM1() {
        assertThat("getIntervalIdentifier", M1.getIntervalIdentifier(new DateTime("2004-12-13T20:59:59.000Z")), is(new DateTime("2004-12-13T20:59:00.000Z")));
        assertThat("getIntervalIdentifier", M1.getIntervalIdentifier(new DateTime("2004-12-13T21:00:00.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat("getIntervalIdentifier", M1.getIntervalIdentifier(new DateTime("2004-12-13T21:00:59.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat("getIntervalIdentifier", M1.getIntervalIdentifier(new DateTime("2004-12-13T21:01:00.001Z")), is(new DateTime("2004-12-13T21:01:00.000Z")));
    }

    @Test
    public void nextPeriodInCaseOfM1() {
        assertThat(M1.nextPeriod(new DateTime("2004-12-13T21:00:00.001Z")), is(new DateTime("2004-12-13T21:01:00.000Z")));
        assertThat(M1.nextPeriod(new DateTime("2004-12-13T21:01:59.000Z")), is(new DateTime("2004-12-13T21:02:00.000Z")));
    }

    @Test
    public void intervalIdentifierInCaseOfM2() {
        assertThat("getIntervalIdentifier", M2.getIntervalIdentifier(new DateTime("2004-12-13T20:59:59.000Z")), is(new DateTime("2004-12-13T20:58:00.000Z")));
        assertThat("getIntervalIdentifier", M2.getIntervalIdentifier(new DateTime("2004-12-13T21:00:00.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat("getIntervalIdentifier", M2.getIntervalIdentifier(new DateTime("2004-12-13T21:01:59.001Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat("getIntervalIdentifier", M2.getIntervalIdentifier(new DateTime("2004-12-13T21:02:00.001Z")), is(new DateTime("2004-12-13T21:02:00.000Z")));
    }

    @Test
    public void nextPeriodInCaseOfM2() {
        assertThat(M2.nextPeriod(new DateTime("2004-12-13T21:00:00.001Z")), is(new DateTime("2004-12-13T21:02:00.000Z")));
        assertThat(M2.nextPeriod(new DateTime("2004-12-13T21:01:59.000Z")), is(new DateTime("2004-12-13T21:02:00.000Z")));
        assertThat(M2.nextPeriod(new DateTime("2004-12-13T21:02:00.001Z")), is(new DateTime("2004-12-13T21:04:00.000Z")));
    }

    @Test
    public void intervalIdentifierInCaseOfM5() {
        assertThat("getIntervalIdentifier", M5.getIntervalIdentifier(new DateTime("2004-12-13T20:59:59.000Z")), is(new DateTime("2004-12-13T20:55:00.000Z")));
        assertThat("getIntervalIdentifier", M5.getIntervalIdentifier(new DateTime("2004-12-13T21:00:00.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat("getIntervalIdentifier", M5.getIntervalIdentifier(new DateTime("2004-12-13T21:00:00.001Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat("getIntervalIdentifier", M5.getIntervalIdentifier(new DateTime("2004-12-13T21:05:01.000Z")), is(new DateTime("2004-12-13T21:05:00.000Z")));
    }

    @Test
    public void nextPeriodInCaseOfM5() {
        assertThat(M5.nextPeriod(new DateTime("2004-12-13T21:00:00.001Z")), is(new DateTime("2004-12-13T21:05:00.000Z")));
        assertThat(M5.nextPeriod(new DateTime("2004-12-13T21:05:01.000Z")), is(new DateTime("2004-12-13T21:10:00.000Z")));
    }

    @Test
    public void intervalIdentifierInCaseOfM15() {
        assertThat(M15.getIntervalIdentifier(new DateTime("2004-12-13T21:00:00.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat(M15.getIntervalIdentifier(new DateTime("2004-12-13T21:01:00.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat(M15.getIntervalIdentifier(new DateTime("2004-12-13T21:14:59.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));

        assertThat(M15.getIntervalIdentifier(new DateTime("2004-12-13T21:15:01.000Z")), is(new DateTime("2004-12-13T21:15:00.000Z")));
        assertThat(M15.getIntervalIdentifier(new DateTime("2004-12-13T21:29:29.999Z")), is(new DateTime("2004-12-13T21:15:00.000Z")));

        assertThat(M15.getIntervalIdentifier(new DateTime("2004-12-13T21:44:29.999Z")), is(new DateTime("2004-12-13T21:30:00.000Z")));

        assertThat(M15.getIntervalIdentifier(new DateTime("2004-12-13T21:45:00.000Z")), is(new DateTime("2004-12-13T21:45:00.000Z")));
        assertThat(M15.getIntervalIdentifier(new DateTime("2004-12-13T21:46:29.999Z")), is(new DateTime("2004-12-13T21:45:00.000Z")));
    }

    @Test
    public void nextPeriodInCaseOfM15() {
        assertThat(M15.nextPeriod(new DateTime("2004-12-13T21:01:00.100Z")), is(new DateTime("2004-12-13T21:15:00.000Z")));
    }

    @Test
    public void intervalIdentifierInCaseOfM30() {
        assertThat(M30.getIntervalIdentifier(new DateTime("2004-12-13T21:00:00.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat(M30.getIntervalIdentifier(new DateTime("2004-12-13T21:01:00.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat(M30.getIntervalIdentifier(new DateTime("2004-12-13T21:29:59.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));

        assertThat(M30.getIntervalIdentifier(new DateTime("2004-12-13T21:30:00.001Z")), is(new DateTime("2004-12-13T21:30:00.000Z")));
        assertThat(M30.getIntervalIdentifier(new DateTime("2004-12-13T21:59:29.999Z")), is(new DateTime("2004-12-13T21:30:00.000Z")));
        assertThat(M30.getIntervalIdentifier(new DateTime("2004-12-13T21:59:59.999Z")), is(new DateTime("2004-12-13T21:30:00.000Z")));
    }

    @Test
    public void nextPeriodInCaseOfM30() {
        assertThat(M30.nextPeriod(new DateTime("2004-12-13T21:01:00.100Z")), is(new DateTime("2004-12-13T21:30:00.000Z")));
        assertThat(M30.nextPeriod(new DateTime("2004-12-13T21:31:00.100Z")), is(new DateTime("2004-12-13T22:00:00.000Z")));
        assertThat(M30.nextPeriod(new DateTime("2004-12-13T23:30:00.001Z")), is(new DateTime("2004-12-14T00:00:00.000Z")));
    }

    @Test
    public void intervalIdentifierInCaseOfH1() {
        assertThat(H1.getIntervalIdentifier(new DateTime("2004-12-13T21:00:00.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat(H1.getIntervalIdentifier(new DateTime("2004-12-13T21:01:00.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat(H1.getIntervalIdentifier(new DateTime("2004-12-13T21:29:59.000Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat(H1.getIntervalIdentifier(new DateTime("2004-12-13T21:30:00.001Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));
        assertThat(H1.getIntervalIdentifier(new DateTime("2004-12-13T21:59:29.999Z")), is(new DateTime("2004-12-13T21:00:00.000Z")));

        assertThat(H1.getIntervalIdentifier(new DateTime("2004-12-13T22:00:00.000Z")), is(new DateTime("2004-12-13T22:00:00.000Z")));
        assertThat(H1.getIntervalIdentifier(new DateTime("2004-12-13T22:00:00.001Z")), is(new DateTime("2004-12-13T22:00:00.000Z")));
    }

    @Test
    public void nextPeriodInCaseOfH1() {
        assertThat(H1.nextPeriod(new DateTime("2004-12-13T21:01:00.100Z")), is(new DateTime("2004-12-13T22:00:00.000Z")));
        assertThat(H1.nextPeriod(new DateTime("2004-12-13T22:00:00.001Z")), is(new DateTime("2004-12-13T23:00:00.000Z")));
        assertThat(H1.nextPeriod(new DateTime("2004-12-13T23:00:00.001Z")), is(new DateTime("2004-12-14T00:00:00.000Z")));
    }

    @Test
    public void intervalIdentifierInCaseOfH2() {
        assertThat(H2.getIntervalIdentifier(new DateTime("2004-12-13T20:00:00.000Z")), is(new DateTime("2004-12-13T20:00:00.000Z")));
        assertThat(H2.getIntervalIdentifier(new DateTime("2004-12-13T20:01:00.000Z")), is(new DateTime("2004-12-13T20:00:00.000Z")));
        assertThat(H2.getIntervalIdentifier(new DateTime("2004-12-13T20:29:59.000Z")), is(new DateTime("2004-12-13T20:00:00.000Z")));
        assertThat(H2.getIntervalIdentifier(new DateTime("2004-12-13T20:59:29.999Z")), is(new DateTime("2004-12-13T20:00:00.000Z")));

        assertThat(H2.getIntervalIdentifier(new DateTime("2004-12-13T21:00:00.000Z")), is(new DateTime("2004-12-13T20:00:00.000Z")));
        assertThat(H2.getIntervalIdentifier(new DateTime("2004-12-13T21:01:00.000Z")), is(new DateTime("2004-12-13T20:00:00.000Z")));
        assertThat(H2.getIntervalIdentifier(new DateTime("2004-12-13T21:29:59.000Z")), is(new DateTime("2004-12-13T20:00:00.000Z")));
        assertThat(H2.getIntervalIdentifier(new DateTime("2004-12-13T21:59:29.999Z")), is(new DateTime("2004-12-13T20:00:00.000Z")));

        assertThat(H2.getIntervalIdentifier(new DateTime("2004-12-13T22:00:00.000Z")), is(new DateTime("2004-12-13T22:00:00.000Z")));
        assertThat(H2.getIntervalIdentifier(new DateTime("2004-12-13T22:00:00.001Z")), is(new DateTime("2004-12-13T22:00:00.000Z")));
    }

    @Test
    public void nextPeriodInCaseOfH2() {
        assertThat(H2.nextPeriod(new DateTime("2004-12-13T20:01:00.100Z")), is(new DateTime("2004-12-13T22:00:00.000Z")));
        assertThat(H2.nextPeriod(new DateTime("2004-12-13T22:00:00.001Z")), is(new DateTime("2004-12-14T00:00:00.000Z")));
    }

    @Test
    public void intervalIdentifierInCaseOfH4() {
        assertThat(H4.getIntervalIdentifier(new DateTime("2004-12-13T21:00:00.000Z")), is(new DateTime("2004-12-13T20:00:00.000Z")));
        assertThat(H4.getIntervalIdentifier(new DateTime("2004-12-13T22:01:00.000Z")), is(new DateTime("2004-12-13T20:00:00.000Z")));
        assertThat(H4.getIntervalIdentifier(new DateTime("2004-12-13T23:29:59.000Z")), is(new DateTime("2004-12-13T20:00:00.000Z")));
        assertThat(H4.getIntervalIdentifier(new DateTime("2004-12-14T00:00:00.000Z")), is(new DateTime("2004-12-14T00:00:00.000Z")));
        assertThat(H4.getIntervalIdentifier(new DateTime("2004-12-14T00:00:00.001Z")), is(new DateTime("2004-12-14T00:00:00.000Z")));
    }

    @Test
    public void nextPeriodInCaseOfH4() {
        assertThat(H4.nextPeriod(new DateTime("2004-12-13T20:01:00.100Z")), is(new DateTime("2004-12-14T00:00:00.000Z")));
        assertThat(H4.nextPeriod(new DateTime("2004-12-14T00:00:00.001Z")), is(new DateTime("2004-12-14T04:00:00.000Z")));
    }

}
