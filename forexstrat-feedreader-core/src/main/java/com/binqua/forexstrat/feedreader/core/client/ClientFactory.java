package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.actions.FeedReaderResponseAction;

public class ClientFactory {
    Runnable createAClientWith(ClientCommands feedReaderClientCommands, FeedReaderResponseAction[] actionToBeExecutedOnFeedReaderResponse) {
        return new FeedReaderClient(feedReaderClientCommands, actionToBeExecutedOnFeedReaderResponse);
    }
}
