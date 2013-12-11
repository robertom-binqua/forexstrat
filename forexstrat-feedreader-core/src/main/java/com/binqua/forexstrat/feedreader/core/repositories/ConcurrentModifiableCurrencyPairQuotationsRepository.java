package com.binqua.forexstrat.feedreader.core.repositories;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class ConcurrentModifiableCurrencyPairQuotationsRepository implements ModifiableCurrencyPairQuotationsRepository {

    private final CurrencyPair[] currencyPairs;
    private final CurrencyPairQuotationsRepository repositoryForDerivedCurrencyPairs;
    private final ModifiableCurrencyPairQuotationsRepository repositoryForNonDerivedCurrencyPairs;

    public ConcurrentModifiableCurrencyPairQuotationsRepository(CurrencyPair[] currencyPairs, CurrencyPairQuotationsRepository repositoryForDerivedCurrencyPairs, ModifiableCurrencyPairQuotationsRepository repositoryForNonDerivedCurrencyPairs) {
        this.currencyPairs = currencyPairs;
        this.repositoryForDerivedCurrencyPairs = repositoryForDerivedCurrencyPairs;
        this.repositoryForNonDerivedCurrencyPairs = repositoryForNonDerivedCurrencyPairs;
    }

    public CurrencyPairQuotation quoteFor(CurrencyPair aCurrencyPair) throws CurrencyPairQuotationNotAvailableException {
        if (aCurrencyPair.isDerived()) {
            return repositoryForDerivedCurrencyPairs.quoteFor(aCurrencyPair);
        }
        return repositoryForNonDerivedCurrencyPairs.quoteFor(aCurrencyPair);
    }

    @Override
    public List<CurrencyPairQuotation> quotes() {
        final List<CurrencyPairQuotation> quotes = new ArrayList<>();
        for (CurrencyPair currencyPair : currencyPairs) {
            try {
                quotes.add(quoteFor(currencyPair));
            } catch (CurrencyPairQuotationNotAvailableException e) {
                e.printStackTrace();
            }
        }
        return quotes;
    }

    public void save(CurrencyPairQuotation currencyPairQuotation) {
        if (currencyPairQuotation.getCurrencyPair().isDerived()) {
            throw new IllegalArgumentException(format("Please call this method with a non derived currencyPair: %s is derived", currencyPairQuotation.getCurrencyPair().asString()));
        }
        repositoryForNonDerivedCurrencyPairs.save(currencyPairQuotation);
    }

}
