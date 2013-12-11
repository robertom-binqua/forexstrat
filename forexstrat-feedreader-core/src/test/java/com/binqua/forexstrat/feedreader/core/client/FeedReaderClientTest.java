package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.actions.FeedReaderResponseAction;
import com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPairs;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;
import org.mockito.internal.verification.AtLeast;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.AUD_JPY;
import static org.mockito.Mockito.*;

public class FeedReaderClientTest {

    private final ClientCommands clientCommands = mock(ClientCommands.class);
    private final FeedReaderResponseAction feedReadResponseAction = mock(FeedReaderResponseAction.class);

    @Test
    public void ifLoginIsSuccessfulReadResponseIsPassedToFeedReadResponseAction() throws Exception {
        final FeedLoginResponse feedLoginResponse = aSuccessfulFeedLoginResponse();
        when(clientCommands.login()).thenReturn(feedLoginResponse);

        final FeedReadResponse readResponse = FeedReadResponse.feedReadSuccessful("AUD/JPY,1,2,3,4,5,6,7,8", new EnumBasedCurrencyPairs());
        when(clientCommands.read(feedLoginResponse)).thenReturn(readResponse);

        final FeedReaderClient feedReaderClient = new FeedReaderClient(clientCommands, feedReadResponseAction);
        final Thread runningThread = new Thread(feedReaderClient);
        runningThread.start();

        waitALittleBitToAllowTheCodeUnderTestToRun();

        runningThread.interrupt();
        runningThread.join();

        verify(feedReadResponseAction, new AtLeast(1)).actOn(readResponse);
    }

    @Test
    public void ifLoginIsNotSuccessfulThereIsNotInteractionWithFeedReadResponseActionEvenWithoutInterruptTheThread() throws Exception {
        final FeedLoginResponse abortedFeedLoginResponse = FeedLoginResponse.loginAborted(mock(HttpGet.class));
        when(clientCommands.login()).thenReturn(abortedFeedLoginResponse);

        final FeedReaderClient singleCurrencyFeedReader = new FeedReaderClient(clientCommands, feedReadResponseAction);
        final Thread runningThread = new Thread(singleCurrencyFeedReader);
        runningThread.start();

        waitALittleBitToAllowTheCodeUnderTestToRun();
        runningThread.join();

        verify(clientCommands).login();
        verifyNoMoreInteractions(clientCommands);
        verifyZeroInteractions(feedReadResponseAction);
    }

    @Test
    public void ifSecondLoginIsNotSuccessfulThereIsNotInteractionWithFeedReadResponseActionEvenWithoutInterruptTheThread() throws Exception {
        final FeedLoginResponse successfulFeedLoginResponse = aSuccessfulFeedLoginResponse();
        when(clientCommands.login()).thenReturn(successfulFeedLoginResponse);

        final FeedReadResponse feedReadAbortedResponse = FeedReadResponse.feedReadUnsuccessful(mock(HttpGet.class));
        when(clientCommands.read(successfulFeedLoginResponse)).thenReturn(feedReadAbortedResponse);

        final FeedLoginResponse secondFeedLoginResponse = FeedLoginResponse.loginAborted(mock(HttpGet.class));
        when(clientCommands.login()).thenReturn(secondFeedLoginResponse);

        final FeedReaderClient feedReaderClient = new FeedReaderClient(clientCommands, feedReadResponseAction);
        final Thread runningThread = new Thread(feedReaderClient);
        runningThread.start();

        waitALittleBitToAllowTheCodeUnderTestToRun();
        runningThread.join();

        verifyZeroInteractions(feedReadResponseAction);
    }

    @Test
    public void ifSecondLoginIsSuccessfulWeReadFeedAgainAndInvokeTheFeedReadResponseAction() throws Exception {
        final FeedLoginResponse successfulFeedLoginResponse = aSuccessfulFeedLoginResponse();
        when(clientCommands.login()).thenReturn(successfulFeedLoginResponse);

        final FeedReadResponse feedReadAbortedResponse = FeedReadResponse.feedReadUnsuccessful(mock(HttpGet.class));
        when(clientCommands.read(successfulFeedLoginResponse)).thenReturn(feedReadAbortedResponse);

        when(clientCommands.login()).thenReturn(successfulFeedLoginResponse);

        final FeedReadResponse successfulReadResponse = FeedReadResponse.feedReadSuccessful("AUD/JPY,1,2,3,4,5,6,7,8", new EnumBasedCurrencyPairs());
        when(clientCommands.read(successfulFeedLoginResponse)).thenReturn(successfulReadResponse);

        final FeedReaderClient feedReaderClient = new FeedReaderClient(clientCommands, feedReadResponseAction);
        final Thread runningThread = new Thread(feedReaderClient);
        runningThread.start();

        waitALittleBitToAllowTheCodeUnderTestToRun();

        runningThread.interrupt();
        runningThread.join();

        verify(feedReadResponseAction, new AtLeast(1)).actOn(successfulReadResponse);
    }

    private void waitALittleBitToAllowTheCodeUnderTestToRun() throws InterruptedException {
        Thread.sleep(100);
    }

    private FeedLoginResponse aSuccessfulFeedLoginResponse() {
        return FeedLoginResponse.loginSuccessful("loginSessionId", AUD_JPY);
    }
}
