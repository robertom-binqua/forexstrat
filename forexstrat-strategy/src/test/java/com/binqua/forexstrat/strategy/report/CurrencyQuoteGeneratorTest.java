package com.binqua.forexstrat.strategy.report;

import com.binqua.forexstrat.strategy.*;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.EUR_GBP;
import static com.binqua.forexstrat.strategy.AmountWon.amountWon;
import static com.binqua.forexstrat.strategy.CurrencyQuote.currencyQuoteOf;
import static com.binqua.forexstrat.strategy.ReportModelEntryBuilder.aModelEntry;
import static com.binqua.forexstrat.strategy.report.CurrencyQuoteGeneratorBuilder.aCurrencyQuoteGenerator;
import static com.binqua.forexstrat.strategy.report.ReportResolution.FIVE;
import static com.binqua.forexstrat.strategy.report.ReportResolution.ONE;
import static com.binqua.forexstrat.strategy.report.ReportResolution.TWO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CurrencyQuoteGeneratorTest {

    @Test
    public void nextWithResolutionOf1() throws GlobalReportModelException {
        CurrencyQuoteGenerator currencyQuoteGenerator = aCurrencyQuoteGenerator()
                .withAmountWonFor1PipDecrease(amountWon("1.05"))
                .withAmountWonAtTheHighestMarketValueInTheReport(amountWon("110"))
                .withHighestMarketValueInTheReport(currencyQuoteOf("1.2490", EUR_GBP))
                .withLowestMarketValueInTheReport(currencyQuoteOf("1.2480", EUR_GBP))
                .withActualMarketValue(currencyQuoteOf("1.2481", EUR_GBP))
                .withStrategyEntryPoint(currencyQuoteOf("1.2488", EUR_GBP))
                .withCurrencyPair(EUR_GBP)
                .withResolution(ONE)
                .build();


        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2490", EUR_GBP)).with(amountWon("110")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2489", EUR_GBP)).with(amountWon("111.05")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2488", EUR_GBP)).with(amountWon("112.10")).isTheStrategyEntryPoint(true).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2487", EUR_GBP)).with(amountWon("113.15")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2486", EUR_GBP)).with(amountWon("114.20")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2485", EUR_GBP)).with(amountWon("115.25")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2484", EUR_GBP)).with(amountWon("116.30")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2483", EUR_GBP)).with(amountWon("117.35")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2482", EUR_GBP)).with(amountWon("118.40")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2481", EUR_GBP)).with(amountWon("119.45")).withAHighlightedValue(false).isTheCurrentMarketValue(true).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2480", EUR_GBP)).with(amountWon("120.50")).withAHighlightedValue(false).build()));

        assertThat(currencyQuoteGenerator.next(), IsNull.<Object>nullValue());
    }

    @Test
    public void nextWithResolutionOf2() throws GlobalReportModelException {
        CurrencyQuoteGenerator currencyQuoteGenerator = aCurrencyQuoteGenerator()
                .withAmountWonFor1PipDecrease(amountWon("1.05"))
                .withAmountWonAtTheHighestMarketValueInTheReport(amountWon("110"))
                .withHighestMarketValueInTheReport(currencyQuoteOf("1.2490", EUR_GBP))
                .withLowestMarketValueInTheReport(currencyQuoteOf("1.2480", EUR_GBP))
                .withActualMarketValue(currencyQuoteOf("1.2481", EUR_GBP))
                .withStrategyEntryPoint(currencyQuoteOf("1.2489", EUR_GBP))
                .withCurrencyPair(EUR_GBP)
                .withResolution(TWO)
                .build();

        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2490", EUR_GBP)).with(amountWon("110")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2489", EUR_GBP)).with(amountWon("111.05")).isTheStrategyEntryPoint(true).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2488", EUR_GBP)).with(amountWon("112.10")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2486", EUR_GBP)).with(amountWon("114.20")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2485", EUR_GBP)).with(amountWon("115.25")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2484", EUR_GBP)).with(amountWon("116.30")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2482", EUR_GBP)).with(amountWon("118.40")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2481", EUR_GBP)).with(amountWon("119.45")).isTheCurrentMarketValue(true).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2480", EUR_GBP)).with(amountWon("120.50")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), IsNull.<Object>nullValue());

    }

    @Test
    public void nextWithResolutionOf2AndANegativeAmountWon() throws GlobalReportModelException {
        CurrencyQuoteGenerator currencyQuoteGenerator = aCurrencyQuoteGenerator()
                .withAmountWonFor1PipDecrease(amountWon("-1.00"))
                .withAmountWonAtTheHighestMarketValueInTheReport(amountWon("110"))
                .withHighestMarketValueInTheReport(currencyQuoteOf("1.2490", EUR_GBP))
                .withLowestMarketValueInTheReport(currencyQuoteOf("1.2480", EUR_GBP))
                .withActualMarketValue(currencyQuoteOf("1.2481", EUR_GBP))
                .withStrategyEntryPoint(currencyQuoteOf("1.2489", EUR_GBP))
                .withCurrencyPair(EUR_GBP)
                .withResolution(TWO)
                .build();

        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2490", EUR_GBP)).with(amountWon("110")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2489", EUR_GBP)).with(amountWon("109.00")).isTheStrategyEntryPoint(true).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2488", EUR_GBP)).with(amountWon("108")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2486", EUR_GBP)).with(amountWon("106.00")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2485", EUR_GBP)).with(amountWon("105.00")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2484", EUR_GBP)).with(amountWon("104.00")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2482", EUR_GBP)).with(amountWon("102.00")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2481", EUR_GBP)).with(amountWon("101")).isTheCurrentMarketValue(true).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2480", EUR_GBP)).with(amountWon("100.00")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), IsNull.<Object>nullValue());

    }

    @Test
    public void nextWithResolutionOf5() throws GlobalReportModelException {

        CurrencyQuoteGenerator currencyQuoteGenerator = aCurrencyQuoteGenerator()
                .withAmountWonFor1PipDecrease(amountWon("-1.00"))
                .withAmountWonAtTheHighestMarketValueInTheReport(amountWon("110"))
                .withHighestMarketValueInTheReport(currencyQuoteOf("1.2450", EUR_GBP))
                .withLowestMarketValueInTheReport(currencyQuoteOf("1.2390", EUR_GBP))
                .withActualMarketValue(currencyQuoteOf("1.2444", EUR_GBP))
                .withStrategyEntryPoint(currencyQuoteOf("1.2419", EUR_GBP))
                .withCurrencyPair(EUR_GBP)
                .withResolution(FIVE)
                .build();

        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2450", EUR_GBP)).with(amountWon("110")).withAHighlightedValue(true).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2445", EUR_GBP)).with(amountWon("105")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2444", EUR_GBP)).with(amountWon("104")).isTheCurrentMarketValue(true).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2440", EUR_GBP)).with(amountWon("100")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2435", EUR_GBP)).with(amountWon("95")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2430", EUR_GBP)).with(amountWon("90")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2425", EUR_GBP)).with(amountWon("85")).withAHighlightedValue(true).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2420", EUR_GBP)).with(amountWon("80")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2419", EUR_GBP)).with(amountWon("79")).isTheStrategyEntryPoint(true).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2415", EUR_GBP)).with(amountWon("75")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2410", EUR_GBP)).with(amountWon("70")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2405", EUR_GBP)).with(amountWon("65")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2400", EUR_GBP)).with(amountWon("60")).withAHighlightedValue(true).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2395", EUR_GBP)).with(amountWon("55")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(aModelEntry().with(currencyQuoteOf("1.2390", EUR_GBP)).with(amountWon("50")).withAHighlightedValue(false).build()));
        assertThat(currencyQuoteGenerator.next(), is(IsNull.<Object>nullValue()));

    }

}
