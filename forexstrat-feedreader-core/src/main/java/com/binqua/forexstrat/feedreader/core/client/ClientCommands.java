package com.binqua.forexstrat.feedreader.core.client;

public interface ClientCommands {

    FeedLoginResponse login();

    FeedReadResponse read(FeedLoginResponse loginResponse);

}