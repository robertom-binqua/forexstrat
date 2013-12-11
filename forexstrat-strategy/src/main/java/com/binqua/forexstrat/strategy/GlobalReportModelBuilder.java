package com.binqua.forexstrat.strategy;

import com.binqua.forexstrat.strategy.report.ReportResolution;

public class GlobalReportModelBuilder {
    private String highestMarketValueInTheReport;
    private String lowestMarketValueInTheReport;
    private Strategy strategy;
    private ReportResolution resolution;

    private GlobalReportModelBuilder() {
    }

    public GlobalReportModelImpl build() throws GlobalReportModelException {
        return new GlobalReportModelImpl(
                lowestMarketValueInTheReport,
                highestMarketValueInTheReport,
                strategy,
                resolution);
    }

    public GlobalReportModelBuilder withStrategy(Strategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public GlobalReportModelBuilder withHighestMarketValueInTheReport(String highestMarketValueInTheReport) {
        this.highestMarketValueInTheReport = highestMarketValueInTheReport;
        return this;
    }

    public GlobalReportModelBuilder withLowestMarketValueInTheReport(String lowestMarketValueInTheReport) {
        this.lowestMarketValueInTheReport = lowestMarketValueInTheReport;
        return this;
    }

    public GlobalReportModelBuilder withResolution(ReportResolution resolution) {
        this.resolution = resolution;
        return this;
    }

    public static GlobalReportModelBuilder aReportModel() {
        return new GlobalReportModelBuilder();

    }
}
