package com.binqua.forexstrat.feedreader.core.client;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.apache.commons.lang.StringUtils.isEmpty;

public class PropertyFileConfiguration implements Configuration {

    private static final String FEED_READER_CONFIGURATION_SYSTEM_PROPERTY_KEY = "feedReader.properties";

    private Properties properties;

    public PropertyFileConfiguration() {
        properties = loadPropertyFrom(systemProperty(FEED_READER_CONFIGURATION_SYSTEM_PROPERTY_KEY));
    }

    @Override
    public boolean feedReaderHasToRun() {
        return Boolean.parseBoolean(property("feedReaderHasToRun"));
    }

    @Override
    public String serverUrl() {
        return property("serverUrl");
    }

    @Override
    public String password() {
        return property("password");
    }

    @Override
    public String user() {
        return property("user");
    }

    @Override
    public int numberOfSecondsBeforeRetry() {
        return Integer.valueOf(property("numberOfSecondsBeforeRetry"));
    }

    private String property(String property) {
        return properties.getProperty(property);
    }

    private Properties loadPropertyFrom(String configurationFileLocation) {
        final Properties properties = new Properties();
        try {
            properties.load(new FileReader(configurationFileLocation));
        } catch (IOException e) {
            throw new IllegalStateException("Problem reading configuration file " + configurationFileLocation);
        }
        return properties;
    }

    private String systemProperty(String property) {
        final String configurationFileLocation = System.getProperty(property);
        if (isEmpty(configurationFileLocation)) {
            throw new IllegalStateException("System property " + property + " has to be specified");
        }
        return configurationFileLocation;
    }
}
