package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.http.client.methods.HttpGet;

public class FeedLoginResponse {
    private CurrencyPair currencyPair;

    private final String response;

    private FeedLoginResponse() {
        this.response = null;
    }

    public String getResponse() {
        return response;
    }

    private FeedLoginResponse(String response, CurrencyPair currencyPair) {
        this.response = response;
        this.currencyPair = currencyPair;
    }

    public boolean loginHasBeenAborted() {
        return response == null;
    }

    public static FeedLoginResponse loginSuccessful(String response, CurrencyPair currencyPair) {
        return new FeedLoginResponse(response, currencyPair);
    }

    public static FeedLoginResponse loginAborted(HttpGet getLogin) {
        getLogin.abort();
        return new FeedLoginResponse();
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
