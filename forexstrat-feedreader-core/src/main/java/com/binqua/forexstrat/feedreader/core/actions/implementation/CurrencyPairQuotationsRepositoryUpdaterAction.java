package com.binqua.forexstrat.feedreader.core.actions.implementation;

import com.binqua.forexstrat.feedreader.core.client.FeedReadResponse;
import com.binqua.forexstrat.feedreader.core.repositories.ModifiableCurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.actions.FeedReaderResponseAction;

public class CurrencyPairQuotationsRepositoryUpdaterAction implements FeedReaderResponseAction {

    private ModifiableCurrencyPairQuotationsRepository modifiableCurrenciesQuoteRepository;

    public CurrencyPairQuotationsRepositoryUpdaterAction(ModifiableCurrencyPairQuotationsRepository modifiableCurrenciesQuoteRepository) {
        this.modifiableCurrenciesQuoteRepository = modifiableCurrenciesQuoteRepository;
    }

    public void actOn(FeedReadResponse feedReadResponse) {
        if (feedReadResponse.feedReadResponseUnsuccessful() || feedReadResponse.getResponse().length() == 0) {
            return;
        }
        modifiableCurrenciesQuoteRepository.save(feedReadResponse.currencyPairQuotation());
    }
}

