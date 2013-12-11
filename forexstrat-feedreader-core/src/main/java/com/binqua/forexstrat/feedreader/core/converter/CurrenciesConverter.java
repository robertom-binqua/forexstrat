package com.binqua.forexstrat.feedreader.core.converter;

import com.binqua.forexstrat.feedreader.core.model.impl.Currency;

public interface CurrenciesConverter {
    CurrencyConverter converterFor(Currency currency);
}
