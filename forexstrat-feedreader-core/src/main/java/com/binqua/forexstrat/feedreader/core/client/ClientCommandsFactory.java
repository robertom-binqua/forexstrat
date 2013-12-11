package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPairs;
import com.binqua.forexstrat.feedreader.core.support.Support;
import org.apache.http.client.HttpClient;

public class ClientCommandsFactory {

    private Support support;
    private CurrencyPairs currencyPairs;
    private Configuration theFeedReaderConfiguration;

    public ClientCommandsFactory(Configuration theFeedReaderConfiguration, CurrencyPairs currencyPairs, Support support) {
        this.support = support;
        this.currencyPairs = currencyPairs;
        this.theFeedReaderConfiguration = theFeedReaderConfiguration;
    }

    ClientCommands createClientCommandsWith(HttpClient aHttpClient, CurrencyPair aCurrencyPair) {
        return new ApacheHttpClientTrueFXClientCommands(
                theFeedReaderConfiguration,
                aHttpClient,
                aCurrencyPair,
                support,
                currencyPairs);
    }
}
