package com.binqua.forexstrat.strategy;

import org.junit.Test;

import java.util.List;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.EUR_JPY;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.EUR_USD;
import static com.binqua.forexstrat.strategy.AmountWon.amountWon;
import static com.binqua.forexstrat.strategy.CurrencyQuote.currencyQuoteOf;
import static com.binqua.forexstrat.strategy.GlobalReportModelBuilder.aReportModel;
import static com.binqua.forexstrat.strategy.ReportModelEntryBuilder.aModelEntry;
import static com.binqua.forexstrat.strategy.StrategyBuilder.aStrategy;
import static com.binqua.forexstrat.strategy.report.ReportResolution.FIVE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GlobalReportModelTest {

    @Test
    public void reportInCaseMarketValueBiggerThanTheStrategyEntryValueAndNonJPYAsRightCurrency() throws StrategyBuilderException, GlobalReportModelException {
        StrategySingleEntry strategySingleEntry1 = new StrategySingleEntryBuilder().withValue("1.2500").withNumberOfContracts("1").build();
        StrategySingleEntry strategySingleEntry2 = new StrategySingleEntryBuilder().withValue("1.2501").withNumberOfContracts("1").build();

        GlobalReportModelImpl reportModel = aReportModel()
                .withHighestMarketValueInTheReport("1.2510")
                .withLowestMarketValueInTheReport("1.2495")
                .withResolution(FIVE)
                .withStrategy(aStrategy()
                        .withMarketValue("1.2503")
                        .withWholeStrategyEntryValue("1.2502")
                        .withWholeStrategyExitValue("8888")
                        .withWorstMarketValue("7777")
                        .withABuyStrategy(true)
                        .withPipsToBePayedToTheBroker("2")
                        .withCostOfTheRightCurrencyInPounds("2")
                        .withAStrategySingleEntry(strategySingleEntry1)
                        .withAStrategySingleEntry(strategySingleEntry2)
                        .withCurrencyPair(EUR_USD)
                        .build())
                .build();

        assertThat(reportModel.getAllEntries().size(), is(7));

        List<ReportModelEntry> entries = reportModel.getAllEntries();

        assertThat(entries.get(0), is(aModelEntry().with(amountWon("300")).with(currencyQuoteOf("1.2510", EUR_USD)).withAHighlightedValue(false).isTheCurrentMarketValue(false).isTheStrategyEntryPoint(false).build()));
        assertThat(entries.get(1), is(aModelEntry().with(amountWon("100")).with(currencyQuoteOf("1.2505", EUR_USD)).withAHighlightedValue(false).isTheCurrentMarketValue(false).isTheStrategyEntryPoint(false).build()));

    }

    @Test
    public void reportInCaseMarketValueBiggerThanTheStrategyEntryValueAndJPYAsRightCurrency() throws StrategyBuilderException, GlobalReportModelException {
        StrategySingleEntry strategySingleEntry1 = new StrategySingleEntryBuilder().withValue("101.64").withNumberOfContracts("1").build();
        GlobalReportModelImpl reportModel = aReportModel()
                .withHighestMarketValueInTheReport("101.70")
                .withLowestMarketValueInTheReport("101.60")
                .withResolution(FIVE)
                .withStrategy(aStrategy()
                        .withMarketValue("101.68")
                        .withWholeStrategyEntryValue("101.66")
                        .withWholeStrategyExitValue("8888")
                        .withWorstMarketValue("7777")
                        .withABuyStrategy(false)
                        .withPipsToBePayedToTheBroker("2")
                        .withCostOfTheRightCurrencyInPounds("0.0078")
                        .withAStrategySingleEntry(strategySingleEntry1)
                        .withCurrencyPair(EUR_JPY)
                        .build())
                .build();

        List<ReportModelEntry> entries = reportModel.getAllEntries();

        assertThat(reportModel.getAllEntries().size(), is(5));

        assertThat(entries.get(0), is(aModelEntry().with(amountWon("-62.40")).with(currencyQuoteOf("101.70", EUR_JPY)).withAHighlightedValue(false).isTheCurrentMarketValue(false).isTheStrategyEntryPoint(false).build()));
        assertThat(entries.get(1), is(aModelEntry().with(amountWon("-46.80")).with(currencyQuoteOf("101.68", EUR_JPY)).withAHighlightedValue(false).isTheCurrentMarketValue(true).isTheStrategyEntryPoint(false).build()));
        assertThat(entries.get(2), is(aModelEntry().with(amountWon("-31.20")).with(currencyQuoteOf("101.66", EUR_JPY)).withAHighlightedValue(false).isTheCurrentMarketValue(false).isTheStrategyEntryPoint(true).build()));
        assertThat(entries.get(3), is(aModelEntry().with(amountWon("-23.40")).with(currencyQuoteOf("101.65", EUR_JPY)).withAHighlightedValue(false).isTheCurrentMarketValue(false).isTheStrategyEntryPoint(false).build()));
        assertThat(entries.get(4), is(aModelEntry().with(amountWon("15.60")).with(currencyQuoteOf("101.60", EUR_JPY)).withAHighlightedValue(false).isTheCurrentMarketValue(false).isTheStrategyEntryPoint(false).build()));

    }

}
