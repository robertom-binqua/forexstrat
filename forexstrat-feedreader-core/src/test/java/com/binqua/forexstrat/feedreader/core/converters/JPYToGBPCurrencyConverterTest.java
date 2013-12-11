package com.binqua.forexstrat.feedreader.core.converters;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import org.junit.Test;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation.aCurrencyPairValue;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.GBP_JPY;
import static com.binqua.forexstrat.feedreader.core.converters.PriceOption.BUY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JPYToGBPCurrencyConverterTest {

    private final CurrencyPairQuotationsRepository currenciesQuoteRepository = mock(CurrencyPairQuotationsRepository.class);

    private final CurrencyPairQuotation gbpJpyCurrencyPairQuotation = aCurrencyPairValue(GBP_JPY, "2.0000", "2.000");


    private final JPYToGBPCurrencyConverter jpyToGbpCurrencyConverter = new JPYToGBPCurrencyConverter(currenciesQuoteRepository);

    @Test
    public void buyValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(currenciesQuoteRepository.quoteFor(GBP_JPY)).thenReturn(gbpJpyCurrencyPairQuotation);

        assertThat(jpyToGbpCurrencyConverter.valueOf(BUY), is(new BigDecimal("0.5000")));
    }

    @Test
    public void sellValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(currenciesQuoteRepository.quoteFor(GBP_JPY)).thenReturn(gbpJpyCurrencyPairQuotation);

        assertThat(jpyToGbpCurrencyConverter.valueOf(PriceOption.SELL), is(new BigDecimal("0.5000")));
    }
}