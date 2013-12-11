package com.binqua.forexstrat.feedreader.core.model.impl;

import org.junit.Test;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EnumCurrencyPairTest {

    @Test
    public void asStringUsesSlash() {
        assertThat(AUD_JPY.asString(), is("AUD/JPY"));
    }

    @Test
    public void firstCurrencyIsCorrect() {
        assertThat(AUD_JPY.firstCurrency(), is(Currency.AUD));
    }

    @Test
    public void secondCurrencyIsCorrect() {
        assertThat(AUD_JPY.secondCurrency(), is(Currency.JPY));
    }

    @Test
    public void ifJapanIsTheRightCurrencyDecimalDigitsAre2() {
        assertThat(AUD_JPY.numberOfDecimalDigits(), is(2));
        assertThat(CAD_JPY.numberOfDecimalDigits(), is(2));
        assertThat(CHF_JPY.numberOfDecimalDigits(), is(2));
        assertThat(EUR_JPY.numberOfDecimalDigits(), is(2));
        assertThat(GBP_JPY.numberOfDecimalDigits(), is(2));
        assertThat(USD_JPY.numberOfDecimalDigits(), is(2));
    }

    @Test
    public void ifJapanIsNotTheRightCurrencyDecimalDigitsAre4() {
        assertThat(AUD_NZD.numberOfDecimalDigits(), is(4));
        assertThat(EUR_GBP.numberOfDecimalDigits(), is(4));
        assertThat(EUR_USD.numberOfDecimalDigits(), is(4));
        assertThat(GBP_USD.numberOfDecimalDigits(), is(4));
        assertThat(NZD_USD.numberOfDecimalDigits(), is(4));
        assertThat(USD_CAD.numberOfDecimalDigits(), is(4));
        assertThat(USD_CHF.numberOfDecimalDigits(), is(4));
        assertThat(AUD_CAD.numberOfDecimalDigits(), is(4));
    }

    @Test
    public void thereAre14CurrencyPairs() {
        assertThat(EnumBasedCurrencyPair.values().length, is(14));
    }

}
