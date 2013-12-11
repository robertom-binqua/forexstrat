package com.binqua.forexstrat.feedreader.core.actions.implementation;

import com.binqua.forexstrat.feedreader.core.client.FeedReadResponse;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.ModifiableCurrencyPairQuotationsRepository;
import org.junit.Test;
import org.mockito.Mockito;

import static com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation.aCurrencyPairValue;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.EUR_GBP;
import static org.mockito.Mockito.*;

public class CurrencyPairQuotationsRepositoryUpdaterActionTest {

    private final CurrencyPairQuotation theCurrencyPairQuotation = aCurrencyPairValue(EUR_GBP, "2.0000", "2.000");

    private ModifiableCurrencyPairQuotationsRepository modifiableCurrenciesQuoteRepository = Mockito.mock(ModifiableCurrencyPairQuotationsRepository.class);
    private FeedReadResponse feedReadResponse = Mockito.mock(FeedReadResponse.class);

    @Test
    public void updatesTheCurrenciesQuoteRepositoryIfResponseIsSuccessfulAndNotEmpty() {
        CurrencyPairQuotationsRepositoryUpdaterAction currenciesQuoteRepositoryUpdaterAction = new CurrencyPairQuotationsRepositoryUpdaterAction(modifiableCurrenciesQuoteRepository);

        when(feedReadResponse.feedReadResponseUnsuccessful()).thenReturn(false);
        when(feedReadResponse.getResponse()).thenReturn(aNonEmptyString());
        when(feedReadResponse.currencyPairQuotation()).thenReturn(theCurrencyPairQuotation);

        currenciesQuoteRepositoryUpdaterAction.actOn(feedReadResponse);

        verify(modifiableCurrenciesQuoteRepository).save(theCurrencyPairQuotation);

    }

    @Test
    public void doesNotUpdateTheCurrenciesQuoteRepositoryIfResponseIsNotSuccessful() {
        CurrencyPairQuotationsRepositoryUpdaterAction currenciesQuoteRepositoryUpdaterAction = new CurrencyPairQuotationsRepositoryUpdaterAction(modifiableCurrenciesQuoteRepository);

        when(feedReadResponse.feedReadResponseUnsuccessful()).thenReturn(true);

        currenciesQuoteRepositoryUpdaterAction.actOn(feedReadResponse);

        verifyZeroInteractions(modifiableCurrenciesQuoteRepository);

    }

    @Test
    public void doesNotUpdateTheCurrenciesQuoteRepositoryIfResponseIsEmpty() {
        CurrencyPairQuotationsRepositoryUpdaterAction currenciesQuoteRepositoryUpdaterAction = new CurrencyPairQuotationsRepositoryUpdaterAction(modifiableCurrenciesQuoteRepository);

        when(feedReadResponse.feedReadResponseUnsuccessful()).thenReturn(false);
        when(feedReadResponse.getResponse()).thenReturn("");

        currenciesQuoteRepositoryUpdaterAction.actOn(feedReadResponse);

        verifyZeroInteractions(modifiableCurrenciesQuoteRepository);

    }

    private String aNonEmptyString() {
        return "something";
    }
}
