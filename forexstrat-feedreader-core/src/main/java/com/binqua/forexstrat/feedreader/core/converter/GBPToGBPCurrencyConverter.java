package com.binqua.forexstrat.feedreader.core.converter;

import java.math.BigDecimal;

public class GBPToGBPCurrencyConverter implements CurrencyConverter {

    public BigDecimal valueOf(PriceOption buyOrSellOption) throws CurrencyConverterException {
        return BigDecimal.ONE;
    }

}
