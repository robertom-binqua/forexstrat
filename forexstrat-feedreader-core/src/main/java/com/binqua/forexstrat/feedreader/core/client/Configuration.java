package com.binqua.forexstrat.feedreader.core.client;

public interface Configuration {
    boolean feedReaderHasToRun();

    String serverUrl();

    String password();

    String user();

    int numberOfSecondsBeforeRetry();
}
