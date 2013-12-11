package com.binqua.forexstrat.strategy.configuration;

import com.binqua.forexstrat.strategy.report.ReportResolution;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ReportConfiguration {
    private final String maxMarketValue;
    private final String minMarketValue;
    private final ReportResolution resolutionInPips;

    ReportConfiguration(String maxMarketValue, String minMarketValue, ReportResolution resolutionInPips) {
        this.maxMarketValue = maxMarketValue;
        this.minMarketValue = minMarketValue;
        this.resolutionInPips = resolutionInPips;
    }

    public String maxMarketValue() {
        return maxMarketValue;
    }

    public String minMarketValue() {
        return minMarketValue;
    }

    public ReportResolution resolutionInPips() {
        return resolutionInPips;
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
