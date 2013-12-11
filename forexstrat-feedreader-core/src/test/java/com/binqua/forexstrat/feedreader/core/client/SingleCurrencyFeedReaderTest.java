package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPairs;
import com.binqua.forexstrat.feedreader.core.actions.FeedReaderResponseAction;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;
import org.mockito.internal.verification.AtLeast;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.AUD_JPY;
import static org.mockito.Mockito.*;

public class SingleCurrencyFeedReaderTest {

    @Test
    public void ifLoginIsSuccessfulReadResponseIsPassedToFeedReadResponseAction() throws Exception {
        ClientCommands feedReadClient = mock(ClientCommands.class);
        FeedReaderResponseAction feedReadResponseAction = mock(FeedReaderResponseAction.class);

        FeedLoginResponse feedLoginResponse = FeedLoginResponse.loginSuccessful("loginSessionId", AUD_JPY);
        when(feedReadClient.login()).thenReturn(feedLoginResponse);

        FeedReadResponse readResponse = FeedReadResponse.feedReadSuccessful("AUD/JPY,1,2,3,4,5,6,7,8", new EnumBasedCurrencyPairs());
        when(feedReadClient.read(feedLoginResponse)).thenReturn(readResponse);

        FeedReaderClient singleCurrencyFeedReader = new FeedReaderClient(feedReadClient, feedReadResponseAction);
        Thread runningThread = new Thread(singleCurrencyFeedReader);
        runningThread.start();

        Thread.sleep(500);

        runningThread.interrupt();
        runningThread.join();

        verify(feedReadResponseAction, new AtLeast(1)).actOn(readResponse);
    }

    @Test
    public void ifLoginIsNotSuccessfulThereIsNotInteractionWithFeedReadResponseActionEvenWithoutInterruptTheThread() throws Exception {
        ClientCommands feedReadClient = mock(ClientCommands.class);
        FeedReaderResponseAction feedReadResponseAction = mock(FeedReaderResponseAction.class);

        FeedLoginResponse abortedFeedLoginResponse = FeedLoginResponse.loginAborted(mock(HttpGet.class));
        when(feedReadClient.login()).thenReturn(abortedFeedLoginResponse);

        FeedReaderClient singleCurrencyFeedReader = new FeedReaderClient(feedReadClient, feedReadResponseAction);
        Thread runningThread = new Thread(singleCurrencyFeedReader);
        runningThread.start();

        Thread.sleep(500);
        runningThread.join();

        verify(feedReadClient).login();
        verifyNoMoreInteractions(feedReadClient);
        verifyZeroInteractions(feedReadResponseAction);
    }

    @Test
    public void ifSecondLoginIsNotSuccessfulThereIsNotInteractioWithFeedReadResponseActionEvenWithoutInterruptTheThread() throws Exception {
        ClientCommands feedReadClient = mock(ClientCommands.class);
        FeedReaderResponseAction feedReadResponseAction = mock(FeedReaderResponseAction.class);

        FeedLoginResponse successfulFeedLoginResponse = FeedLoginResponse.loginSuccessful("loginSessionId", AUD_JPY);
        when(feedReadClient.login()).thenReturn(successfulFeedLoginResponse);

        FeedReadResponse feedReadAbortedResponse = FeedReadResponse.feedReadUnsuccessful(mock(HttpGet.class));
        when(feedReadClient.read(successfulFeedLoginResponse)).thenReturn(feedReadAbortedResponse);

        FeedLoginResponse secondFeedLoginResponse = FeedLoginResponse.loginAborted(mock(HttpGet.class));
        when(feedReadClient.login()).thenReturn(secondFeedLoginResponse);

        FeedReaderClient singleCurrencyFeedReader = new FeedReaderClient(feedReadClient, feedReadResponseAction);
        Thread runningThread = new Thread(singleCurrencyFeedReader);
        runningThread.start();

        Thread.sleep(500);
        runningThread.join();

        verifyZeroInteractions(feedReadResponseAction);
    }

    @Test
    public void ifSecondLoginIsSuccessfulWeReadFeedAgainAndInvokeTheFeedReadResponseAction() throws Exception {
        ClientCommands feedReadClient = mock(ClientCommands.class);
        FeedReaderResponseAction feedReadResponseAction = mock(FeedReaderResponseAction.class);

        FeedLoginResponse successfulFeedLoginResponse = FeedLoginResponse.loginSuccessful("loginSessionId", AUD_JPY);
        when(feedReadClient.login()).thenReturn(successfulFeedLoginResponse);

        FeedReadResponse feedReadAbortedResponse = FeedReadResponse.feedReadUnsuccessful(mock(HttpGet.class));
        when(feedReadClient.read(successfulFeedLoginResponse)).thenReturn(feedReadAbortedResponse);

        when(feedReadClient.login()).thenReturn(successfulFeedLoginResponse);

        FeedReadResponse successfulReadResponse = FeedReadResponse.feedReadSuccessful("AUD/JPY,1,2,3,4,5,6,7,8", new EnumBasedCurrencyPairs());
        when(feedReadClient.read(successfulFeedLoginResponse)).thenReturn(successfulReadResponse);

        FeedReaderClient singleCurrencyFeedReader = new FeedReaderClient(feedReadClient, feedReadResponseAction);
        Thread runningThread = new Thread(singleCurrencyFeedReader);
        runningThread.start();

        Thread.sleep(500);

        runningThread.interrupt();
        runningThread.join();

        verify(feedReadResponseAction, new AtLeast(1)).actOn(successfulReadResponse);
    }
}
