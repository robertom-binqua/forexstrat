package com.binqua.forexstrat.feedreader.core.converter;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.model.impl.Currency;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static com.binqua.forexstrat.feedreader.core.model.impl.Currency.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

public class ToPoundsConverterTest {

    private final CurrencyPairQuotationsRepository currenciesQuoteRepository = mock(CurrencyPairQuotationsRepository.class);

    @Test
    public void AUDConverterIsRegistered() {
        ToPoundsConverter toPoundsConverter = new ToPoundsConverter(currenciesQuoteRepository);
        assertThat(toPoundsConverter.converterFor(AUD), is(CoreMatchers.<Object>instanceOf(AUDToGBPCurrencyConverter.class)));
    }

    @Test
    public void EURConverterIsRegistered() {
        ToPoundsConverter toPoundsConverter = new ToPoundsConverter(currenciesQuoteRepository);
        assertThat(toPoundsConverter.converterFor(EUR), is(CoreMatchers.<Object>instanceOf(EURToGBPCurrencyConverter.class)));
    }

    @Test
    public void NZDConverterIsRegistered() {
        ToPoundsConverter toPoundsConverter = new ToPoundsConverter(currenciesQuoteRepository);
        assertThat(toPoundsConverter.converterFor(NZD), is(CoreMatchers.<Object>instanceOf(NZDToGBPCurrencyConverter.class)));
    }

    @Test
    public void USDConverterIsRegistered() {
        ToPoundsConverter toPoundsConverter = new ToPoundsConverter(currenciesQuoteRepository);
        assertThat(toPoundsConverter.converterFor(USD), is(CoreMatchers.<Object>instanceOf(USDToGBPCurrencyConverter.class)));
    }

    @Test
    public void JPYConverterIsRegistered() {
        ToPoundsConverter toPoundsConverter = new ToPoundsConverter(currenciesQuoteRepository);
        assertThat(toPoundsConverter.converterFor(Currency.JPY), is(CoreMatchers.<Object>instanceOf(JPYToGBPCurrencyConverter.class)));
    }

    @Test
    public void CADConverterIsRegistered() {
        ToPoundsConverter toPoundsConverter = new ToPoundsConverter(currenciesQuoteRepository);
        assertThat(toPoundsConverter.converterFor(Currency.CAD), is(CoreMatchers.<Object>instanceOf(CADToGBPCurrencyConverter.class)));
    }

    @Test
    public void throwsExceptionIfCannotFindConverter() {
        ToPoundsConverter toPoundsConverter = new ToPoundsConverter(currenciesQuoteRepository);
        try {
            toPoundsConverter.converterFor(null);
            fail(IllegalStateException.class + " should have been thrown");
        } catch (IllegalStateException ise) {
            assertThat(ise.getMessage(), is("No converter found for currency null. Converters available AUD CAD EUR GBP JPY NZD USD"));
        }

    }
}
