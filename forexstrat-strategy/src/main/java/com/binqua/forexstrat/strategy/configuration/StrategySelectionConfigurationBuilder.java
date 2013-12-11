package com.binqua.forexstrat.strategy.configuration;

public class StrategySelectionConfigurationBuilder {
    private int index;
    private double contractSoldOrBought;
    private SelectionConfiguration selectionConfiguration;

    public StrategySelectionConfigurationBuilder withIndex(int index) {
        this.index = index;
        return this;
    }

    public StrategySelectionConfigurationBuilder withContractsSellOrBought(double contractSoldOrBought) {
        this.contractSoldOrBought = contractSoldOrBought;
        return this;
    }

    public StrategySelectionConfigurationBuilder withSelectionCofiguration(SelectionConfiguration selectionConfiguration) {
        this.selectionConfiguration = selectionConfiguration;
        return this;
    }

    public StrategySingleEntryConfiguration build() {
        return new StrategySingleEntryConfiguration(index, contractSoldOrBought, selectionConfiguration);
    }
}
