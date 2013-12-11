package com.binqua.forexstrat.strategy.configuration;

public class SelectionConfigurationBuilder {

    private String selection;
    private String marketValue;

    public SelectionConfigurationBuilder withSelection(String selection) {
        this.selection = selection;
        return this;
    }

    public SelectionConfigurationBuilder withMarketValue(String marketValue) {
        this.marketValue = marketValue;
        return this;
    }

    public SelectionConfiguration build() {
        return new SelectionConfiguration(selection, marketValue);
    }
}
