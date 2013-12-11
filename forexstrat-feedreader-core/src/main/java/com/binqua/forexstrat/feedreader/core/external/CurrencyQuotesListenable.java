package com.binqua.forexstrat.feedreader.core.external;

public interface CurrencyQuotesListenable {

    void addRightCurrencyQuoteListener(CurrencyQuoteListener currencyQuoteListener);

    void addCurrencyPairQuoteListener(CurrencyQuoteListener currencyQuoteListener);

    void removeListeners();

    void removeRightCurrencyQuoteListener();

    void removeCurrencyPairQuoteListener();

}
