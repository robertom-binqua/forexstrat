package com.binqua.forexstrat.feedreader.core.converters;


import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;

import java.util.*;

import static com.binqua.forexstrat.feedreader.core.model.impl.Currency.*;

public class ToPoundsConverter implements CurrenciesConverter {

    private final Map<com.binqua.forexstrat.feedreader.core.model.impl.Currency, CurrencyConverter> map = new HashMap<com.binqua.forexstrat.feedreader.core.model.impl.Currency, CurrencyConverter>();

    public ToPoundsConverter(CurrencyPairQuotationsRepository currenciesQuoteRepository) {
        map.put(AUD, new AUDToGBPCurrencyConverter(currenciesQuoteRepository));
        map.put(EUR, new EURToGBPCurrencyConverter(currenciesQuoteRepository));
        map.put(NZD, new NZDToGBPCurrencyConverter(currenciesQuoteRepository));
        map.put(USD, new USDToGBPCurrencyConverter(currenciesQuoteRepository));
        map.put(JPY, new JPYToGBPCurrencyConverter(currenciesQuoteRepository));
        map.put(CAD, new CADToGBPCurrencyConverter(currenciesQuoteRepository));
        map.put(GBP, new GBPToGBPCurrencyConverter());
    }

    public CurrencyConverter converterFor(com.binqua.forexstrat.feedreader.core.model.impl.Currency currency) {
        if (map.get(currency) == null) {
            throw new IllegalStateException("No converter found for currency " + currency + ". Converters available" + asString(map.keySet()));
        }
        return map.get(currency);
    }

    private String asString(Set<com.binqua.forexstrat.feedreader.core.model.impl.Currency> entries) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<com.binqua.forexstrat.feedreader.core.model.impl.Currency> currencies = new ArrayList<com.binqua.forexstrat.feedreader.core.model.impl.Currency>(entries);
        Collections.sort(currencies);
        for (com.binqua.forexstrat.feedreader.core.model.impl.Currency currency : currencies) {
            stringBuilder.append(" " + currency);
        }
        return stringBuilder.toString();
    }

}
