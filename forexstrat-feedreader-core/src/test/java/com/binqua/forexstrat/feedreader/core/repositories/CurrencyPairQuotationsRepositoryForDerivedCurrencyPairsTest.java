package com.binqua.forexstrat.feedreader.core.repositories;

import com.binqua.forexstrat.feedreader.core.converter.CurrencyConverterException;
import org.junit.Test;

public class CurrencyPairQuotationsRepositoryForDerivedCurrencyPairsTest {

    @Test(expected = IllegalStateException.class)
    public void getQuotesThrowsIllegalStateException() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        new CurrencyPairQuotationsRepositoryForDerivedCurrencyPairs(null).quotes();
    }
}
