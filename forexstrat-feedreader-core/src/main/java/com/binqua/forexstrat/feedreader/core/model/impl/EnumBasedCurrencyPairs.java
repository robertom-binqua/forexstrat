package com.binqua.forexstrat.feedreader.core.model.impl;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPairs;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static ch.lambdaj.Lambda.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class EnumBasedCurrencyPairs implements CurrencyPairs {

    private static final CurrencyPair[] currencyPairs = EnumBasedCurrencyPair.values();

    public CurrencyPair currencyPairFrom(String currencyPairAsString) {
        for (CurrencyPair currencyPair : currencyPairs) {
            if (currencyPair.asString().equals(currencyPairAsString)) {
                return currencyPair;
            }
        }
        throw new IllegalArgumentException("Cannot find currency pair for " + currencyPairAsString);
    }

    public CurrencyPair[] asArray() {
        return currencyPairs;
    }

    public String[] displayValues() {
        String[] displayValues = new String[currencyPairs.length];
        for (int i = 0; i < displayValues.length; i++) {
            displayValues[i] = currencyPairs[i].asString();
        }
        return displayValues;
    }

    public CurrencyPair[] nonDerivedValues() {
        List<CurrencyPair> currencyPairNonDerivedList = filter(having(on(CurrencyPair.class).isDerived(), equalTo(false)), Arrays.asList(currencyPairs));
        Collections.sort(currencyPairNonDerivedList, new Comparator<CurrencyPair>() {
            public int compare(CurrencyPair firstCurrencyPair, CurrencyPair secondCurrencyPair) {
                return firstCurrencyPair.asString().compareTo(secondCurrencyPair.asString());
            }
        });
        return currencyPairNonDerivedList.toArray(new CurrencyPair[currencyPairNonDerivedList.size()]);
    }

}
