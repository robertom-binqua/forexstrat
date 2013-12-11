package com.binqua.forexstrat.feedreader.core.client;


import com.binqua.forexstrat.feedreader.core.actions.FeedReaderResponseAction;

public class FeedReaderClient implements Runnable {

    private final ClientCommands clientCommands;
    private final FeedReaderResponseAction[] actions;

    public FeedReaderClient(ClientCommands clientCommands, FeedReaderResponseAction... actions) {
        this.clientCommands = clientCommands;
        this.actions = actions;
    }

    public void run() {
        FeedLoginResponse feedLoginResponse = clientCommands.login();
        if (feedLoginResponse.loginHasBeenAborted()) {
            return;
        }
        while (!Thread.currentThread().isInterrupted()) {
            FeedReadResponse readResponse = clientCommands.read(feedLoginResponse);
            if (readResponse.feedReadResponseUnsuccessful()) {
                feedLoginResponse = clientCommands.login();
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
