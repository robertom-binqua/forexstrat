package com.binqua.forexstrat.feedreader.core.util.date;

import org.joda.time.DateTime;

public class DefaultDateProvider implements DateProvider {
    public DateTime now() {
        return new DateTime();
    }
}
