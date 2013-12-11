package com.binqua.forexstrat.feedreader.core.repositories;

import com.binqua.forexstrat.feedreader.core.converters.CurrencyConverter;
import com.binqua.forexstrat.feedreader.core.converters.CurrencyConverterException;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;

import java.util.List;
import java.util.Map;

import static com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation.aCurrencyPairValue;
import static com.binqua.forexstrat.feedreader.core.converters.PriceOption.BUY;
import static com.binqua.forexstrat.feedreader.core.converters.PriceOption.SELL;

public class CurrencyPairQuotationsRepositoryForDerivedCurrencyPairs implements CurrencyPairQuotationsRepository {

    private Map<CurrencyPair, CurrencyConverter> currenciesConvertersForDerivedCurrencyPairs;

    public CurrencyPairQuotationsRepositoryForDerivedCurrencyPairs(Map<CurrencyPair, CurrencyConverter> currenciesConvertersForDerivedCurrencyPairs) {
        this.currenciesConvertersForDerivedCurrencyPairs = currenciesConvertersForDerivedCurrencyPairs;
    }

    public CurrencyPairQuotation quoteFor(CurrencyPair aCurrencyPair) throws CurrencyPairQuotationNotAvailableException {
        if (!aCurrencyPair.isDerived()) {
            throw new IllegalArgumentException("This repository is for derived currency pair! " + aCurrencyPair + " is non derived");
        }
        CurrencyConverter currencyConverterForTheDerivedPair = currenciesConvertersForDerivedCurrencyPairs.get(aCurrencyPair);
        if (currencyConverterForTheDerivedPair == null) {
            throw new CurrencyPairQuotationNotAvailableException(aCurrencyPair);
        }
        try {
            return aCurrencyPairValue(aCurrencyPair, currencyConverterForTheDerivedPair.valueOf(SELL), currencyConverterForTheDerivedPair.valueOf(BUY));
        } catch (CurrencyConverterException e) {
            throw new CurrencyPairQuotationNotAvailableException(aCurrencyPair, e.getMessage());
        }
    }

    @Override
    public List<CurrencyPairQuotation> quotes() {
        throw new IllegalStateException("This method should no be called!");
    }

}
