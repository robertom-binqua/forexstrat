package com.binqua.forexstrat.strategy;

import org.junit.Test;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.*;
import static com.binqua.forexstrat.strategy.CurrencyQuote.currencyQuoteOf;
import static com.binqua.forexstrat.strategy.DistanceInPips.aDistanceInPipsOf;
import static com.binqua.forexstrat.strategy.report.CurrencyQuoteGeneratorPipsScale.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CurrencyQuoteTest {

    @Test
    public void asBigDecimalRoundedTheAmountDependingOnTheCurrencyPair() {
        assertThat(currencyQuoteOf("1.20101111111", EUR_USD).asBigDecimal(), is(new BigDecimal("1.2010")));
        assertThat(currencyQuoteOf("1.20109", EUR_USD).asBigDecimal(), is(new BigDecimal("1.2011")));
        assertThat(currencyQuoteOf("1.20105", EUR_USD).asBigDecimal(), is(new BigDecimal("1.2011")));

        assertThat(currencyQuoteOf("1.20101111111", CAD_JPY).asBigDecimal(), is(new BigDecimal("1.20")));
        assertThat(currencyQuoteOf("1.209", CAD_JPY).asBigDecimal(), is(new BigDecimal("1.21")));
        assertThat(currencyQuoteOf("1.205", CAD_JPY).asBigDecimal(), is(new BigDecimal("1.21")));
        assertThat(currencyQuoteOf("1.204", CAD_JPY).asBigDecimal(), is(new BigDecimal("1.20")));
    }

    @Test
    public void absoluteDistanceInPipsFrom() {
        assertThat(currencyQuoteOf("1.2010", EUR_USD).absoluteDistanceInPipsFrom(currencyQuoteOf("1.2003", EUR_USD)), is(aDistanceInPipsOf(7)));
        assertThat(currencyQuoteOf("1.2010", EUR_USD).absoluteDistanceInPipsFrom(currencyQuoteOf("1.2013", EUR_USD)), is(aDistanceInPipsOf(3)));
    }

    @Test
    public void givenAPositiveNumberOfPipsThenAddPipsWork() {
        assertThat(currencyQuoteOf("1.2001", EUR_USD).addPips(2), is(currencyQuoteOf("1.2003", EUR_USD)));
    }

    @Test
    public void givenANegativeNumberOfPipsThenAddPipsWork() {
        assertThat(currencyQuoteOf("1.2001", EUR_USD).addPips(-2), is(currencyQuoteOf("1.1999", EUR_USD)));
    }

    @Test
    public void isBiggerThanInCaseOfBiggerValueIsFalse() {
        assertThat(currencyQuoteOf("1.2001", EUR_USD).isBiggerThan(currencyQuoteOf("1.2002", EUR_USD)), is(false));
    }

    @Test
    public void isBiggerThanInCaseOfLowerValueIsTrue() {
        assertThat(currencyQuoteOf("1.2003", EUR_USD).isBiggerThan(currencyQuoteOf("1.2002", EUR_USD)), is(true));
    }

    @Test
    public void isBiggerThanInCaseOfEqualValueIsTrue() {
        assertThat(currencyQuoteOf("1.2001", EUR_USD).isBiggerThan(currencyQuoteOf("1.2001", EUR_USD)), is(false));
    }

    @Test
    public void isBiggerOrEqualThanInCaseOfBiggerValueIsFalse() {
        assertThat(currencyQuoteOf("1.2001", EUR_USD).isBiggerOrEqualThan(currencyQuoteOf("1.2002", EUR_USD)), is(false));
    }

    @Test
    public void isBiggerOrEqualThanInCaseOfLowerValueIsTrue() {
        assertThat(currencyQuoteOf("1.2003", EUR_USD).isBiggerOrEqualThan(currencyQuoteOf("1.2002", EUR_USD)), is(true));
    }

    @Test
    public void isBiggerOrEqualThanInCaseOfEqualValueIsTrue() {
        assertThat(currencyQuoteOf("1.2001", EUR_USD).isBiggerOrEqualThan(currencyQuoteOf("1.2001", EUR_USD)), is(true));
    }

    @Test
    public void isLowerOrEqualThanInCaseOfBiggerValueIsTrue() {
        assertThat(currencyQuoteOf("1.2004", EUR_USD).isLowerOrEqualThan(currencyQuoteOf("1.2005", EUR_USD)), is(true));
    }

    @Test
    public void isLowerOrEqualThanInCaseOfLowerValueIsFalse() {
        assertThat(currencyQuoteOf("1.2001", EUR_USD).isLowerOrEqualThan(currencyQuoteOf("1.2000", EUR_USD)), is(false));
    }

    @Test
    public void isLowerOrEqualThanInCaseOfEqualValueIsTrue() {
        assertThat(currencyQuoteOf("1.2001", EUR_USD).isLowerOrEqualThan(currencyQuoteOf("1.2001", EUR_USD)), is(true));
    }

    @Test
    public void isLowerThanInCaseOfBiggerValueIsTrue() {
        assertThat(currencyQuoteOf("1.2004", EUR_USD).isLowerThan(currencyQuoteOf("1.2005", EUR_USD)), is(true));
    }

    @Test
    public void isLowerThanInCaseOfLowerValueIsFalse() {
        assertThat(currencyQuoteOf("1.2001", EUR_USD).isLowerThan(currencyQuoteOf("1.2000", EUR_USD)), is(false));
    }

    @Test
    public void isLowerThanInCaseOfEqualValueIsFalse() {
        assertThat(currencyQuoteOf("1.2001", EUR_USD).isLowerThan(currencyQuoteOf("1.2001", EUR_USD)), is(false));
    }

    @Test
    public void asIntInCaseOfnonJPY() {
        assertThat(currencyQuoteOf("1.2001", EUR_USD).asInt(), is(12001));
        assertThat(currencyQuoteOf("1.200111111", EUR_USD).asInt(), is(12001));
        assertThat(currencyQuoteOf("1.200199999", EUR_USD).asInt(), is(12002));
        assertThat(currencyQuoteOf("1.20015555", EUR_USD).asInt(), is(12002));
        assertThat(currencyQuoteOf("1.20014444", EUR_USD).asInt(), is(12001));
    }

    @Test
    public void asIntInCaseOfJPY() {
        assertThat(currencyQuoteOf("1.20", AUD_JPY).asInt(), is(120));
        assertThat(currencyQuoteOf("1.20111111", AUD_JPY).asInt(), is(120));
        assertThat(currencyQuoteOf("1.2099999", AUD_JPY).asInt(), is(121));
        assertThat(currencyQuoteOf("1.205555", AUD_JPY).asInt(), is(121));
        assertThat(currencyQuoteOf("1.204444", AUD_JPY).asInt(), is(120));
    }

    @Test
    public void endsWith100() {
        assertThat(currencyQuoteOf("1.2000", EUR_USD).endsWith(ONE_HUNDRED), is(true));
        assertThat(currencyQuoteOf("1.2001", EUR_USD).endsWith(ONE_HUNDRED), is(false));
        assertThat(currencyQuoteOf("78.00", AUD_JPY).endsWith(ONE_HUNDRED), is(true));
        assertThat(currencyQuoteOf("78.01", AUD_JPY).endsWith(ONE_HUNDRED), is(false));
    }

    @Test
    public void endsWith50() {
        assertThat(currencyQuoteOf("1.2000", EUR_USD).endsWith(FIFTY), is(false));
        assertThat(currencyQuoteOf("1.2050", EUR_USD).endsWith(FIFTY), is(true));
        assertThat(currencyQuoteOf("1.2051", EUR_USD).endsWith(FIFTY), is(false));
        assertThat(currencyQuoteOf("78.00", AUD_JPY).endsWith(FIFTY), is(false));
        assertThat(currencyQuoteOf("78.50", AUD_JPY).endsWith(FIFTY), is(true));
        assertThat(currencyQuoteOf("78.51", AUD_JPY).endsWith(FIFTY), is(false));
    }

    @Test
    public void endsWith25() {
        assertThat(currencyQuoteOf("1.2000", EUR_USD).endsWith(TWENTY_FIVE), is(false));
        assertThat(currencyQuoteOf("1.2050", EUR_USD).endsWith(TWENTY_FIVE), is(false));
        assertThat(currencyQuoteOf("1.2051", EUR_USD).endsWith(TWENTY_FIVE), is(false));
        assertThat(currencyQuoteOf("1.2075", EUR_USD).endsWith(TWENTY_FIVE), is(true));
        assertThat(currencyQuoteOf("1.2025", EUR_USD).endsWith(TWENTY_FIVE), is(true));

        assertThat(currencyQuoteOf("78.00", AUD_JPY).endsWith(TWENTY_FIVE), is(false));
        assertThat(currencyQuoteOf("78.50", AUD_JPY).endsWith(TWENTY_FIVE), is(false));
        assertThat(currencyQuoteOf("78.51", AUD_JPY).endsWith(TWENTY_FIVE), is(false));
        assertThat(currencyQuoteOf("78.25", AUD_JPY).endsWith(TWENTY_FIVE), is(true));
        assertThat(currencyQuoteOf("78.75", AUD_JPY).endsWith(TWENTY_FIVE), is(true));

    }
}
