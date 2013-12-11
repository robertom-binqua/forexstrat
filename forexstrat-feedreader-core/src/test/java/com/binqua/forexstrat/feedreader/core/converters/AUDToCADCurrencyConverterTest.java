package com.binqua.forexstrat.feedreader.core.converters;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import org.junit.Test;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation.aCurrencyPairValue;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.AUD_JPY;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.CAD_JPY;
import static com.binqua.forexstrat.feedreader.core.converters.PriceOption.BUY;
import static com.binqua.forexstrat.feedreader.core.converters.PriceOption.SELL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AUDToCADCurrencyConverterTest {

    private final CurrencyPairQuotationsRepository currenciesQuoteRepository = mock(CurrencyPairQuotationsRepository.class);
    private final CurrencyPairQuotation audJpyCurrencyPairQuotation = aCurrencyPairValue(AUD_JPY, "3.0000", "6.000");
    private final CurrencyPairQuotation cadJpyCurrencyPairQuotation = aCurrencyPairValue(CAD_JPY, "1.5000", "2.000");

    private final AUDToCADCurrencyConverter audToCadCurrencyConverter = new AUDToCADCurrencyConverter(currenciesQuoteRepository);

    @Test
    public void buyValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(currenciesQuoteRepository.quoteFor(AUD_JPY)).thenReturn(audJpyCurrencyPairQuotation);
        when(currenciesQuoteRepository.quoteFor(CAD_JPY)).thenReturn(cadJpyCurrencyPairQuotation);

        assertThat(audToCadCurrencyConverter.valueOf(BUY), is(new BigDecimal("3.0000")));
    }

    @Test
    public void sellValueIsCorrect() throws CurrencyPairQuotationNotAvailableException, CurrencyConverterException {
        when(currenciesQuoteRepository.quoteFor(AUD_JPY)).thenReturn(audJpyCurrencyPairQuotation);
        when(currenciesQuoteRepository.quoteFor(CAD_JPY)).thenReturn(cadJpyCurrencyPairQuotation);

        assertThat(audToCadCurrencyConverter.valueOf(SELL), is(new BigDecimal("2.0000")));
    }
}