package com.binqua.forexstrat.strategy;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.strategy.report.CurrencyQuoteGeneratorPipsScale;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.math.BigDecimal;

import static com.binqua.forexstrat.strategy.DistanceInPips.aDistanceInPipsOf;
import static com.binqua.forexstrat.strategy.report.CurrencyQuoteGeneratorPipsScale.*;

public class CurrencyQuote {

    private final BigDecimal value;
    private final CurrencyPair currencyPair;

    private CurrencyQuote(String value, CurrencyPair currencyPair) {
        this(new BigDecimal(value), currencyPair);
    }

    private CurrencyQuote(BigDecimal value, CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
        this.value = value.setScale(currencyPair.numberOfDecimalDigits(), BigDecimal.ROUND_HALF_UP);
    }

    public CurrencyQuote addPips(int numberOfPipToAdd) {
        return new CurrencyQuote(value.add(new BigDecimal(numberOfPipToAdd).divide((BigDecimal.TEN).pow(currencyPair.numberOfDecimalDigits()))), currencyPair);
    }

    public static CurrencyQuote currencyQuoteOf(BigDecimal value, CurrencyPair currencyPair) {
        return new CurrencyQuote(value, currencyPair);
    }

    public static CurrencyQuote currencyQuoteOf(String value, CurrencyPair currencyPair) {
        return new CurrencyQuote(value, currencyPair);
    }

    public BigDecimal asBigDecimal() {
        return value;
    }

    public Boolean isBiggerOrEqualThan(CurrencyQuote currencyQuote) {
        return this.asBigDecimal().subtract(currencyQuote.asBigDecimal()).signum() >= 0;
    }

    public Boolean isLowerOrEqualThan(CurrencyQuote currencyQuote) {
        return this.asBigDecimal().subtract(currencyQuote.asBigDecimal()).signum() <= 0;
    }

    public Boolean isLowerThan(CurrencyQuote currencyQuote) {
        return this.asBigDecimal().subtract(currencyQuote.asBigDecimal()).signum() < 0;
    }

    public DistanceInPips absoluteDistanceInPipsFrom(CurrencyQuote currencyQuote) {
        final BigDecimal value0 = value.movePointRight(value.scale());
        final BigDecimal value1 = currencyQuote.value.movePointRight(currencyQuote.value.scale());
        return aDistanceInPipsOf(value0.subtract(value1).abs().intValue());
    }

    public boolean isBiggerThan(CurrencyQuote currencyQuote) {
        return this.asBigDecimal().subtract(currencyQuote.asBigDecimal()).signum() > 0;
    }

    public boolean endsWith(CurrencyQuoteGeneratorPipsScale scale) {
        int valueWithNoDecimalPoint = removeDecimalPoint(value);
        if (scale == ONE_HUNDRED) {
            return valueWithNoDecimalPoint % 100 == 0;
        }
        if (scale == FIFTY) {
            if (valueWithNoDecimalPoint % 100 == 0) {
                return false;
            }
            return valueWithNoDecimalPoint % 50 == 0;
        }
        if (scale == TWENTY_FIVE) {
            if (valueWithNoDecimalPoint % 100 == 0 || valueWithNoDecimalPoint % 50 == 0) {
                return false;
            }
            return valueWithNoDecimalPoint % 25 == 0;
        }
        return false;
    }

    private int removeDecimalPoint(BigDecimal value) {
        return value.movePointRight(currencyPair.numberOfDecimalDigits()).intValue();
    }

    public int asInt() {
        return value.movePointRight(currencyPair.numberOfDecimalDigits()).intValue();
    }

    public static CurrencyQuote currencyQuoteOf(int actualValue, CurrencyPair currencyPair) {
        return new CurrencyQuote(new BigDecimal(actualValue).movePointLeft(currencyPair.numberOfDecimalDigits()), currencyPair);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    public String toString() {
        return value.toPlainString();
    }
}
