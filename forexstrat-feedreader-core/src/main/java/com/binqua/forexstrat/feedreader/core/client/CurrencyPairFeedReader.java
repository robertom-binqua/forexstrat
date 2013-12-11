package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.actions.FeedReaderResponseAction;
import org.apache.http.client.HttpClient;

public class CurrencyPairFeedReader {

    private final ClientRunner clientRunner;
    private final FeedReaderResponseAction[] actionToBeExecutedOnFeedReaderResponse;
    private final ClientCommandsFactory clientCommandsFactory;
    private final HttpClientManager httpClientManager;

    public CurrencyPairFeedReader(ClientRunner clientRunner,
                                  ClientCommandsFactory clientCommandsFactory,
                                  HttpClientManager httpClientManager,
                                  FeedReaderResponseAction... actionToBeExecutedOnFeedReaderResponse) {
        this.clientRunner = clientRunner;
        this.clientCommandsFactory = clientCommandsFactory;
        this.httpClientManager = httpClientManager;
        this.actionToBeExecutedOnFeedReaderResponse = actionToBeExecutedOnFeedReaderResponse;
    }

    void readFeedFor(CurrencyPair aCurrencyPair) {
        final HttpClient aHttpClient = httpClientManager.createAHttpClient();
        final ClientCommands clientCommands = clientCommandsFactory.createClientCommandsWith(aHttpClient, aCurrencyPair);
        clientRunner.run(clientCommands, actionToBeExecutedOnFeedReaderResponse);
    }

    void stopReadingFeed() {
        httpClientManager.shutdown();
        clientRunner.stop();
    }
}
