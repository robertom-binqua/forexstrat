package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.actions.FeedReaderResponseAction;

import java.util.concurrent.ExecutorService;

public class ExecutorServiceClientRunner implements ClientRunner {

    private final ExecutorService executorService;

    private final ClientFactory clientFactory;

    public ExecutorServiceClientRunner(ExecutorService executorService, ClientFactory clientFactory) {
        this.executorService = executorService;
        this.clientFactory = clientFactory;
    }

    @Override
    public void run(ClientCommands clientCommands, FeedReaderResponseAction[] actionToBeExecutedOnFeedReaderResponse) {
        Runnable clientsCommand = clientFactory.createAClientWith(clientCommands, actionToBeExecutedOnFeedReaderResponse);
        executorService.execute(clientsCommand);
    }

    @Override
    public void stop() {
        executorService.shutdown();
    }
}
