package com.binqua.forexstrat.feedreader.core.model.impl;

import com.binqua.forexstrat.feedreader.core.model.CandlePeriodicity;
import org.joda.time.DateTime;

public enum EnumBasedCandlePeriodicity implements CandlePeriodicity {

    M1(60 * 1000),
    M2(M1.millisecondsPerPeriodicityIdentifier * 2L),
    M5(M1.millisecondsPerPeriodicityIdentifier * 5L),
    M15(M5.millisecondsPerPeriodicityIdentifier * 3L),
    M30(M15.millisecondsPerPeriodicityIdentifier * 2L),
    H1(M30.millisecondsPerPeriodicityIdentifier * 2L),
    H2(H1.millisecondsPerPeriodicityIdentifier * 2L),
    H4(H1.millisecondsPerPeriodicityIdentifier * 4L);

    private long millisecondsPerPeriodicityIdentifier;

    EnumBasedCandlePeriodicity(long millisecondsPerPeriodicityIdentifier) {
        this.millisecondsPerPeriodicityIdentifier = millisecondsPerPeriodicityIdentifier;
    }

    public DateTime getIntervalIdentifier(DateTime dateTime) {
        return new DateTime(dateTime.toDate().getTime() / millisecondsPerPeriodicityIdentifier * millisecondsPerPeriodicityIdentifier);
    }

    public DateTime nextPeriod(DateTime dateTime) {
        return getIntervalIdentifier(dateTime).plus(millisecondsPerPeriodicityIdentifier);
    }

    public String asString() {
        return toString();
    }
}
