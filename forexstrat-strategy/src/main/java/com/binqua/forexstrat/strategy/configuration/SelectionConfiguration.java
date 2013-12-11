package com.binqua.forexstrat.strategy.configuration;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SelectionConfiguration {
    private final String selection;

    private final String marketValue;

    public SelectionConfiguration(String selection, String marketValue) {
        this.selection = selection;
        this.marketValue = marketValue;
    }

    public String getSelection() {
        return selection;
    }

    public String getMarketValue() {
        return marketValue;
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
