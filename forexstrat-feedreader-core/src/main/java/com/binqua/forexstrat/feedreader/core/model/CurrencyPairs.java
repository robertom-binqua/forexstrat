package com.binqua.forexstrat.feedreader.core.model;

public interface CurrencyPairs {

    CurrencyPair[] nonDerivedValues();

    String[] displayValues();

    CurrencyPair currencyPairFrom(String currencyPairAsString);

    CurrencyPair[] asArray();

}
