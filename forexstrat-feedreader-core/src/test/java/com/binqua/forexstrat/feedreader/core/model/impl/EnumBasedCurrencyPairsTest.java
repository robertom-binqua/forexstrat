package com.binqua.forexstrat.feedreader.core.model.impl;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPairs;
import org.hamcrest.Matcher;
import org.junit.Test;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EnumBasedCurrencyPairsTest {

    private EnumBasedCurrencyPairs enumBasedCurrencyPairs = new EnumBasedCurrencyPairs();

    @Test
    public void nonDerivedValues() {
        CurrencyPair[] actualNonDerivedCurrencyPairs = enumBasedCurrencyPairs.nonDerivedValues();
        assertThat(actualNonDerivedCurrencyPairs.length, is(13));

        assertThat(actualNonDerivedCurrencyPairs[0].asString(), is(AUD_JPY.asString()));
        assertThat(actualNonDerivedCurrencyPairs[1].asString(), is(AUD_NZD.asString()));
        assertThat(actualNonDerivedCurrencyPairs[2].asString(), is(CAD_JPY.asString()));
        assertThat(actualNonDerivedCurrencyPairs[3].asString(), is(CHF_JPY.asString()));
        assertThat(actualNonDerivedCurrencyPairs[4].asString(), is(EUR_GBP.asString()));
        assertThat(actualNonDerivedCurrencyPairs[5].asString(), is(EUR_JPY.asString()));
        assertThat(actualNonDerivedCurrencyPairs[6].asString(), is(EUR_USD.asString()));
        assertThat(actualNonDerivedCurrencyPairs[7].asString(), is(GBP_JPY.asString()));
        assertThat(actualNonDerivedCurrencyPairs[8].asString(), is(GBP_USD.asString()));
        assertThat(actualNonDerivedCurrencyPairs[9].asString(), is(NZD_USD.asString()));
        assertThat(actualNonDerivedCurrencyPairs[10].asString(), is(USD_CAD.asString()));
        assertThat(actualNonDerivedCurrencyPairs[11].asString(), is(USD_CHF.asString()));
        assertThat(actualNonDerivedCurrencyPairs[12].asString(), is(USD_JPY.asString()));

    }

    @Test
    public void currencyPairFrom() {
        assertThat(enumBasedCurrencyPairs.currencyPairFrom("AUD/JPY"), (Matcher<? super CurrencyPair>) is(AUD_JPY));

    }


}
