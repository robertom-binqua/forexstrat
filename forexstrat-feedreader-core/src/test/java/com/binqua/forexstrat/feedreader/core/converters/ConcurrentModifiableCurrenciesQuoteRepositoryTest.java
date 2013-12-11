package com.binqua.forexstrat.feedreader.core.converters;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.ConcurrentModifiableCurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import com.binqua.forexstrat.feedreader.core.repositories.ModifiableCurrencyPairQuotationsRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation.aCurrencyPairValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ConcurrentModifiableCurrenciesQuoteRepositoryTest {

    private final CurrencyPair aDerivedCurrencyPair = mock(CurrencyPair.class);
    private final CurrencyPair aNonDerivedCurrencyPair = mock(CurrencyPair.class);

    private ModifiableCurrencyPairQuotationsRepository repositoryForNonDeriveCurrencyPairs = mock(ModifiableCurrencyPairQuotationsRepository.class);
    private CurrencyPairQuotationsRepository repositoryForDeriveCurrencyPairs = mock(CurrencyPairQuotationsRepository.class);


    @Test
    public void aCurrencyPairIsSavedIfItIsNotDerived() throws CurrencyPairQuotationNotAvailableException {
        when(aNonDerivedCurrencyPair.isDerived()).thenReturn(false);

        ConcurrentModifiableCurrencyPairQuotationsRepository concurrentCurrenciesQuoteRepository = new ConcurrentModifiableCurrencyPairQuotationsRepository(new CurrencyPair[0], null, repositoryForNonDeriveCurrencyPairs);

        CurrencyPairQuotation aCurrencyPairQuotation = aCurrencyPairValue(aNonDerivedCurrencyPair, "1", "3");

        concurrentCurrenciesQuoteRepository.save(aCurrencyPairQuotation);

        verify(repositoryForNonDeriveCurrencyPairs).save(aCurrencyPairQuotation);
    }

    @Test
    public void saveThrowsIllegalArgumentExceptionIfCalledWithADerivedCurrencyPair() throws CurrencyPairQuotationNotAvailableException {
        when(aDerivedCurrencyPair.isDerived()).thenReturn(true);
        when(aDerivedCurrencyPair.asString()).thenReturn("XXX/YYY");

        ConcurrentModifiableCurrencyPairQuotationsRepository concurrentCurrenciesQuoteRepository = new ConcurrentModifiableCurrencyPairQuotationsRepository(new CurrencyPair[0], null, repositoryForNonDeriveCurrencyPairs);

        CurrencyPairQuotation aCurrencyPairQuotation = aCurrencyPairValue(aDerivedCurrencyPair, "1", "3");

        try {
            concurrentCurrenciesQuoteRepository.save(aCurrencyPairQuotation);
            fail(IllegalArgumentException.class + " should have be thrown");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), is("Please call this method with a non derived currencyPair: XXX/YYY is derived"));
        }

    }

    @Test
    public void quoteForWhenCurrencyPairIsDerived() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        ConcurrentModifiableCurrencyPairQuotationsRepository concurrentCurrenciesQuoteRepository = new ConcurrentModifiableCurrencyPairQuotationsRepository(new CurrencyPair[0], repositoryForDeriveCurrencyPairs, null);
        CurrencyPairQuotation aDerivedCurrencyPairQuotation = aCurrencyPairValue(aDerivedCurrencyPair, "1", "10");

        when(aDerivedCurrencyPair.isDerived()).thenReturn(true);
        when(repositoryForDeriveCurrencyPairs.quoteFor(aDerivedCurrencyPair)).thenReturn(aDerivedCurrencyPairQuotation);

        assertThat(concurrentCurrenciesQuoteRepository.quoteFor(aDerivedCurrencyPair), is(aDerivedCurrencyPairQuotation));
    }

    @Test
    public void quoteForWhenCurrencyPairIsNotDerived() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        ConcurrentModifiableCurrencyPairQuotationsRepository concurrentCurrenciesQuoteRepository = new ConcurrentModifiableCurrencyPairQuotationsRepository(new CurrencyPair[0], null, repositoryForNonDeriveCurrencyPairs);
        CurrencyPairQuotation aNonDerivedCurrencyPairQuotation = aCurrencyPairValue(aNonDerivedCurrencyPair, "1", "10");

        when(aNonDerivedCurrencyPair.isDerived()).thenReturn(false);
        when(repositoryForNonDeriveCurrencyPairs.quoteFor(aNonDerivedCurrencyPair)).thenReturn(aNonDerivedCurrencyPairQuotation);

        assertThat(concurrentCurrenciesQuoteRepository.quoteFor(aNonDerivedCurrencyPair), is(aNonDerivedCurrencyPairQuotation));
    }

    @Test
    public void getQuotes() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        CurrencyPair aDerivedCurrencyPair = Mockito.mock(CurrencyPair.class);
        CurrencyPair aNonDerivedCurrencyPair = Mockito.mock(CurrencyPair.class);

        ConcurrentModifiableCurrencyPairQuotationsRepository concurrentCurrenciesQuoteRepository = new ConcurrentModifiableCurrencyPairQuotationsRepository(
                new CurrencyPair[]{aDerivedCurrencyPair, aNonDerivedCurrencyPair},
                repositoryForDeriveCurrencyPairs,
                repositoryForNonDeriveCurrencyPairs);

        CurrencyPairQuotation aNonDerivedCurrencyPairQuotation = aCurrencyPairValue(aNonDerivedCurrencyPair, "1", "10");

        when(aNonDerivedCurrencyPair.isDerived()).thenReturn(false);
        when(repositoryForNonDeriveCurrencyPairs.quoteFor(aNonDerivedCurrencyPair)).thenReturn(aNonDerivedCurrencyPairQuotation);

        CurrencyPairQuotation aDerivedCurrencyPairQuotation = aCurrencyPairValue(aDerivedCurrencyPair, "2", "20");

        when(aDerivedCurrencyPair.isDerived()).thenReturn(true);
        when(repositoryForDeriveCurrencyPairs.quoteFor(aDerivedCurrencyPair)).thenReturn(aDerivedCurrencyPairQuotation);


        List<CurrencyPairQuotation> actualQuotes = concurrentCurrenciesQuoteRepository.quotes();

        assertThat(actualQuotes.size(), is(2));
        assertThat(concurrentCurrenciesQuoteRepository.quotes().contains(aNonDerivedCurrencyPairQuotation), is(true));
        assertThat(concurrentCurrenciesQuoteRepository.quotes().contains(aDerivedCurrencyPairQuotation), is(true));
    }

}
