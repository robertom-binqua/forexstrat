package com.binqua.forexstrat.feedreader.core.external;

import com.binqua.forexstrat.feedreader.core.converters.PriceOption;

public interface CurrencyQuoteListener {

    PriceOption priceValueOption();

    void update(Object value);

}

