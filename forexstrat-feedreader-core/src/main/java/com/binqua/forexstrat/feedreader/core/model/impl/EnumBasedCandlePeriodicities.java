package com.binqua.forexstrat.feedreader.core.model.impl;

import com.binqua.forexstrat.feedreader.core.model.CandlePeriodicities;
import com.binqua.forexstrat.feedreader.core.model.CandlePeriodicity;

public class EnumBasedCandlePeriodicities implements CandlePeriodicities {
    public CandlePeriodicity[] asArray() {
        return EnumBasedCandlePeriodicity.values();
    }
}
