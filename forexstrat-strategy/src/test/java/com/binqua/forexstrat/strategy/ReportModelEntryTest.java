package com.binqua.forexstrat.strategy;

import org.junit.Test;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.EUR_GBP;
import static com.binqua.forexstrat.strategy.AmountWon.amountWon;
import static com.binqua.forexstrat.strategy.CurrencyQuote.currencyQuoteOf;
import static com.binqua.forexstrat.strategy.ReportModelEntryBuilder.aModelEntry;
import static com.binqua.forexstrat.strategy.ReportModelEntryBuilder.aNonValidReportModelEntry;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReportModelEntryTest {

    @Test
    public void asTextInCaseOfAWin() {
        assertThat(aModelEntry().with(amountWon("1.400")).with(currencyQuoteOf("1", EUR_GBP)).build().asString(), is("\u0040 1.0000 +1 \u00A3"));
    }

    @Test
    public void asTextInCaseOfALoss() {
        assertThat(aModelEntry().with(amountWon("-1.400")).with(currencyQuoteOf("1", EUR_GBP)).build().asString(), is("\u0040 1.0000 -1 \u00A3"));
    }

    @Test
    public void asTextInCaseOfAmountWonZero() {
        assertThat(aModelEntry().with(amountWon("0.00")).with(currencyQuoteOf("1", EUR_GBP)).build().asString(), is("\u0040 1.0000 0 \u00A3"));
    }

    @Test
    public void asTextInCaseOfANonValidReportModelEntry() {
        assertThat(aNonValidReportModelEntry().withMessage("text").build().asString(), is("text"));
    }
}
