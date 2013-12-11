package com.binqua.forexstrat.feedreader.core.repositories;

import com.binqua.forexstrat.feedreader.core.converter.CurrencyConverterException;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import org.junit.Test;

import static com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation.aCurrencyPairValue;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.AUD_JPY;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.EUR_GBP;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class CurrencyPairQuotationsRepositoryForNonDerivedCurrencyPairsTest {

    private CurrencyPairQuotation theEUR_GBPCurrencyPairQuotation = aCurrencyPairValue(EUR_GBP, "2.0000", "2.000");

    private CurrencyPairQuotationsRepositoryForNonDerivedCurrencyPairs repoUnderTest = new CurrencyPairQuotationsRepositoryForNonDerivedCurrencyPairs();

    @Test
    public void savesTheRightValue() throws CurrencyPairQuotationNotAvailableException {
        repoUnderTest.save(theEUR_GBPCurrencyPairQuotation);

        assertThat("feed read response", repoUnderTest.quoteFor(EUR_GBP), is(theEUR_GBPCurrencyPairQuotation));
    }

    @Test
    public void whenQuoteIsNotAvailableACurrencyQuoteNotAvailableExceptionIsThrown() throws CurrencyPairQuotationNotAvailableException {
        CurrencyPair nonAvailableCurrencyPair = AUD_JPY;
        try {
            repoUnderTest.quoteFor(nonAvailableCurrencyPair);
            fail(CurrencyPairQuotationNotAvailableException.class + " should have been thrown");
        } catch (CurrencyPairQuotationNotAvailableException e) {
            assertThat(e.getMessage(), is("No currency quote available yet for AUD/JPY. Please try later!"));
        }
    }

    @Test(expected = IllegalStateException.class)
      public void getQuotesThrowsIllegalStateException() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
         repoUnderTest.quotes();
      }


}
