package com.binqua.forexstrat.strategy.configuration;

import com.binqua.forexstrat.strategy.report.ReportResolution;

public class ReportConfigurationBuilder {
    private String maxMarketValue;
    private String minMarketValue;
    private ReportResolution resolutionInPips;

    private ReportConfigurationBuilder() {
    }

    public ReportConfigurationBuilder withMaxMarketValue(String maxMarketValue) {
        this.maxMarketValue = maxMarketValue;
        return this;
    }

    public ReportConfigurationBuilder withMinMarketValue(String minMarketValue) {
        this.minMarketValue = minMarketValue;
        return this;
    }

    public ReportConfigurationBuilder withResolutionInPips(ReportResolution resolutionInPips) {
        this.resolutionInPips = resolutionInPips;
        return this;
    }

    public static ReportConfigurationBuilder aReportConfiguration() {
        return new ReportConfigurationBuilder();
    }

    public ReportConfiguration build() {
        return new ReportConfiguration(maxMarketValue, minMarketValue, resolutionInPips);
    }
}
