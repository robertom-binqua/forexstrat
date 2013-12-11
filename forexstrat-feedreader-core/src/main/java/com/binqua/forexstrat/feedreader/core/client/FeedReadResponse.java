package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPairs;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.http.client.methods.HttpGet;

import java.math.BigDecimal;

public class FeedReadResponse {
    private String buyValue;
    private String sellValue;
    private String averageValue;
    private final String response;
    private CurrencyPairs currencyPairs;

    private FeedReadResponse() {
        this.response = null;
    }

    public FeedReadResponse(String response, CurrencyPairs currencyPairs) {
        this.response = response;
        this.currencyPairs = currencyPairs;
        if (response.isEmpty()) {
            this.buyValue = "";
            this.sellValue = "";
            this.averageValue = "";
        } else {
            this.buyValue = calculateBuyValue();
            this.sellValue = calculateSellValue();
            this.averageValue = calculateAverageValue(buyValue, sellValue);
        }
    }

    public String getResponse() {
        return response;
    }

    public CurrencyPairQuotation currencyPairQuotation() {
        return CurrencyPairQuotation.aCurrencyPairValue(currencyPair(), sellValue, buyValue);
    }

    public boolean feedReadResponseUnsuccessful() {
        return response == null;
    }

    public static FeedReadResponse feedReadSuccessful(String response, CurrencyPairs currencyPairs) {
        return new FeedReadResponse(response, currencyPairs);
    }

    public static FeedReadResponse feedReadUnsuccessful(HttpGet getRead) {
        getRead.abort();
        return new FeedReadResponse();
    }

    public static FeedReadResponse feedReadUnsuccessful() {
        return new FeedReadResponse();
    }

    private String averageValue() {
        return this.averageValue.toString();
    }

    private String calculateAverageValue(String buyValue, String sellValue) {
        BigDecimal sellValueAsBigDecimal = new BigDecimal(sellValue);
        return new BigDecimal(buyValue).subtract(sellValueAsBigDecimal).divide(new BigDecimal(2), 4, BigDecimal.ROUND_HALF_UP).add(sellValueAsBigDecimal).toString();
    }

    private String calculateBuyValue() {
        String[] data = parseResponse();
        return new StringBuilder().append(data[4]).append(",").append(data[5]).toString().replace(",", "");
    }

    private CurrencyPair currencyPair() {
        return currencyPairs.currencyPairFrom(parseResponse()[0]);
    }

    private String calculateSellValue() {
        String[] data = parseResponse();
        return new StringBuilder().append(data[2]).append(",").append(data[3]).toString().replace(",", "");
    }

    private String[] parseResponse() {
        return response.split(",");
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(response, response);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(response);
    }

}
