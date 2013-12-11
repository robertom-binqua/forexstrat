package com.binqua.forexstrat.feedreader.core.converters;

import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import com.binqua.forexstrat.feedreader.core.repositories.ModifiableCurrencyPairQuotationsRepository;
import org.junit.Test;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation.aCurrencyPairValue;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.EUR_GBP;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.GBP_USD;
import static com.binqua.forexstrat.feedreader.core.converters.PriceOption.BUY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class USDToGBPCurrencyConverterTest {

    private final ModifiableCurrencyPairQuotationsRepository modifiableCurrenciesQuoteRepository = mock(ModifiableCurrencyPairQuotationsRepository.class);

    private final CurrencyPairQuotation gbpUsdCurrencyPairQuotation = aCurrencyPairValue(EUR_GBP, "2.0000", "2.000");

    private final USDToGBPCurrencyConverter usdToGbpCurrencyConverter = new USDToGBPCurrencyConverter(modifiableCurrenciesQuoteRepository);

    @Test
    public void buyValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(modifiableCurrenciesQuoteRepository.quoteFor(GBP_USD)).thenReturn(gbpUsdCurrencyPairQuotation);

        assertThat(usdToGbpCurrencyConverter.valueOf(BUY), is(new BigDecimal("0.5000")));
    }

    @Test
    public void sellValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(modifiableCurrenciesQuoteRepository.quoteFor(GBP_USD)).thenReturn(gbpUsdCurrencyPairQuotation);

        assertThat(usdToGbpCurrencyConverter.valueOf(PriceOption.SELL), is(new BigDecimal("0.5000")));
    }
}