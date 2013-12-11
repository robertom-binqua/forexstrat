package com.binqua.forexstrat.feedreader.core.external;

import com.binqua.forexstrat.feedreader.core.converter.PriceOption;

public interface CurrencyQuoteListener {

    PriceOption priceValueOption();

    void update(Object value);

}

