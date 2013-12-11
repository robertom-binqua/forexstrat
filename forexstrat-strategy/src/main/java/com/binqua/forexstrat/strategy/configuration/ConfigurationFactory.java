package com.binqua.forexstrat.strategy.configuration;

public class ConfigurationFactory {
    private static ConfigurationExporter configurationExporter;

    private static ConfigurationClient configurationClient;

    public static void registerConfigurationExporter(ConfigurationExporter configurationExporter) {
        ConfigurationFactory.configurationExporter = configurationExporter;
    }

    public static ConfigurationExporter getConfigurationExporter() {
        return configurationExporter;
    }

    public static void registerConfigurationClient(ConfigurationClient configurationClient) {
        ConfigurationFactory.configurationClient = configurationClient;
    }

    public static ConfigurationClient getConfigurationClient() {
        return configurationClient;
    }

}
