package com.binqua.forexstrat.strategy;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;

import java.util.ArrayList;
import java.util.List;

public class StrategyBuilder {

    private List<StrategySingleEntry> strategySingleEntryList = new ArrayList<StrategySingleEntry>();
    private String pipsToBePayedToTheBroker;
    private String costOfTheRightCurrencyInPounds;
    private String wholeStrategyEntryValue;
    private String wholeStrategyExitValue;
    private boolean isABuyStrategy;
    private String lowestMarketValueInTheReport;
    private String actualMarketValue;
    private CurrencyPair currencyPair;

    public static StrategyBuilder aStrategy() {
        return new StrategyBuilder();
    }

    public StrategyBuilder withCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public StrategyBuilder withPipsToBePayedToTheBroker(String pipsToBePayedToTheBroker) {
        this.pipsToBePayedToTheBroker = pipsToBePayedToTheBroker;
        return this;
    }

    public StrategyBuilder withCostOfTheRightCurrencyInPounds(String costOfTheRightCurrencyInPounds) {
        this.costOfTheRightCurrencyInPounds = costOfTheRightCurrencyInPounds;
        return this;
    }

    public StrategyBuilder withMarketValue(String actualMarketValue) {
        this.actualMarketValue = actualMarketValue;
        return this;
    }

    public StrategyBuilder withWholeStrategyEntryValue(String wholeStrategyEntryValue) {
        this.wholeStrategyEntryValue = wholeStrategyEntryValue;
        return this;
    }

    public StrategyBuilder withWholeStrategyExitValue(String wholeStrategyExitValue) {
        this.wholeStrategyExitValue = wholeStrategyExitValue;
        return this;
    }

    public StrategyBuilder withWorstMarketValue(String lowestMarketValueInTheReport) {
        this.lowestMarketValueInTheReport = lowestMarketValueInTheReport;
        return this;
    }

    public StrategyBuilder withABuyStrategy(boolean isABuyStrategy) {
        this.isABuyStrategy = isABuyStrategy;
        return this;
    }

    public StrategyBuilder withAStrategySingleEntry(StrategySingleEntry strategySingleEntry) {
        if (strategySingleEntry.isEmpty()) {
            return this;
        }
        strategySingleEntryList.add(strategySingleEntry);
        return this;
    }

    public Strategy build() throws StrategyBuilderException {
        return new Strategy(currencyPair,
                wholeStrategyEntryValue,
                wholeStrategyExitValue,
                lowestMarketValueInTheReport,
                isABuyStrategy,
                pipsToBePayedToTheBroker,
                costOfTheRightCurrencyInPounds,
                actualMarketValue,
                strategySingleEntryList);
    }

}
