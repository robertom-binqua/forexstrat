package com.binqua.forexstrat.feedreader.core.external;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import com.binqua.forexstrat.feedreader.core.converters.CurrenciesConverter;
import com.binqua.forexstrat.feedreader.core.converters.CurrencyConverter;
import com.binqua.forexstrat.feedreader.core.converters.CurrencyConverterException;

import java.util.concurrent.CopyOnWriteArrayList;

public class CurrencyQuotesUpdaterJob implements Job, CurrencyQuotesListenable {

    private CopyOnWriteArrayList<CurrencyQuoteListener> rightCurrencyQuoteListeners = new CopyOnWriteArrayList<CurrencyQuoteListener>();
    private CopyOnWriteArrayList<CurrencyQuoteListener> currencyPairQuoteListeners = new CopyOnWriteArrayList<CurrencyQuoteListener>();

    private final CurrencyConverter rightCurrencyConverter;
    private final CurrencyPairQuotationsRepository currenciesQuoteRepository;

    private final CurrencyPair currencyPair;

    public CurrencyQuotesUpdaterJob(CurrencyPair currencyPair, CurrenciesConverter currenciesConverter, CurrencyPairQuotationsRepository currenciesQuoteRepository) {
        this.currencyPair = currencyPair;
        this.currenciesQuoteRepository = currenciesQuoteRepository;
        this.rightCurrencyConverter = currenciesConverter.converterFor(currencyPair.secondCurrency());
    }

    public String name() {
        return currencyPair.asString() + " job";
    }

    public void run() {
        updateRightCurrencyQuoteListeners();
        updateCurrencyPairQuoteListeners();
    }

    private void updateRightCurrencyQuoteListeners() {
        if (rightCurrencyQuoteListeners.size() > 0) {
            for (CurrencyQuoteListener currencyQuoteListener : rightCurrencyQuoteListeners) {
                try {
                    currencyQuoteListener.update(rightCurrencyConverter.valueOf(currencyQuoteListener.priceValueOption()));
                } catch (CurrencyConverterException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void updateCurrencyPairQuoteListeners() {
        if (currencyPairQuoteListeners.size() > 0) {
            for (CurrencyQuoteListener currencyPairQuotesListener : currencyPairQuoteListeners) {
                try {
                    CurrencyPairQuotation currencyPairValue = currenciesQuoteRepository.quoteFor(currencyPair);
                    currencyPairQuotesListener.update(currencyPairValue.getAverageValue());
                } catch (CurrencyPairQuotationNotAvailableException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void stop() {
    }

    public void addCurrencyPairQuoteListener(CurrencyQuoteListener currencyQuoteListener) {
        currencyPairQuoteListeners.add(currencyQuoteListener);
    }

    public void removeListeners() {
        this.rightCurrencyQuoteListeners = new CopyOnWriteArrayList<CurrencyQuoteListener>();
        this.currencyPairQuoteListeners = new CopyOnWriteArrayList<CurrencyQuoteListener>();
    }

    public void removeRightCurrencyQuoteListener() {
        this.rightCurrencyQuoteListeners = new CopyOnWriteArrayList<CurrencyQuoteListener>();
    }

    public void removeCurrencyPairQuoteListener() {
        this.currencyPairQuoteListeners = new CopyOnWriteArrayList<CurrencyQuoteListener>();
    }

    public void addRightCurrencyQuoteListener(CurrencyQuoteListener currencyQuoteListener) {
        rightCurrencyQuoteListeners.add(currencyQuoteListener);
    }
}