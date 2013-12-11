package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPairs;
import com.binqua.forexstrat.feedreader.core.support.Support;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;
import java.util.regex.Pattern;

import static com.binqua.forexstrat.feedreader.core.client.FeedLoginResponse.loginAborted;
import static com.binqua.forexstrat.feedreader.core.client.FeedLoginResponse.loginSuccessful;
import static com.binqua.forexstrat.feedreader.core.client.FeedReadResponse.feedReadSuccessful;
import static com.binqua.forexstrat.feedreader.core.client.FeedReadResponse.feedReadUnsuccessful;

public class ApacheHttpClientFeedReaderClient implements ClientCommands {
    private final Configuration feedReaderConfiguration;
    private final HttpClient httpclient;
    private final CurrencyPair currencyPair;
    private final Support support;
    private final CurrencyPairs currencyPairs;

    public ApacheHttpClientFeedReaderClient(Configuration feedReaderConfiguration, HttpClient httpclient, CurrencyPair currencyPair, Support support, CurrencyPairs currencyPairs) {
        this.feedReaderConfiguration = feedReaderConfiguration;
        this.httpclient = httpclient;
        this.currencyPair = currencyPair;
        this.support = support;
        this.currencyPairs = currencyPairs;
    }

    public FeedLoginResponse login() {
        final HttpGet loginHttpGetCommand = loginHttpGetCommand(currencyPair);
        while (currentThreadHasNotBeInterrupted()) {
            try {
                return loginSuccessful(retrieveLoginResponse(httpclient, loginHttpGetCommand), currencyPair);
            } catch (IOException e) {
                logConnectionProblems(e);
                try {
                    waitForSeconds(feedReaderConfiguration.numberOfSecondsBeforeRetry());
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return loginAborted(loginHttpGetCommand);
                }
            }
        }
        return loginAborted(loginHttpGetCommand);
    }

    private void waitForSeconds(int numberOfSeconds) throws InterruptedException {
        Thread.sleep(numberOfSeconds * 1000);
    }

    public FeedReadResponse read(FeedLoginResponse loginResponse) {
        final HttpGet httpGetQuotes = getQuotes(loginResponse.getResponse());
        try {
            final String response = retrieveQuotesResponse(httpclient, httpGetQuotes);
            if (responseIsCorrect(response, loginResponse.getCurrencyPair())) {
                return feedReadSuccessful(response, currencyPairs);
            }
            support.info("Unsuccessful response " + response + " from " + httpGetQuotes.getURI());
            return feedReadUnsuccessful(httpGetQuotes);
        } catch (IOException e) {
            support.feedReadingProblem("Problem reading feed to " + feedReadUrl(loginResponse.getResponse()));
            return feedReadUnsuccessful(httpGetQuotes);
        }
    }

    private void logConnectionProblems(IOException e) {
        support.feedLoginProblem("Problem connecting to " + loginUrl(currencyPair) + ". Retrying every " + feedReaderConfiguration.numberOfSecondsBeforeRetry() + " secs.", e.getMessage());
    }

    private boolean currentThreadHasNotBeInterrupted() {
        return !Thread.currentThread().isInterrupted();
    }

    private boolean responseIsCorrect(String response, CurrencyPair currencyPair) {
        return response.length() == 0 || Pattern.compile(currencyPair.asString() + ",\\d{13},.*").matcher(response).matches();
    }

    private String retrieveQuotesResponse(HttpClient httpclient, HttpGet httpQuotesResponse) throws IOException {
        return httpclient.execute(httpQuotesResponse, new BasicResponseHandler()).replaceAll("\\r\\n", "").trim();
    }

    private HttpGet getQuotes(String sessionId) {
        return new HttpGet(feedReadUrl(sessionId));
    }

    private String feedReadUrl(String sessionId) {
        return feedReaderConfiguration.serverUrl() + "?id=" + sessionId;
    }

    private String retrieveLoginResponse(HttpClient httpclient, HttpGet httpget) throws IOException {
        return httpclient.execute(httpget, new BasicResponseHandler()).replaceAll("\\r\\n", "");
    }

    private HttpGet loginHttpGetCommand(CurrencyPair currencyPair) {
        return new HttpGet(loginUrl(currencyPair));
    }

    private String loginUrl(CurrencyPair currencyPair) {
        return feedReaderConfiguration.serverUrl() +
                "?u=" + feedReaderConfiguration.user() +
                "&p=" + feedReaderConfiguration.password() +
                "&q=rates&c=" + currencyPair.asString() + "&f=csv&s=n";
    }

}
