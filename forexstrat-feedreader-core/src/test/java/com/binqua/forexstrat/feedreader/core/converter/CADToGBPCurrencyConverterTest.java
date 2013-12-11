package com.binqua.forexstrat.feedreader.core.converter;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import org.junit.Test;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation.aCurrencyPairValue;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.CAD_JPY;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.GBP_JPY;
import static com.binqua.forexstrat.feedreader.core.converter.PriceOption.BUY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CADToGBPCurrencyConverterTest {

    private final CurrencyPairQuotationsRepository currenciesQuoteRepository = mock(CurrencyPairQuotationsRepository.class);

    private final CurrencyPairQuotation cadJpyCurrencyPairQuotation = aCurrencyPairValue(CAD_JPY, "1.5000", "3.000");
    private final CurrencyPairQuotation gbpJpyCurrencyPairQuotation = aCurrencyPairValue(GBP_JPY, "3.000", "12.000");

    private final CADToGBPCurrencyConverter cadToGbpCurrencyConverter = new CADToGBPCurrencyConverter(currenciesQuoteRepository);

    @Test
    public void buyValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(currenciesQuoteRepository.quoteFor(CAD_JPY)).thenReturn(cadJpyCurrencyPairQuotation);
        when(currenciesQuoteRepository.quoteFor(GBP_JPY)).thenReturn(gbpJpyCurrencyPairQuotation);

        assertThat(cadToGbpCurrencyConverter.valueOf(BUY), is(new BigDecimal("0.2500")));
    }

    @Test
    public void sellValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(currenciesQuoteRepository.quoteFor(CAD_JPY)).thenReturn(cadJpyCurrencyPairQuotation);
        when(currenciesQuoteRepository.quoteFor(GBP_JPY)).thenReturn(gbpJpyCurrencyPairQuotation);

        assertThat(cadToGbpCurrencyConverter.valueOf(PriceOption.SELL), is(new BigDecimal("0.5000")));
    }
}