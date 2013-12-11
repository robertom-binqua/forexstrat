package com.binqua.forexstrat.feedreader.core.converter;

import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import com.binqua.forexstrat.feedreader.core.repositories.ModifiableCurrencyPairQuotationsRepository;
import org.junit.Test;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation.aCurrencyPairValue;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.EUR_GBP;
import static com.binqua.forexstrat.feedreader.core.converter.PriceOption.BUY;
import static com.binqua.forexstrat.feedreader.core.converter.PriceOption.SELL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EURToGBPCurrencyConverterTest {

    private final CurrencyPairQuotation eurGbpCurrencyPairQuotation = aCurrencyPairValue(EUR_GBP, "1.0000", "2.0000");
    private final ModifiableCurrencyPairQuotationsRepository modifiableCurrenciesQuoteRepository = mock(ModifiableCurrencyPairQuotationsRepository.class);

    private final EURToGBPCurrencyConverter eurToGBPCurrencyConverter = new EURToGBPCurrencyConverter(modifiableCurrenciesQuoteRepository);

    @Test
    public void buyValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(modifiableCurrenciesQuoteRepository.quoteFor(EUR_GBP)).thenReturn(eurGbpCurrencyPairQuotation);

        assertThat(eurToGBPCurrencyConverter.valueOf(BUY), is(new BigDecimal(eurGbpCurrencyPairQuotation.getBuyValue())));
    }

    @Test
    public void sellValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(modifiableCurrenciesQuoteRepository.quoteFor(EUR_GBP)).thenReturn(eurGbpCurrencyPairQuotation);

        assertThat(eurToGBPCurrencyConverter.valueOf(SELL), is(new BigDecimal(eurGbpCurrencyPairQuotation.getSellValue())));
    }
}
