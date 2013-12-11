package com.binqua.forexstrat.strategy;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import com.binqua.forexstrat.strategy.configuration.ConfigurationReader;
import com.binqua.forexstrat.strategy.configuration.StrategySingleEntryConfiguration;

import java.math.BigDecimal;

public class GlobalReportModelFromConfigurationBuilder {
    private ConfigurationReader configurationReader;
    private CurrencyPairQuotationsRepository currenciesQuoteRepository;

    private GlobalReportModelFromConfigurationBuilder() {
    }

    public GlobalReportModelImpl build() throws GlobalReportModelException {
        return GlobalReportModelBuilder.aReportModel().withStrategy(strategy())
                .withHighestMarketValueInTheReport(configurationReader.getReportConfiguration().maxMarketValue())
                .withLowestMarketValueInTheReport(configurationReader.getReportConfiguration().minMarketValue())
                .withResolution(configurationReader.getReportConfiguration().resolutionInPips())
                .build();
    }

    private Strategy strategy() throws GlobalReportModelException {
        StrategyBuilder strategyBuilder = StrategyBuilder.aStrategy()
                .withWholeStrategyEntryValue(configurationReader.wholeStrategyEntryValue())
                .withWholeStrategyExitValue(configurationReader.getRetracedToConfiguration().getMarketValue())
                .withPipsToBePayedToTheBroker(configurationReader.getPipsToBePaidToTheBroker())
                .withABuyStrategy(!configurationReader.isASellingStrategy());

        for (StrategySingleEntry strategySingleEntry : strategySingleEntryList(configurationReader)) {
            strategyBuilder = strategyBuilder.withAStrategySingleEntry(strategySingleEntry);
        }

        Strategy strategy = null;
        try {
            strategy = strategyBuilder
                    .withCostOfTheRightCurrencyInPounds(configurationReader.getCostOfTheRightCurrencyInPounds())
                    .withWorstMarketValue(configurationReader.getMarketWorstValueConfiguration().getMarketValue())
                    .withMarketValue(actualMarketValueText(configurationReader))
                    .withCurrencyPair(configurationReader.getCurrencyPair())
                    .build();
        } catch (StrategyBuilderException e) {
            throw new GlobalReportModelException(e.getMessage());
        }

        return strategy;
    }

    private StrategySingleEntry[] strategySingleEntryList(ConfigurationReader configurationReader) {
        int numberOfSingleStrategies = configurationReader.getStrategyConfiguration().size();
        StrategySingleEntry[] strategyEntries = new StrategySingleEntry[numberOfSingleStrategies];
        for (int strategyIndex = 0; strategyIndex < numberOfSingleStrategies; strategyIndex++) {
            StrategySingleEntryConfiguration singleStrategyConfiguration = configurationReader.getStrategyConfiguration().getSingleStrategyConfiguration(strategyIndex);
            strategyEntries[strategyIndex] = new StrategySingleEntry(new BigDecimal(singleStrategyConfiguration.getValue()), new BigDecimal(singleStrategyConfiguration.getNumberOfContracts()));
        }
        return strategyEntries;
    }

    private String actualMarketValueText(ConfigurationReader configurationReader) throws GlobalReportModelException {
        if (currenciesQuoteRepository == null) {
            return configurationReader.actualMarketValue();
        }
        CurrencyPairQuotation currencyPairQuotation = null;
        try {
            currencyPairQuotation = currenciesQuoteRepository.quoteFor(configurationReader.getCurrencyPair());
        } catch (CurrencyPairQuotationNotAvailableException e) {
            throw new GlobalReportModelException(e.getMessage());
        }
        if (configurationReader.isASellingStrategy()) {
            return currencyPairQuotation.getSellValue();
        }
        return currencyPairQuotation.getBuyValue();
    }

    public static GlobalReportModelFromConfigurationBuilder aReportModel() {
        return new GlobalReportModelFromConfigurationBuilder();

    }

    public GlobalReportModelFromConfigurationBuilder withConfiguration(ConfigurationReader configurationReader) {
        this.configurationReader = configurationReader;
        return this;
    }

    public GlobalReportModelFromConfigurationBuilder withCurrenciesQuoteRepository(CurrencyPairQuotationsRepository currenciesQuoteRepository) {
        this.currenciesQuoteRepository = currenciesQuoteRepository;
        return this;

    }
}
