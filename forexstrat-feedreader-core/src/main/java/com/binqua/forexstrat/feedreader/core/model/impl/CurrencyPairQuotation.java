package com.binqua.forexstrat.feedreader.core.model.impl;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.math.BigDecimal;

public class CurrencyPairQuotation {
    private String buyValue;
    private CurrencyPair currencyPair;
    private String sellValue;

    private CurrencyPairQuotation(CurrencyPair currencyPair, String sellValue, String buyValue) {
        this.currencyPair = currencyPair;
        this.sellValue = sellValue;
        this.buyValue = buyValue;
    }

    public String getBuyValue() {
        return buyValue;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public BigDecimal getBuyValueTemp() {
        return new BigDecimal(buyValue);
    }

    public BigDecimal getSellValueTemp() {
        return new BigDecimal(sellValue);
    }

    public String getSellValue() {
        return sellValue;
    }

    public String getAverageValue() {
        if (isUnavailable()) {
            return null;
        }
        BigDecimal sellValueAsBigDecimal = new BigDecimal(sellValue);
        return new BigDecimal(buyValue).subtract(sellValueAsBigDecimal).divide(new BigDecimal(2), 4, BigDecimal.ROUND_HALF_UP).add(sellValueAsBigDecimal).toString();
    }

    public boolean isUnavailable() {
        return buyValue == null && sellValue == null;
    }

    public static CurrencyPairQuotation aCurrencyPairValue(CurrencyPair currencyPair, String sellValue, String buyValue) {
        return new CurrencyPairQuotation(currencyPair, sellValue, buyValue);
    }

    public static CurrencyPairQuotation aCurrencyPairValue(CurrencyPair currencyPair, BigDecimal sellValue, BigDecimal buyValue) {
        return new CurrencyPairQuotation(currencyPair, sellValue.toPlainString(), buyValue.toPlainString());
    }

    public static CurrencyPairQuotation anUnavailableCurrencyPairQuotation(CurrencyPair currencyPair) {
        return new CurrencyPairQuotation(currencyPair, null, null);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
