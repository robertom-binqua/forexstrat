package com.binqua.forexstrat.strategy;

public class ReportModelEntryBuilder {
    private AmountWon amountWon;
    private CurrencyQuote quoteValue;
    private boolean isTheStrategyEntryPoint;
    private boolean isHighlighted;
    private boolean isTheCurrentMarketValue;
    private String message;

    public ReportModelEntry build() {
        if (message != null) {
            return new ReportModelEntry(message);
        }
        return new ReportModelEntry(amountWon, quoteValue, isTheStrategyEntryPoint, isTheCurrentMarketValue, isHighlighted);
    }

    public static ReportModelEntryBuilder aModelEntry() {
        return new ReportModelEntryBuilder();
    }

    public static ReportModelEntryBuilder aNonValidReportModelEntry() {
        return new ReportModelEntryBuilder();
    }

    public ReportModelEntryBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ReportModelEntryBuilder with(AmountWon withAmountWon) {
        this.amountWon = withAmountWon;
        return this;
    }

    public ReportModelEntryBuilder with(CurrencyQuote quoteValue) {
        this.quoteValue = quoteValue;
        return this;
    }

    public ReportModelEntryBuilder isTheStrategyEntryPoint(boolean isTheStrategyEntryPoint) {
        this.isTheStrategyEntryPoint = isTheStrategyEntryPoint;
        return this;
    }

    public ReportModelEntryBuilder withAHighlightedValue(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
        return this;
    }

    public ReportModelEntryBuilder isTheCurrentMarketValue(boolean isTheCurrentMarketValue) {
        this.isTheCurrentMarketValue = isTheCurrentMarketValue;
        return this;
    }
}
