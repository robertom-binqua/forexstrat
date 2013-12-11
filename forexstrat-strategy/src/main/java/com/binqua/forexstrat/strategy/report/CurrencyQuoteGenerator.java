package com.binqua.forexstrat.strategy.report;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.strategy.AmountWon;
import com.binqua.forexstrat.strategy.CurrencyQuote;
import com.binqua.forexstrat.strategy.ReportModelEntry;

import static com.binqua.forexstrat.strategy.CurrencyQuote.currencyQuoteOf;

public class CurrencyQuoteGenerator {

    private CurrencyQuote actualMarketValue;
    private CurrencyQuote strategyEntryPoint;
    private AmountWon amountWonFor1PipDecrease;
    private CurrencyPair currencyPair;
    private ReportResolution resolution;
    private CurrencyQuote highestMarketValueInTheReport;
    private CurrencyQuote lowestMarketValueInTheReport;
    private int reportActualValue;
    private int theLowestTen;
    private AmountWon amountWon;

    public CurrencyQuoteGenerator(CurrencyQuote actualMarketValue,
                                  CurrencyQuote strategyEntryPoint,
                                  AmountWon amountWonFor1PipDecrease,
                                  AmountWon amountWonAtTheHighestMarketValueInTheReport,
                                  CurrencyPair currencyPair,
                                  CurrencyQuote lowestMarketValueInTheReport,
                                  CurrencyQuote highestMarketValueInTheReport,
                                  ReportResolution resolution) {
        this.actualMarketValue = actualMarketValue;
        this.strategyEntryPoint = strategyEntryPoint;
        this.amountWonFor1PipDecrease = amountWonFor1PipDecrease;
        this.currencyPair = currencyPair;
        this.resolution = resolution;
        this.highestMarketValueInTheReport = highestMarketValueInTheReport;
        this.lowestMarketValueInTheReport = lowestMarketValueInTheReport;
        this.reportActualValue = this.highestMarketValueInTheReport.asInt();
        this.amountWon = amountWonAtTheHighestMarketValueInTheReport;
        this.theLowestTen = this.lowestMarketValueInTheReport.asInt();
    }

    public ReportModelEntry next() {
        if (reportActualValue < theLowestTen) {
            return null;
        }

        while (divisorNotFoundFor(reportActualValue) && !isActualMarketValue() && !isStrategyEntryPoint()) {
            reportActualValue--;
            amountWon = amountWon.add(amountWonFor1PipDecrease);
        }

        CurrencyQuote nextValue = currencyQuoteOf(reportActualValue, currencyPair);
        ReportModelEntry reportModelEntry = new ReportModelEntry(amountWon, nextValue, isStrategyEntryPoint(), isActualMarketValue(), isDivisibleBy(25, reportActualValue));

        reportActualValue--;
        amountWon = amountWon.add(amountWonFor1PipDecrease);

        return reportModelEntry;
    }

    private boolean isStrategyEntryPoint() {
        return reportActualValue == strategyEntryPoint.asInt();
    }

    private boolean isActualMarketValue() {
        return reportActualValue == actualMarketValue.asInt();
    }

    private boolean divisorNotFoundFor(int actualValue) {
        return !(isDivisibleBy(10, actualValue) ||
                isDivisibleBy(5, actualValue) ||
                isDivisibleBy(resolution.incrementInPips(), actualValue));
    }

    private boolean isDivisibleBy(int i, int valueToBeChecked) {
        return valueToBeChecked % i == 0;
    }


}
