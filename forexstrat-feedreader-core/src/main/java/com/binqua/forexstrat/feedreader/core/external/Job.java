package com.binqua.forexstrat.feedreader.core.external;

public interface Job {

    String name();

    void run();

    void stop();

}
