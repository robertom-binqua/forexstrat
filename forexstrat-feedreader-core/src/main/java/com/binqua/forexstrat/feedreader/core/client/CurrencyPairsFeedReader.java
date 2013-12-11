package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPairs;

public class CurrencyPairsFeedReader {

    private final CurrencyPairs currencyPairs;
    private final CurrencyPairFeedReader currencyPairFeedReader;
    private final Configuration feedReaderConfiguration;

    public CurrencyPairsFeedReader(Configuration feedReaderConfiguration,
                                   CurrencyPairs currencyPairs,
                                   CurrencyPairFeedReader currencyPairFeedReader) {
        this.feedReaderConfiguration = feedReaderConfiguration;
        this.currencyPairs = currencyPairs;
        this.currencyPairFeedReader = currencyPairFeedReader;
    }

    public void start() {
        if (feedReaderConfiguration.feedReaderHasToRun()) {
            for (CurrencyPair aNonDerivedCurrencyPair : currencyPairs.nonDerivedValues()) {
                currencyPairFeedReader.readFeedFor(aNonDerivedCurrencyPair);
            }
        }
    }

    public void stop() {
        currencyPairFeedReader.stopReadingFeed();
    }

}
