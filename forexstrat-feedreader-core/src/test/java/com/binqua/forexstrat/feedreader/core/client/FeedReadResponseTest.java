package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPairs;
import com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.GBP_USD;
import static com.binqua.forexstrat.feedreader.core.client.FeedReadResponse.feedReadSuccessful;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeedReadResponseTest {

    private static final String FEED_RESPONSE = "GBP/USD,122334343,2.22,200,2.22,400,1.30034,1.30843,1.30151";
    private CurrencyPairs currencyPairs = mock(CurrencyPairs.class);

    @Test
    public void sellValueIsCorrect() {
        assertThat(feedReadSuccessful(FEED_RESPONSE, currencyPairs).currencyPairQuotation().getSellValue(), is("2.22200"));
    }

    @Test
    public void buyValueIsCorrect() {
        assertThat(feedReadSuccessful(FEED_RESPONSE, currencyPairs).currencyPairQuotation().getBuyValue(), is("2.22400"));
    }

    @Test
    public void averageValueIsCorrect() {
        assertThat(feedReadSuccessful(FEED_RESPONSE, currencyPairs).currencyPairQuotation().getAverageValue(), is("2.22300"));
    }

    @Test
    public void currencyPairCorrect() {
        when(currencyPairs.currencyPairFrom("GBP/USD")).thenReturn(EnumBasedCurrencyPair.GBP_USD);

        MatcherAssert.assertThat(feedReadSuccessful(FEED_RESPONSE, currencyPairs).currencyPairQuotation().getCurrencyPair(), (Matcher<? super CurrencyPair>) is(GBP_USD));
    }
}
