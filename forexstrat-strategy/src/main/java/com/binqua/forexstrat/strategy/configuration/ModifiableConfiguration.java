package com.binqua.forexstrat.strategy.configuration;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;

public interface ModifiableConfiguration extends Configuration {
    void addStrategyStartValue(String s);

    void addIsASellStrategy(boolean priceOption);

    void addPipsToBePaidToTheBroker(String pipsToBePaidToTheBroker);

    void addRetracedToValue(SelectionConfiguration retracedToValue);

    void addMarketWorstCaseValueConfiguration(SelectionConfiguration marketWorstCaseValue);

    void addEntry(StrategySingleEntryConfiguration strategyConfigurationEntry);

    void addCostOfTheSecondCurrencyInPounds(String costOfTheSecondCurrencyInPounds);

    void addCurrencyPair(CurrencyPair currencyPair);

    void addReportConfiguration(ReportConfiguration reportConfiguration);
}
