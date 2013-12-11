package com.binqua.forexstrat.feedreader.core.model.impl;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;

public enum EnumBasedCurrencyPair implements CurrencyPair {

    EUR_USD("EUR/USD", 4),
    USD_JPY("USD/JPY", 2),
    AUD_JPY("AUD/JPY", 2),
    CAD_JPY("CAD/JPY", 2),
    CHF_JPY("CHF/JPY", 2),
    EUR_GBP("EUR/GBP", 4),
    EUR_JPY("EUR/JPY", 2),
    GBP_JPY("GBP/JPY", 2),
    GBP_USD("GBP/USD", 4),
    NZD_USD("NZD/USD", 4),
    USD_CAD("USD/CAD", 4),
    USD_CHF("USD/CHF", 4),
    AUD_NZD("AUD/NZD", 4),
    AUD_CAD("AUD/CAD", 4, true);

    private String value;
    private int numberOfDecimalDigits;
    private boolean isDerived;

    EnumBasedCurrencyPair(String value, int numberOfDecimalDigits) {
        this(value, numberOfDecimalDigits, false);
    }

    EnumBasedCurrencyPair(String value, int numberOfDecimalDigits, boolean isDerived) {
        this.value = value;
        this.numberOfDecimalDigits = numberOfDecimalDigits;
        this.isDerived = isDerived;
    }

    public int numberOfDecimalDigits() {
        return numberOfDecimalDigits;
    }

    public String asString() {
        return value;
    }

    public Currency firstCurrency() {
        return Currency.valueOf(value.substring(0, value.indexOf("/")));
    }

    public Currency secondCurrency() {
        return Currency.valueOf(value.substring(value.indexOf("/") + 1, value.length()));
    }

    public boolean isDerived() {
        return isDerived;
    }
}
