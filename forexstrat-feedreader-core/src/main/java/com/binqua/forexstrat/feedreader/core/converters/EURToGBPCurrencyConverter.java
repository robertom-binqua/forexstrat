package com.binqua.forexstrat.feedreader.core.converters;


import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.EUR_GBP;

public class EURToGBPCurrencyConverter extends AbstractCurrencyConverter {
    private CurrencyPairQuotationsRepository currenciesQuoteRepository;

    public EURToGBPCurrencyConverter(CurrencyPairQuotationsRepository currenciesQuoteRepository) {
        this.currenciesQuoteRepository = currenciesQuoteRepository;
    }

    BigDecimal sellValue() throws CurrencyPairQuotationNotAvailableException {
        return new BigDecimal(currenciesQuoteRepository.quoteFor(EUR_GBP).getSellValue());
    }

    BigDecimal buyValue() throws CurrencyPairQuotationNotAvailableException {
        return new BigDecimal(currenciesQuoteRepository.quoteFor(EUR_GBP).getBuyValue());
    }
}
