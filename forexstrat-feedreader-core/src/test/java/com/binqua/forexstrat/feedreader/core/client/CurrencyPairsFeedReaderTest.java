package com.binqua.forexstrat.feedreader.core.client;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPairs;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class CurrencyPairsFeedReaderTest {

    private Configuration feedReaderConfiguration = mock(Configuration.class);
    private CurrencyPairs currencyPairs = mock(CurrencyPairs.class);
    private CurrencyPairFeedReader currencyPairFeedReader = mock(CurrencyPairFeedReader.class);
    private CurrencyPair firstNonDerivedCurrencyPair = mock(CurrencyPair.class);
    private CurrencyPair secondNonDerivedCurrencyPair = mock(CurrencyPair.class);

    private CurrencyPairsFeedReader currencyPairsFeedReader = new CurrencyPairsFeedReader(
            feedReaderConfiguration,
            currencyPairs,
            currencyPairFeedReader);

    @Test
    public void givenFeedReaderHasNotToRunThenNothingHappens() {
        when(feedReaderConfiguration.feedReaderHasToRun()).thenReturn(false);
        verifyNoMoreInteractions(currencyPairFeedReader);
    }

    @Test
    public void givenFeedReaderHasToRunThenFeedIsReadForEachNonDerivedCurrencyPair() {
        when(feedReaderConfiguration.feedReaderHasToRun()).thenReturn(true);
        when(currencyPairs.nonDerivedValues()).thenReturn(new CurrencyPair[]{firstNonDerivedCurrencyPair, secondNonDerivedCurrencyPair});

        currencyPairsFeedReader.start();

        verify(currencyPairFeedReader).readFeedFor(firstNonDerivedCurrencyPair);
        verify(currencyPairFeedReader).readFeedFor(secondNonDerivedCurrencyPair);
    }

    @Test
    public void givenFeedReaderHasToRunWhenThereAreNotNonDerivedCurrencyPairThenNothingHappens() {
        when(feedReaderConfiguration.feedReaderHasToRun()).thenReturn(true);
        when(currencyPairs.nonDerivedValues()).thenReturn(new CurrencyPair[0]);

        currencyPairsFeedReader.start();

         verifyNoMoreInteractions(currencyPairFeedReader);
    }
    
     @Test
    public void stopShouldStopReadingFeed() {
        when(feedReaderConfiguration.feedReaderHasToRun()).thenReturn(true);
        when(currencyPairs.nonDerivedValues()).thenReturn(new CurrencyPair[0]);

        currencyPairsFeedReader.stop();

        verify(currencyPairFeedReader).stopReadingFeed();
    }
}
