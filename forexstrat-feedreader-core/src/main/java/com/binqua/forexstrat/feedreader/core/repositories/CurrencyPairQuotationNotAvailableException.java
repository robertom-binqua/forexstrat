package com.binqua.forexstrat.feedreader.core.repositories;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;

public class CurrencyPairQuotationNotAvailableException extends Exception {

    public CurrencyPairQuotationNotAvailableException(CurrencyPair aCurrencyPair) {
        super(createMessageFor(aCurrencyPair));
    }

    public CurrencyPairQuotationNotAvailableException(CurrencyPair aCurrencyPair, String details) {
        super(createMessageFor(aCurrencyPair) + " Details: " + details);
    }

    private static String createMessageFor(CurrencyPair aCurrencyPair) {
        return "No currency quote available yet for " + aCurrencyPair.asString() + ". Please try later!";
    }
}
