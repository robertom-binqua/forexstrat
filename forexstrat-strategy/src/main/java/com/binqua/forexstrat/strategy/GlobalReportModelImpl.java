package com.binqua.forexstrat.strategy;

import com.binqua.forexstrat.strategy.report.CurrencyQuoteGenerator;
import com.binqua.forexstrat.strategy.report.ReportResolution;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.binqua.forexstrat.strategy.AmountWon.amountWon;
import static com.binqua.forexstrat.strategy.CurrencyQuote.currencyQuoteOf;
import static java.util.Arrays.asList;

public class GlobalReportModelImpl implements GlobalReportModel {
    private CurrencyQuote desiredHighestMarketValueInTheReport;
    private CurrencyQuote desiredLowestMarketValueInTheReport;
    private int pipsToPayToTheBroker;
    private BigDecimal costOfTheRightCurrencyInPounds;
    private boolean isABuyStrategy;
    private List<StrategySingleEntry> strategySingleEntryList;
    private Strategy strategy;
    private ReportResolution resolution;
    private CurrencyQuoteGenerator currencyQuoteGenerator;
    private CurrencyQuote theLowestMarketValueInTheReport;
    private AmountWon amountWonFor1PipDecrease;
    private CurrencyQuote theHighestMarketValueInTheReport;

    public GlobalReportModelImpl(String desiredLowestMarketValueInTheReport, String desiredHighestMarketValueInTheReport, Strategy strategy, ReportResolution resolution) throws GlobalReportModelException {
        this.strategy = strategy;
        this.resolution = resolution;
        this.desiredHighestMarketValueInTheReport = currencyQuoteOf(checkThatItIsValid(desiredHighestMarketValueInTheReport, "desiredHighestMarketValueInTheReport"), strategy.currencyPair());
        this.desiredLowestMarketValueInTheReport = currencyQuoteOf(checkThatItIsValid(desiredLowestMarketValueInTheReport, "desiredLowestMarketValueInTheReport"), strategy.currencyPair());
        this.pipsToPayToTheBroker = strategy.pipsToBePayedToTheBroker();
        this.costOfTheRightCurrencyInPounds = strategy.costOfTheRightCurrencyInPounds();
        this.isABuyStrategy = strategy.isABuyStrategy();
        this.strategySingleEntryList = asList(strategy.singleEntriesList());
        this.theLowestMarketValueInTheReport = calculateTheLowestTen(this.desiredLowestMarketValueInTheReport);
        this.theHighestMarketValueInTheReport = calculateTheLowestTen(this.desiredHighestMarketValueInTheReport);
        amountWonFor1PipDecrease = calculateAmountWonOrLostFor1PipDecrease();

    }

    @Override
    public List<ReportModelEntry> getAllEntries() {
        this.currencyQuoteGenerator = new CurrencyQuoteGenerator(strategy.actualMarketValue(),
                strategy.marketEntryValue(),
                amountWonFor1PipDecrease,
                calculateAmountWonOrLost(theHighestMarketValueInTheReport),
                strategy.currencyPair(),
                theLowestMarketValueInTheReport,
                theHighestMarketValueInTheReport,
                resolution);

        List<ReportModelEntry> entries = new ArrayList<>();

        ReportModelEntry reportModelEntry = null;

        while ((reportModelEntry = currencyQuoteGenerator.next()) != null) {
            entries.add(reportModelEntry);
        }

        return entries;
    }

    private BigDecimal checkThatItIsValid(String valueToBeChecked, String fieldInException) throws GlobalReportModelException {
        if (valueToBeChecked == null || valueToBeChecked.length() == 0) {
            throw new GlobalReportModelException("Please specified valid data for " + fieldInException);
        }
        try {
            return new BigDecimal(valueToBeChecked);
        } catch (NumberFormatException e) {
            throw new GlobalReportModelException("Please specified valid data for " + fieldInException);
        }
    }

    private AmountWon calculateAmountWonOrLost(CurrencyQuote theCurrentReportMarketValue) {
        BigDecimal totalAmountWonOrLost = new BigDecimal(0);
        for (StrategySingleEntry strategySingleEntry : strategySingleEntryList) {
            totalAmountWonOrLost = totalAmountWonOrLost.add(strategySingleEntry.calculateGainInPounds(theCurrentReportMarketValue.asBigDecimal(), pipsToPayToTheBroker, isABuyStrategy, costOfTheRightCurrencyInPounds));
        }
        return amountWon(totalAmountWonOrLost);
    }

    private AmountWon calculateAmountWonOrLostFor1PipDecrease() {
        return calculateAmountWonOrLost(theLowestMarketValueInTheReport.addPips(-1)).subtract(calculateAmountWonOrLost(theLowestMarketValueInTheReport));
    }

    private CurrencyQuote calculateTheLowestTen(CurrencyQuote desiredLowestMarketValueInTheReport) {
        return currencyQuoteOf(desiredLowestMarketValueInTheReport.asInt() - desiredLowestMarketValueInTheReport.asInt() % 10, strategy.currencyPair());
    }

}
