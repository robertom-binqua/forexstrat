package com.binqua.forexstrat.feedreader.core.client;


import com.binqua.forexstrat.feedreader.core.actions.FeedReaderResponseAction;

public class FeedReaderClient implements Runnable {

    private final ClientCommands feedReadClient;
    private final FeedReaderResponseAction[] actions;

    public FeedReaderClient(ClientCommands feedReadClient, FeedReaderResponseAction... actions) {
        this.feedReadClient = feedReadClient;
        this.actions = actions;
    }

    public void run() {
        FeedLoginResponse feedLoginResponse = feedReadClient.login();
        if (feedLoginResponse.loginHasBeenAborted()) {
            return;
        }
        while (!Thread.currentThread().isInterrupted()) {
            FeedReadResponse readResponse = feedReadClient.read(feedLoginResponse);
            if (readResponse.feedReadResponseUnsuccessful()) {
                feedLoginResponse = feedReadClient.login();
                if (feedLoginResponse.loginHasBeenAborted()) {
                    return;
                }
            } else {
                for (FeedReaderResponseAction action : actions) {
                    action.actOn(readResponse);
                }
            }
        }
    }
}
