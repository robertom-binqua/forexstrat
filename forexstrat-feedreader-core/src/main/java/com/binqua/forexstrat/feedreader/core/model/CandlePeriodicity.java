package com.binqua.forexstrat.feedreader.core.model;

import org.joda.time.DateTime;

public interface CandlePeriodicity {

    DateTime getIntervalIdentifier(DateTime dateTime);

    DateTime nextPeriod(DateTime dateTime);

    String asString();

}
