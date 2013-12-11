package com.binqua.forexstrat.strategy.configuration;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class StrategySingleEntryConfiguration {
    private final int sliderIndex;
    private final double numberOfContracts;
    private final String valueAsText;
    private final int selection;

    public StrategySingleEntryConfiguration(int index, double numberOfContracts, SelectionConfiguration selectionConfiguration) {
        this.sliderIndex = index;
        this.numberOfContracts = numberOfContracts;
        this.valueAsText = selectionConfiguration.getMarketValue();
        this.selection = Integer.parseInt(selectionConfiguration.getSelection());
    }

    public double getNumberOfContracts() {
        return numberOfContracts;
    }

    public String getSelection() {
        return String.valueOf(selection);
    }

    public String getSliderIndex() {
        return String.valueOf(sliderIndex);
    }

    public String getValue() {
        return valueAsText;
    }

    public SelectionConfiguration getSelectionConfiguration() {
        return new SelectionConfigurationBuilder().withSelection(String.valueOf(selection)).withMarketValue(valueAsText).build();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
