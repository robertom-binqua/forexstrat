package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPairs;
import com.binqua.forexstrat.feedreader.core.support.Support;
import com.google.common.base.Joiner;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.verification.AtLeast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.EUR_USD;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ApacheHttpClientFeedReaderClientTest {

    private static final int TEST_SERVER_PORT = 8080;
    private static final String SERVER_CONTEXT = "test";
    private static final String FEED_URL_HOST = "http://localhost:" + TEST_SERVER_PORT;
    private static final String URL_HOST_PLUS_PREFIX = FEED_URL_HOST + "/" + SERVER_CONTEXT;
    private static final String THE_USER = "theUser";
    private static final String THE_USER_PASSWORD = "theUserPassword";

    private FakeFeedServer fakeFeedServer;
    private Thread theFeedReaderClientThread;

    @Before
    public void setUp() {
        fakeFeedServer = new FakeFeedServer();
    }

    @After
    public void tearDown() throws InterruptedException {
        if (theFeedReaderClientThread != null) {
            theFeedReaderClientThread.interrupt();
            sleepForSeconds(1);
        }
        fakeFeedServer.stop();
    }

    @Test
    public void givenThatTheFeedReaderServerIsUpAndFeedResponseIsEmptyAfterTrimmingItThanResponseIsSuccessful() throws IOException, InterruptedException {
        final String expectedLoginResponse = THE_USER + ":" + THE_USER_PASSWORD + ":rates:1324041751649";
        fakeFeedServer.registerRequestResponsePair("u=" + THE_USER + "&p=" + THE_USER_PASSWORD + "&q=rates&c=" + EUR_USD.asString() + "&f=csv&s=n", expectedLoginResponse);
        final String expectedFeedReadResponse = "   \r\n";
        fakeFeedServer.registerRequestResponsePair("id=" + expectedLoginResponse, expectedFeedReadResponse);

        fakeFeedServer.start();

        final Configuration feedReaderConfigurationMock = mock(Configuration.class);
        when(feedReaderConfigurationMock.serverUrl()).thenReturn(URL_HOST_PLUS_PREFIX);
        when(feedReaderConfigurationMock.user()).thenReturn(THE_USER);
        when(feedReaderConfigurationMock.password()).thenReturn(THE_USER_PASSWORD);
        when(feedReaderConfigurationMock.numberOfSecondsBeforeRetry()).thenReturn(1);

        final ClientCommands feedReaderClient = new ApacheHttpClientFeedReaderClient(feedReaderConfigurationMock, createHttpClientForTesting(), EUR_USD, mock(Support.class), new EnumBasedCurrencyPairs());
        final RunnableFeedReaderClientToLoginAndThenGetResponse runnable = new RunnableFeedReaderClientToLoginAndThenGetResponse(feedReaderClient);

        runInASeparateThreadAndStopItIfIsNotFinishedInSeconds(3000, runnable);

        assertThat("login should not be aborted" + fakeFeedServer, runnable.feedLoginResponse().loginHasBeenAborted(), is(false));
        assertThat("feed reader response should be successful." + fakeFeedServer, runnable.feedReadResponse().feedReadResponseUnsuccessful(), is(false));
        assertThat("feed reader response should be empty." + fakeFeedServer, runnable.feedReadResponse().getResponse(), is(""));
    }

    private void forTheInterruptToTakeEffectWaitForSeconds(int seconds) throws InterruptedException {
        sleepForSeconds(seconds);
    }

    @Test
    public void givenTheServerIsUpAndFeedResponseStartsWithCurrencyPairComma13DigitsAndCommaThanResponseIsSuccessful() throws IOException, InterruptedException {
        final String expectedLoginResponse = THE_USER + ":" + THE_USER_PASSWORD + ":rates:1324041751649";
        fakeFeedServer.registerRequestResponsePair("u=" + THE_USER + "&p=" + THE_USER_PASSWORD + "&q=rates&c=" + EUR_USD.asString() + "&f=csv&s=n", expectedLoginResponse);
        final String nonTrimmedFeedReadResponse = EUR_USD.asString() + ",1326205942749,118.,915,118.,925,118.652,119.079,118.793 ";
        fakeFeedServer.registerRequestResponsePair("id=" + expectedLoginResponse, nonTrimmedFeedReadResponse);

        fakeFeedServer.start();

        final Configuration feedReaderConfigurationMock = mock(Configuration.class);
        when(feedReaderConfigurationMock.serverUrl()).thenReturn(URL_HOST_PLUS_PREFIX);
        when(feedReaderConfigurationMock.user()).thenReturn(THE_USER);
        when(feedReaderConfigurationMock.password()).thenReturn(THE_USER_PASSWORD);
        when(feedReaderConfigurationMock.numberOfSecondsBeforeRetry()).thenReturn(1);

        final ClientCommands feedReaderClient = new ApacheHttpClientFeedReaderClient(feedReaderConfigurationMock, createHttpClientForTesting(), EUR_USD, mock(Support.class), new EnumBasedCurrencyPairs());
        final RunnableFeedReaderClientToLoginAndThenGetResponse runnable = new RunnableFeedReaderClientToLoginAndThenGetResponse(feedReaderClient);

        runInASeparateThreadAndStopItIfIsNotFinishedInSeconds(3, runnable);

        assertThat("login should not be aborted" + fakeFeedServer, runnable.feedLoginResponse().loginHasBeenAborted(), is(false));
        assertThat("feed reader response should be successful." + fakeFeedServer, runnable.feedReadResponse().feedReadResponseUnsuccessful(), is(false));
        assertThat("feed reader response is wrong." + fakeFeedServer, runnable.feedReadResponse().getResponse(), is(nonTrimmedFeedReadResponse.trim()));
    }

    @Test
    public void givenTheFeedServerIsUpAndFeedResponseDoesNotStartWithCurrencyPairComma13DigitsAndCommaThanResponseIsUnsuccessful() throws IOException, InterruptedException {
        final String expectedLoginResponse = THE_USER + ":" + THE_USER_PASSWORD + ":rates:1324041751649";
        fakeFeedServer.registerRequestResponsePair("u=" + THE_USER + "&p=" + THE_USER_PASSWORD + "&q=rates&c=" + EUR_USD.asString() + "&f=csv&s=n", expectedLoginResponse);
        final String expectedFeedReadResponse = "EUR/USDUSD/JPYGBP/USD....";
        fakeFeedServer.registerRequestResponsePair("id=" + expectedLoginResponse, expectedFeedReadResponse);

        fakeFeedServer.start();

        final Configuration feedReaderConfigurationMock = mock(Configuration.class);
        when(feedReaderConfigurationMock.serverUrl()).thenReturn(URL_HOST_PLUS_PREFIX);
        when(feedReaderConfigurationMock.user()).thenReturn(THE_USER);
        when(feedReaderConfigurationMock.password()).thenReturn(THE_USER_PASSWORD);
        when(feedReaderConfigurationMock.numberOfSecondsBeforeRetry()).thenReturn(1);

        final Support supportMock = mock(Support.class);
        final ClientCommands feedReaderClient = new ApacheHttpClientFeedReaderClient(feedReaderConfigurationMock, createHttpClientForTesting(), EUR_USD, supportMock, new EnumBasedCurrencyPairs());
        final RunnableFeedReaderClientToLoginAndThenGetResponse runnable = new RunnableFeedReaderClientToLoginAndThenGetResponse(feedReaderClient);

        runInASeparateThreadAndStopItIfIsNotFinishedInSeconds(3, runnable);

        assertThat("login has been aborted" + fakeFeedServer, runnable.feedLoginResponse().loginHasBeenAborted(), is(false));
        assertThat("feed reader response unsuccessful" + fakeFeedServer, runnable.feedReadResponse().feedReadResponseUnsuccessful(), is(true));
        assertThat("feed reader response" + fakeFeedServer, runnable.feedReadResponse().getResponse(), is(CoreMatchers.<Object>nullValue()));

        verify(supportMock).info("Unsuccessful response EUR/USDUSD/JPYGBP/USD.... from " + URL_HOST_PLUS_PREFIX + "?id=" + THE_USER + ":" + THE_USER_PASSWORD + ":rates:1324041751649");
    }

    @Test
    public void givenTheFeedServerIsUpThanLoginResponseIsCorrect() throws IOException, InterruptedException {
        final String expectedLoginResponse = THE_USER + ":" + THE_USER_PASSWORD + ":rates:1324041751649";
        fakeFeedServer.registerRequestResponsePair("u=" + THE_USER + "&p=" + THE_USER_PASSWORD + "&q=rates&c=EUR/USD&f=csv&s=n", expectedLoginResponse);

        fakeFeedServer.start();

        final Configuration feedReaderConfigurationMock = mock(Configuration.class);
        when(feedReaderConfigurationMock.serverUrl()).thenReturn(URL_HOST_PLUS_PREFIX);
        when(feedReaderConfigurationMock.user()).thenReturn(THE_USER);
        when(feedReaderConfigurationMock.password()).thenReturn(THE_USER_PASSWORD);
        when(feedReaderConfigurationMock.numberOfSecondsBeforeRetry()).thenReturn(1);

        final ClientCommands feedReaderClient = new ApacheHttpClientFeedReaderClient(feedReaderConfigurationMock, createHttpClientForTesting(), EUR_USD, mock(Support.class), new EnumBasedCurrencyPairs());
        final RunnableFeedReaderClientToLogin runnable = new RunnableFeedReaderClientToLogin(feedReaderClient);

        runInASeparateThreadAndStopItIfIsNotFinishedInSeconds(3, runnable);

        assertThat("login response" + fakeFeedServer, runnable.loginResponse().getResponse(), is(expectedLoginResponse));
        assertThat("login response currency pair", runnable.loginResponse().getCurrencyPair(), (Matcher<? super CurrencyPair>) is(EUR_USD));
    }

    private void runInASeparateThreadAndStopItIfIsNotFinishedInSeconds(int seconds, Runnable runnable) throws InterruptedException {
        theFeedReaderClientThread = new Thread(runnable);
        theFeedReaderClientThread.start();
        theFeedReaderClientThread.join(seconds * 1000);

        theFeedReaderClientThread.interrupt();
        forTheInterruptToTakeEffectWaitForSeconds(1);
    }


    @Test
    public void givenTheFeedServerIsNotStartedThanLoginTriesEveryNumberOfSecondsSpecifiedByTheConfiguration() throws IOException, InterruptedException {
        final Support support = mock(Support.class);

        final Configuration feedReaderConfigurationMock = mock(Configuration.class);
        when(feedReaderConfigurationMock.serverUrl()).thenReturn(URL_HOST_PLUS_PREFIX);
        when(feedReaderConfigurationMock.user()).thenReturn(THE_USER);
        when(feedReaderConfigurationMock.password()).thenReturn(THE_USER_PASSWORD);
        when(feedReaderConfigurationMock.numberOfSecondsBeforeRetry()).thenReturn(1);

        final ClientCommands feedReaderClient = new ApacheHttpClientFeedReaderClient(feedReaderConfigurationMock, createHttpClientForTesting(), EUR_USD, support, new EnumBasedCurrencyPairs());
        final RunnableFeedReaderClientToLogin runnable = new RunnableFeedReaderClientToLogin(feedReaderClient);

        runInASeparateThreadAndStopItIfIsNotFinishedInSeconds(3, runnable);

        verify(support, new AtLeast(2)).feedLoginProblem("Problem connecting to " + URL_HOST_PLUS_PREFIX + "?u=" + THE_USER + "&p=" + THE_USER_PASSWORD + "&q=rates&c=EUR/USD&f=csv&s=n. Retrying every 1 secs.", "Connection to " + FEED_URL_HOST + " refused");
    }

    @Test
    public void givenTheFeedLoginThreadHasBeenInterruptedBeforeTheFeedServerIsUpThanLoginWillBeAborted() throws IOException, InterruptedException {
        final Support support = mock(Support.class);

        final Configuration feedReaderConfigurationMock = mock(Configuration.class);
        when(feedReaderConfigurationMock.serverUrl()).thenReturn(URL_HOST_PLUS_PREFIX);
        when(feedReaderConfigurationMock.user()).thenReturn(THE_USER);
        when(feedReaderConfigurationMock.password()).thenReturn(THE_USER_PASSWORD);
        when(feedReaderConfigurationMock.numberOfSecondsBeforeRetry()).thenReturn(1);

        final ClientCommands feedReaderClient = new ApacheHttpClientFeedReaderClient(feedReaderConfigurationMock, createHttpClientForTesting(), EUR_USD, support, new EnumBasedCurrencyPairs());
        final RunnableFeedReaderClientToLogin runnableFeedReaderClient = new RunnableFeedReaderClientToLogin(feedReaderClient);

        runInASeparateThreadAndStopItIfIsNotFinishedInSeconds(3, runnableFeedReaderClient);

        assertThat("loginHasBeenAborted",runnableFeedReaderClient.loginResponse().loginHasBeenAborted(), is(equalTo(true)));

        verify(support, new AtLeast(1)).feedLoginProblem("Problem connecting to " + URL_HOST_PLUS_PREFIX + "?u=" + THE_USER + "&p=" + THE_USER_PASSWORD + "&q=rates&c=EUR/USD&f=csv&s=n. Retrying every 1 secs.", "Connection to " + FEED_URL_HOST + " refused");
    }

    class RunnableFeedReaderClientToLoginAndThenGetResponse implements Runnable {
        private ClientCommands feedReaderClient;
        private FeedReadResponse readResponse;
        private FeedLoginResponse feedLoginResponse;

        RunnableFeedReaderClientToLoginAndThenGetResponse(ClientCommands feedReaderClient) {
            this.feedReaderClient = feedReaderClient;
        }

        public void run() {
            feedLoginResponse = feedReaderClient.login();
            readResponse = feedReaderClient.read(feedLoginResponse);
        }

        FeedReadResponse feedReadResponse() {
            return readResponse;
        }

        FeedLoginResponse feedLoginResponse() {
            return feedLoginResponse;
        }

    }

    class RunnableFeedReaderClientToLogin implements Runnable {
        private ClientCommands feedReaderClient;
        private FeedLoginResponse feedLoginResponse;

        RunnableFeedReaderClientToLogin(ClientCommands feedReaderClient) {
            this.feedReaderClient = feedReaderClient;
        }

        public void run() {
            feedLoginResponse = feedReaderClient.login();
        }

        FeedLoginResponse loginResponse() {
            return feedLoginResponse;
        }

    }

    private HttpClient createHttpClientForTesting() {
        final PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
        cm.setMaxTotal(10);

        return new DefaultHttpClient(cm);
    }

    private void sleepForSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

    static class FakeFeedServer {
        private final java.util.Map<String, String> registeredRequestResponsePairs = new HashMap<String, String>();
        private final java.util.ArrayList<String> receivedRequest = new ArrayList<String>();
        private HttpServer server;

        public void registerRequestResponsePair(String request, String response) {
            registeredRequestResponsePairs.put(request, response);
        }

        public void start() throws IOException {
            server = HttpServer.create(new InetSocketAddress(TEST_SERVER_PORT), 0);
            server.createContext("/" + SERVER_CONTEXT, new TwoHundredResponseHandler());
            server.setExecutor(null);
            server.start();
        }

        public void stop() throws InterruptedException {
            if (server != null) {
                server.stop(1);
                Thread.sleep(1000);
            }
            receivedRequest.clear();
            registeredRequestResponsePairs.clear();
        }

        class TwoHundredResponseHandler implements HttpHandler {
            public void handle(HttpExchange t) throws IOException {
                final URI uriRequest = t.getRequestURI();
                String uriQueryRequest = uriRequest.getQuery();
                receivedRequest.add(uriQueryRequest);
                String response = registeredRequestResponsePairs.get(uriQueryRequest);
                int httpCode = 200;
                if (response == null) {
                    response = "Not response registered for request " + uriQueryRequest + " " + this;
                    httpCode = 400;
                }
                t.sendResponseHeaders(httpCode, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        @Override
        public String toString() {
            return "FakeFeedServer request --> response :\n" + Joiner.on("\n").withKeyValueSeparator(" --> ").join(registeredRequestResponsePairs.entrySet().iterator()) + "\n" +
                    "received requests:\n " + Joiner.on("\n").join(receivedRequest);
        }
    }


}
