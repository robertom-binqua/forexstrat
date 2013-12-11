package com.binqua.forexstrat.strategy;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import com.binqua.forexstrat.strategy.configuration.*;
import com.binqua.forexstrat.strategy.report.ReportResolution;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.EUR_USD;
import static com.binqua.forexstrat.strategy.AmountWon.amountWon;
import static com.binqua.forexstrat.strategy.CurrencyQuote.currencyQuoteOf;
import static com.binqua.forexstrat.strategy.ReportModelEntryBuilder.aModelEntry;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalReportModelFromConfigurationBuilderTest {

    private ConfigurationReader configurationReader = mock(ConfigurationReader.class);
    private ReportConfiguration reportConfiguration = mock(ReportConfiguration.class);

    @Test
    public void whenCurrenciesQuoteRepositoryIsNotSpecifiedBuilderWorks() throws GlobalReportModelException, CurrencyPairQuotationNotAvailableException, StrategyBuilderException {
        whenPartOfConfigurationReaderIsPopulated();
        when(configurationReader.actualMarketValue()).thenReturn("1.2503");

        GlobalReportModelImpl globalReportModel = GlobalReportModelFromConfigurationBuilder.aReportModel()
                .withConfiguration(configurationReader)
                .build();

        assertThat(globalReportModel.getAllEntries().size(), is(6));
        List<ReportModelEntry> entries = globalReportModel.getAllEntries();

        assertThat(entries.get(1), is(aModelEntry().with(amountWon("100")).with(currencyQuoteOf("1.2505", EUR_USD)).withAHighlightedValue(false).isTheCurrentMarketValue(false).isTheStrategyEntryPoint(false).build()));
        assertThat(entries.get(0), is(aModelEntry().with(amountWon("300")).with(currencyQuoteOf("1.2510", EUR_USD)).withAHighlightedValue(false).isTheCurrentMarketValue(false).isTheStrategyEntryPoint(false).build()));

    }

    private void whenPartOfConfigurationReaderIsPopulated() {
        StrategySingleEntryConfiguration strategyFirstEntry = new StrategySingleEntryConfiguration(0, 1, new SelectionConfiguration("1", "1.2500"));
        StrategySingleEntryConfiguration strategySecondEntry = new StrategySingleEntryConfiguration(1, 1, new SelectionConfiguration("2", "1.2501"));
        StrategyConfiguration strategyConfiguration = new StrategyConfiguration(Arrays.asList(strategyFirstEntry, strategySecondEntry));

        when(configurationReader.getCostOfTheRightCurrencyInPounds()).thenReturn("2");
        when(configurationReader.getReportConfiguration()).thenReturn(reportConfiguration);
        when(reportConfiguration.maxMarketValue()).thenReturn("1.2510");
        when(reportConfiguration.minMarketValue()).thenReturn("1.2495");
        when(reportConfiguration.resolutionInPips()).thenReturn(ReportResolution.FIVE);
        when(configurationReader.getCurrencyPair()).thenReturn(EUR_USD);
        when(configurationReader.getMarketWorstValueConfiguration()).thenReturn(new SelectionConfiguration("1", "7777"));
        when(configurationReader.getPipsToBePaidToTheBroker()).thenReturn("2");
        when(configurationReader.getRetracedToConfiguration()).thenReturn(new SelectionConfiguration("1", "8888"));
        when(configurationReader.getStrategyConfiguration()).thenReturn(strategyConfiguration);
        when(configurationReader.isASellingStrategy()).thenReturn(false);
        when(configurationReader.wholeStrategyEntryValue()).thenReturn("1.2503");
    }

    @Test
    public void whenCurrenciesQuoteRepositoryIsSpecifiedBuilderWorks() throws GlobalReportModelException, CurrencyPairQuotationNotAvailableException, StrategyBuilderException {

        whenPartOfConfigurationReaderIsPopulated();
        when(configurationReader.actualMarketValue()).thenReturn(null);

        CurrencyPairQuotationsRepository currenciesQuoteRepository = mock(CurrencyPairQuotationsRepository.class);
        when(currenciesQuoteRepository.quoteFor(EUR_USD)).thenReturn(CurrencyPairQuotation.aCurrencyPairValue(EUR_USD, "1.2503", "1.2503"));

        GlobalReportModelImpl globalReportModel = GlobalReportModelFromConfigurationBuilder.aReportModel()
                .withConfiguration(configurationReader)
                .withCurrenciesQuoteRepository(currenciesQuoteRepository)
                .build();

        assertThat(globalReportModel.getAllEntries().size(), is(6));

        List<ReportModelEntry> entries = globalReportModel.getAllEntries();

        assertThat(entries.get(0), is(aModelEntry().with(amountWon("300")).with(currencyQuoteOf("1.2510", EUR_USD)).withAHighlightedValue(false).isTheCurrentMarketValue(false).isTheStrategyEntryPoint(false).build()));
        assertThat(entries.get(1), is(aModelEntry().with(amountWon("100")).with(currencyQuoteOf("1.2505", EUR_USD)).withAHighlightedValue(false).isTheCurrentMarketValue(false).isTheStrategyEntryPoint(false).build()));

    }

}
