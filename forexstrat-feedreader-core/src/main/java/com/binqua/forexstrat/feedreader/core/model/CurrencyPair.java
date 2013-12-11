package com.binqua.forexstrat.feedreader.core.model;

import com.binqua.forexstrat.feedreader.core.model.impl.Currency;

public interface CurrencyPair {

    int numberOfDecimalDigits();

    String asString();

    Currency firstCurrency();

    Currency secondCurrency();

    boolean isDerived();
}
