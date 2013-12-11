package com.binqua.forexstrat.feedreader.core.converter;

import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair;
import com.binqua.forexstrat.feedreader.core.repositories.ModifiableCurrencyPairQuotationsRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation.aCurrencyPairValue;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.AUD_JPY;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.GBP_JPY;
import static com.binqua.forexstrat.feedreader.core.converter.PriceOption.BUY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class AUDToGBPCurrencyConverterTest {

    private final ModifiableCurrencyPairQuotationsRepository modifiableCurrenciesQuoteRepository = Mockito.mock(ModifiableCurrencyPairQuotationsRepository.class);
    private final CurrencyPairQuotation audJpyCurrencyPairQuotation = aCurrencyPairValue(AUD_JPY, "2.0000", "2.000");
    private final CurrencyPairQuotation gbpJpyCurrencyPairQuotation = aCurrencyPairValue(GBP_JPY, "0.500", "0.500");

    private final AUDToGBPCurrencyConverter audToGbpCurrencyConverter = new AUDToGBPCurrencyConverter(modifiableCurrenciesQuoteRepository);

    @Test
    public void buyValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(modifiableCurrenciesQuoteRepository.quoteFor(AUD_JPY)).thenReturn(audJpyCurrencyPairQuotation);

        when(modifiableCurrenciesQuoteRepository.quoteFor(EnumBasedCurrencyPair.GBP_JPY)).thenReturn(gbpJpyCurrencyPairQuotation);

        assertThat(audToGbpCurrencyConverter.valueOf(BUY), is(new BigDecimal("4.0000")));
    }

    @Test
    public void sellValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(modifiableCurrenciesQuoteRepository.quoteFor(AUD_JPY)).thenReturn(audJpyCurrencyPairQuotation);

        when(modifiableCurrenciesQuoteRepository.quoteFor(EnumBasedCurrencyPair.GBP_JPY)).thenReturn(gbpJpyCurrencyPairQuotation);

        assertThat(audToGbpCurrencyConverter.valueOf(PriceOption.SELL), is(new BigDecimal("4.0000")));
    }
}
