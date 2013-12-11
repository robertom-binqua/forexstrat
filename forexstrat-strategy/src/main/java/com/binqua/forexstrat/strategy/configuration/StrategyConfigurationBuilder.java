package com.binqua.forexstrat.strategy.configuration;

import java.util.ArrayList;
import java.util.List;

public class StrategyConfigurationBuilder {
    private List<StrategySingleEntryConfiguration> strategyEntries = new ArrayList<StrategySingleEntryConfiguration>();

    private StrategyConfigurationBuilder() {
    }

    public StrategyConfigurationBuilder withSingleStrategyConfiguration(StrategySingleEntryConfiguration strategyConfigurationEntry) {
        strategyEntries.add(strategyConfigurationEntry);
        return this;
    }

    public StrategyConfiguration build() {
        return new StrategyConfiguration(strategyEntries);
    }

    public static StrategyConfigurationBuilder aStrategyConfiguration() {
        return new StrategyConfigurationBuilder();
    }
}
