package com.binqua.forexstrat.feedreader.core.converter;

import java.math.BigDecimal;

public interface CurrencyConverter {
    BigDecimal valueOf(PriceOption priceOption) throws CurrencyConverterException;
}
