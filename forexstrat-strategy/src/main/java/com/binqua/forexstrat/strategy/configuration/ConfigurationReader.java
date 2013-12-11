package com.binqua.forexstrat.strategy.configuration;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;

public interface ConfigurationReader extends Configuration {

    String wholeStrategyEntryValue();

    boolean isASellingStrategy();

    SelectionConfiguration getRetracedToConfiguration();

    SelectionConfiguration getMarketWorstValueConfiguration();

    String getPipsToBePaidToTheBroker();

    StrategyConfiguration getStrategyConfiguration();

    String getCostOfTheRightCurrencyInPounds();

    CurrencyPair getCurrencyPair();

    ReportConfiguration getReportConfiguration();

    String actualMarketValue();

    String name();
}
