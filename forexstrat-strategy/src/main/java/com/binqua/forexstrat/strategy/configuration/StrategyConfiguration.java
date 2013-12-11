package com.binqua.forexstrat.strategy.configuration;

import java.util.List;

public class StrategyConfiguration {
    private List<StrategySingleEntryConfiguration> strategyConfigurationEntries;

    public StrategyConfiguration(List<StrategySingleEntryConfiguration> strategyConfigurationEntries) {
        this.strategyConfigurationEntries = strategyConfigurationEntries;
    }

    public StrategySingleEntryConfiguration getSingleStrategyConfiguration(int strategyConfigurationEntryIndex) {
        for (StrategySingleEntryConfiguration strategyEntry : strategyConfigurationEntries) {
            if (strategyEntry.getSliderIndex().equals(String.valueOf(strategyConfigurationEntryIndex))) {
                return strategyEntry;
            }
        }
        throw new IllegalStateException("there is not strategy configuration entry with index " + strategyConfigurationEntryIndex + " in " + strategyConfigurationEntries);
    }


    @Override
    public String toString() {
        return "StrategyConfiguration{" +
                "strategyConfigurationEntries=" + strategyConfigurationEntries +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StrategyConfiguration)) return false;

        StrategyConfiguration that = (StrategyConfiguration) o;

        if (strategyConfigurationEntries != null ? !strategyConfigurationEntries.equals(that.strategyConfigurationEntries) : that.strategyConfigurationEntries != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return strategyConfigurationEntries != null ? strategyConfigurationEntries.hashCode() : 0;
    }

    public int size() {
        return strategyConfigurationEntries.size();
    }
}
