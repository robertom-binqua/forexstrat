package com.binqua.forexstrat.feedreader.core.converters;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;

import java.math.BigDecimal;

public abstract class AbstractCurrencyConverter implements CurrencyConverter {

    public final BigDecimal valueOf(PriceOption priceOption) throws CurrencyConverterException {
        try {
            return priceOption == PriceOption.BUY ? buyValue() : sellValue();
        } catch (CurrencyPairQuotationNotAvailableException e) {
            throw new CurrencyConverterException(e.getMessage());
        }
    }

    abstract BigDecimal sellValue() throws CurrencyPairQuotationNotAvailableException;

    abstract BigDecimal buyValue() throws CurrencyPairQuotationNotAvailableException;
}
