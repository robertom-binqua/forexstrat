package com.binqua.forexstrat.feedreader.core.support;

public interface Support {
    void feedLoginProblem(String message, String details);

    void feedReadingProblem(String message);

    void info(String message);
}
