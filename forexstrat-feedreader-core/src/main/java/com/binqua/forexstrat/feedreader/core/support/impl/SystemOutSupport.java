package com.binqua.forexstrat.feedreader.core.support.impl;

import com.binqua.forexstrat.feedreader.core.support.Support;

public class SystemOutSupport implements Support {

    public void feedLoginProblem(String url, String details) {
        System.out.println("url " + url + " details " + details);
    }

    public void feedReadingProblem(String message) {
        System.out.println(message);
    }

    public void info(String message) {
        System.out.println(message);
    }
}
