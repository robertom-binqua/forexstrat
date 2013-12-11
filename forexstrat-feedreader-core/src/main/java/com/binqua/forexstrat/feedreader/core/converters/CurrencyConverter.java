package com.binqua.forexstrat.feedreader.core.converters;

import java.math.BigDecimal;

public interface CurrencyConverter {
    BigDecimal valueOf(PriceOption priceOption) throws CurrencyConverterException;
}
