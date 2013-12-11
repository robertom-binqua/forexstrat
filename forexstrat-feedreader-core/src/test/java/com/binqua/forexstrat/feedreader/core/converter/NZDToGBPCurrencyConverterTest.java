package com.binqua.forexstrat.feedreader.core.converter;

import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import com.binqua.forexstrat.feedreader.core.repositories.ModifiableCurrencyPairQuotationsRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation.aCurrencyPairValue;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.GBP_USD;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.NZD_USD;
import static com.binqua.forexstrat.feedreader.core.converter.PriceOption.BUY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class NZDToGBPCurrencyConverterTest {
    private final ModifiableCurrencyPairQuotationsRepository modifiableCurrenciesQuoteRepository = Mockito.mock(ModifiableCurrencyPairQuotationsRepository.class);
    private final CurrencyPairQuotation nzdUsdCurrencyPairQuotation = aCurrencyPairValue(NZD_USD, "2.0000", "2.000");
    private final CurrencyPairQuotation gbpUsdCurrencyPairQuotation = aCurrencyPairValue(GBP_USD, "0.500", "0.500");

    private final NZDToGBPCurrencyConverter nzdToGbpCurrencyConverter = new NZDToGBPCurrencyConverter(modifiableCurrenciesQuoteRepository);

    @Test
    public void buyValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(modifiableCurrenciesQuoteRepository.quoteFor(NZD_USD)).thenReturn(nzdUsdCurrencyPairQuotation);

        when(modifiableCurrenciesQuoteRepository.quoteFor(GBP_USD)).thenReturn(gbpUsdCurrencyPairQuotation);

        assertThat(nzdToGbpCurrencyConverter.valueOf(BUY), is(new BigDecimal("4.0000")));
    }

    @Test
    public void sellValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(modifiableCurrenciesQuoteRepository.quoteFor(NZD_USD)).thenReturn(nzdUsdCurrencyPairQuotation);

        when(modifiableCurrenciesQuoteRepository.quoteFor(GBP_USD)).thenReturn(gbpUsdCurrencyPairQuotation);

        assertThat(nzdToGbpCurrencyConverter.valueOf(PriceOption.SELL), is(new BigDecimal("4.0000")));
    }
}

