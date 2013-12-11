package com.binqua.forexstrat.feedreader.core.converters;

import com.binqua.forexstrat.feedreader.core.model.impl.Currency;

public interface CurrenciesConverter {
    CurrencyConverter converterFor(Currency currency);
}
