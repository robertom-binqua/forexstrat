package com.binqua.forexstrat.strategy.configuration;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ConfigurationSaver {

    public void save(Configuration configuration, String fileLocation) {
        try {
            FileUtils.write(new File(fileLocation), configuration.asString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean configurationExist(String fileLocation) {
        return new File(fileLocation).exists();
    }
}
