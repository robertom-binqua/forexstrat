package com.binqua.forexstrat.strategy.report;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.strategy.AmountWon;
import com.binqua.forexstrat.strategy.CurrencyQuote;
import com.binqua.forexstrat.strategy.GlobalReportModelException;

public class CurrencyQuoteGeneratorBuilder {
    private CurrencyQuote highestMarketValueInTheReport;
    private CurrencyQuote lowestMarketValueInTheReport;
    private ReportResolution resolution;
    private CurrencyPair currencyPair;
    private CurrencyQuoteGenerator currencyQuoteGenerator;
    private AmountWon amountWonFor1PipIncrease;
    private AmountWon amountWonAtTheLowestMarketValueInTheReport;
    private CurrencyQuote actualMarketValue;
    private CurrencyQuote strategyEntryPoint;

    private CurrencyQuoteGeneratorBuilder() {
    }

    public CurrencyQuoteGenerator build() throws GlobalReportModelException {
        currencyQuoteGenerator = new CurrencyQuoteGenerator(
                actualMarketValue,
                strategyEntryPoint,
                amountWonFor1PipIncrease,
                amountWonAtTheLowestMarketValueInTheReport,
                currencyPair,
                lowestMarketValueInTheReport,
                highestMarketValueInTheReport,
                resolution
        );
        return currencyQuoteGenerator;
    }

    public CurrencyQuoteGeneratorBuilder withCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public CurrencyQuoteGeneratorBuilder withHighestMarketValueInTheReport(CurrencyQuote highestMarketValueInTheReport) {
        this.highestMarketValueInTheReport = highestMarketValueInTheReport;
        return this;
    }

    public CurrencyQuoteGeneratorBuilder withLowestMarketValueInTheReport(CurrencyQuote lowestMarketValueInTheReport) {
        this.lowestMarketValueInTheReport = lowestMarketValueInTheReport;
        return this;
    }

    public CurrencyQuoteGeneratorBuilder withResolution(ReportResolution resolution) {
        this.resolution = resolution;
        return this;
    }

    public CurrencyQuoteGeneratorBuilder withAmountWonFor1PipDecrease(AmountWon amountWonFor1PipIncrease) {
        this.amountWonFor1PipIncrease = amountWonFor1PipIncrease;
        return this;
    }

    public CurrencyQuoteGeneratorBuilder withAmountWonAtTheHighestMarketValueInTheReport(AmountWon amountWonAtThelowestMarketValueInTheReport) {
        this.amountWonAtTheLowestMarketValueInTheReport = amountWonAtThelowestMarketValueInTheReport;
        return this;
    }

    public static CurrencyQuoteGeneratorBuilder aCurrencyQuoteGenerator() {
        return new CurrencyQuoteGeneratorBuilder();
    }

    public CurrencyQuoteGeneratorBuilder withActualMarketValue(CurrencyQuote actualMarketValue) {
        this.actualMarketValue = actualMarketValue;
        return this;
    }

     public CurrencyQuoteGeneratorBuilder withStrategyEntryPoint(CurrencyQuote strategyEntryPoint) {
        this.strategyEntryPoint = strategyEntryPoint;
        return this;
    }
}
