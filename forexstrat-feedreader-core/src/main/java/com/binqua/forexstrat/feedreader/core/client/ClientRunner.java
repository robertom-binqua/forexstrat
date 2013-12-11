package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.actions.FeedReaderResponseAction;

public interface ClientRunner {

    void run(ClientCommands feedReaderClientCommands, FeedReaderResponseAction... actionToBeExecutedOnFeedReaderResponse);

    void stop();
    
}
