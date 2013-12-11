package com.binqua.forexstrat.feedreader.core.repositories;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;

import java.util.List;

public interface CurrencyPairQuotationsRepository {

    CurrencyPairQuotation quoteFor(CurrencyPair theCurrencyPairIdentifier) throws CurrencyPairQuotationNotAvailableException;

    List<CurrencyPairQuotation> quotes();


}
