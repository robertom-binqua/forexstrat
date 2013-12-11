package com.binqua.forexstrat.feedreader.core.repositories;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyPairQuotationsRepositoryForNonDerivedCurrencyPairs implements ModifiableCurrencyPairQuotationsRepository {

    private ConcurrentHashMap<CurrencyPair, CurrencyPairQuotation> currencyPairsMap = new ConcurrentHashMap<CurrencyPair, CurrencyPairQuotation>();

    public CurrencyPairQuotation quoteFor(CurrencyPair aCurrencyPair) throws CurrencyPairQuotationNotAvailableException {
        if (aCurrencyPair.isDerived()) {
            throw new IllegalArgumentException("This repository is valid for non derived currencyPair. " + aCurrencyPair.asString() + " is derived");
        }
        CurrencyPairQuotation currencyPairQuotation = currencyPairsMap.get(aCurrencyPair);
        if (currencyPairQuotation == null) {
            throw new CurrencyPairQuotationNotAvailableException(aCurrencyPair);
        }
        return currencyPairQuotation;

    }

    @Override
    public List<CurrencyPairQuotation> quotes() {
        throw new IllegalStateException("This method should no be called!");
    }

    public void save(CurrencyPairQuotation currencyPairQuotation) {
        currencyPairsMap.put(currencyPairQuotation.getCurrencyPair(), currencyPairQuotation);
    }

}
