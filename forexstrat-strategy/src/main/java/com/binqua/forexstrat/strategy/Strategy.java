package com.binqua.forexstrat.strategy;


import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;

import java.math.BigDecimal;
import java.util.List;

import static com.binqua.forexstrat.strategy.CurrencyQuote.currencyQuoteOf;

public class Strategy {

    private CurrencyQuote marketEntryValue;
    private CurrencyQuote lowestMarketValueToBeConsidered;
    private BigDecimal pipsToBePayedToTheBroker;
    private boolean isABuyStrategy;
    private BigDecimal costOfTheRightCurrencyInPounds;
    private CurrencyQuote marketExitValue;
    private StrategySingleEntry[] strategySingleEntries;
    private CurrencyQuote actualMarketValue;

    private CurrencyPair currencyPair;

    public Strategy(CurrencyPair currencyPair, String marketEntryValue, String marketExitValue, String lowestMarketValueToBeConsidered, boolean isABuyStrategy, String pipsToBePayedToTheBroker, String costOfTheRightCurrencyInPounds, String actualMarketValue, List<StrategySingleEntry> strategySingleEntries) throws StrategyBuilderException {
        this.currencyPair = currencyPair;
        this.marketEntryValue = currencyQuoteOf(checkThatItIsValid(marketEntryValue, "marketEntryValue"), currencyPair);
        this.marketExitValue = currencyQuoteOf(checkThatItIsValid(marketExitValue, "marketExitValue"), currencyPair);
        this.costOfTheRightCurrencyInPounds = checkThatItIsValid(costOfTheRightCurrencyInPounds, "costOfTheRightCurrencyInPounds");
        this.pipsToBePayedToTheBroker = checkThatItIsValid(pipsToBePayedToTheBroker, "pipsToBePayedToTheBroker");
        this.lowestMarketValueToBeConsidered = currencyQuoteOf(checkThatItIsValid(lowestMarketValueToBeConsidered, "lowestMarketValueToBeConsidered"), currencyPair);
        this.actualMarketValue = currencyQuoteOf(checkThatItIsValid(actualMarketValue, "actualMarketValue"), currencyPair);
        this.strategySingleEntries = strategySingleEntries.toArray(new StrategySingleEntry[strategySingleEntries.size()]);
        this.isABuyStrategy = isABuyStrategy;
    }

    public StrategySingleEntry[] singleEntriesList() {
        return strategySingleEntries;
    }

    public CurrencyPair currencyPair() {
        return currencyPair;
    }

    public CurrencyQuote marketEntryValue() {
        return marketEntryValue;
    }

    public CurrencyQuote marketExitValue() {
        return marketExitValue;
    }

    public int pipsToBePayedToTheBroker() {
        return pipsToBePayedToTheBroker.intValue();
    }

    public BigDecimal costOfTheRightCurrencyInPounds() {
        return costOfTheRightCurrencyInPounds;
    }

    public boolean isABuyStrategy() {
        return isABuyStrategy;
    }

    public CurrencyQuote lowestMarketValueInTheReport() {
        return lowestMarketValueToBeConsidered;
    }

    public CurrencyQuote actualMarketValue() {
        return actualMarketValue;
    }

    private BigDecimal checkThatItIsValid(String valueToBeChecked, String fieldInException) throws StrategyBuilderException {
        if (valueToBeChecked == null || valueToBeChecked.length() == 0) {
            throw new StrategyBuilderException("Please specified valid data for " + fieldInException);
        }
        try {
            return new BigDecimal(valueToBeChecked);
        } catch (NumberFormatException e) {
            throw new StrategyBuilderException("Please specified valid data for " + fieldInException);
        }
    }
}
