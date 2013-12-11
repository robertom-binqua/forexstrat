package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.actions.FeedReaderResponseAction;
import org.apache.http.client.HttpClient;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CurrencyPairFeedReaderTest {

    private ClientCommandsFactory clientCommandsFactory = mock(ClientCommandsFactory.class);
    private HttpClientManager httpClientManager = mock(HttpClientManager.class);
    private ClientRunner clientRunner = mock(ClientRunner.class);
    private FeedReaderResponseAction firstAction = mock(FeedReaderResponseAction.class);
    private FeedReaderResponseAction secondAction = mock(FeedReaderResponseAction.class);

    private CurrencyPairFeedReader currencyPairFeedReader = new CurrencyPairFeedReader(clientRunner,
            clientCommandsFactory,
            httpClientManager,
            firstAction,
            secondAction);

    @Test
    public void stopReadingFeedShouldShoutDownHttpClientManagerAndClientManager() {
        currencyPairFeedReader.stopReadingFeed();

        verify(httpClientManager).shutdown();
        verify(clientRunner).stop();
    }

    @Test
    public void readFeedForRunsTheClient() {
        final HttpClient theHttpClient = mock(HttpClient.class);
        when(httpClientManager.createAHttpClient()).thenReturn(theHttpClient);

        final CurrencyPair aCurrencyPair = mock(CurrencyPair.class);
        final ClientCommands theClientCommands = mock(ClientCommands.class);
        when(clientCommandsFactory.createClientCommandsWith(theHttpClient, aCurrencyPair)).thenReturn(theClientCommands);

        currencyPairFeedReader.readFeedFor(aCurrencyPair);

        verify(clientRunner).run(theClientCommands, new FeedReaderResponseAction[]{firstAction, secondAction});
    }

}
