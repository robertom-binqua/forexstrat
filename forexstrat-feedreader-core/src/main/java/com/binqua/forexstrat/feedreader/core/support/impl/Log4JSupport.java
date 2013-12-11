package com.binqua.forexstrat.feedreader.core.support.impl;

import com.binqua.forexstrat.feedreader.core.support.Support;
import org.apache.log4j.Logger;

public class Log4JSupport implements Support {

    private static Logger LOG = Logger.getLogger(Log4JSupport.class);

    public void feedLoginProblem(String url, String details) {
        LOG.info("Problem login to " + url + ".Details " + details);
    }

    public void feedReadingProblem(String message) {
        LOG.info(message);
    }

    public void info(String message) {
        LOG.info(message);
    }
}
