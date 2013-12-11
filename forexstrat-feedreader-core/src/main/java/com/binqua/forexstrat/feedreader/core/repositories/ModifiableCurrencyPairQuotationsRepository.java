package com.binqua.forexstrat.feedreader.core.repositories;

import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;

public interface ModifiableCurrencyPairQuotationsRepository extends CurrencyPairQuotationsRepository {

    void save(CurrencyPairQuotation currencyQuote);

}
