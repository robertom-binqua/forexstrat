package com.binqua.forexstrat.feedreader.core.converters;

import java.math.BigDecimal;

public class GBPToGBPCurrencyConverter implements CurrencyConverter {

    public BigDecimal valueOf(PriceOption buyOrSellOption) throws CurrencyConverterException {
        return BigDecimal.ONE;
    }

}
