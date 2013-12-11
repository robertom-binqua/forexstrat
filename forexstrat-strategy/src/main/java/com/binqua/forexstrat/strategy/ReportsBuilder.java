package com.binqua.forexstrat.strategy;

public class ReportsBuilder {

    private Strategy strategy;

    public ReportsBuilder withStrategy(Strategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public Report buildReportWithAllStrategyEntriesDetails() {
        Report report = new Report();
        for (StrategySingleEntry strategySingleEntry : strategy.singleEntriesList()) {
            ReportEntry reportEntry = strategySingleEntry.createReportEntry(strategy.marketEntryValue().asBigDecimal(),
                    strategy.marketExitValue().asBigDecimal(),
                    strategy.isABuyStrategy(),
                    strategy.pipsToBePayedToTheBroker(),
                    strategy.lowestMarketValueInTheReport().asBigDecimal(),
                    strategy.costOfTheRightCurrencyInPounds());
            report.addReportEntry(reportEntry);
        }

        return report;
    }

    public static ReportsBuilder aReportsBuilder() {
        return new ReportsBuilder();
    }


}
