package com.binqua.forexstrat.feedreader.core.external;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.model.impl.Currency;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.converters.CurrenciesConverter;
import com.binqua.forexstrat.feedreader.core.converters.CurrencyConverter;
import com.binqua.forexstrat.feedreader.core.converters.CurrencyConverterException;
import com.binqua.forexstrat.feedreader.core.converters.PriceOption;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class CurrencyQuotesUpdaterJobBU {

    private CurrencyPairQuotationsRepository currenciesQuoteRepository = mock(CurrencyPairQuotationsRepository.class);
    private CurrenciesConverter currenciesConverter = mock(CurrenciesConverter.class);
    private CurrencyQuoteListener currencyQuoteListener = mock(CurrencyQuoteListener.class);
    private CurrencyConverter currencyConverter = mock(CurrencyConverter.class);
    private CurrencyPair currencyPair = mock(CurrencyPair.class);


    @Test
    @Ignore
    public void x() throws CurrencyConverterException {

        when(currencyPair.secondCurrency()).thenReturn(Currency.JPY);

        when(currenciesConverter.converterFor(Currency.JPY)).thenReturn(currencyConverter);

        when(currencyQuoteListener.priceValueOption()).thenReturn(PriceOption.BUY);

        BigDecimal AUD_JPYBuyPriceOption = BigDecimal.ONE;

        when(currencyConverter.valueOf(PriceOption.BUY)).thenReturn(AUD_JPYBuyPriceOption);

        CurrencyQuotesUpdaterJob currencyQuotesUpdaterJob = new CurrencyQuotesUpdaterJob(currencyPair, currenciesConverter, currenciesQuoteRepository);

        currencyQuotesUpdaterJob.addCurrencyPairQuoteListener(currencyQuoteListener);

        currencyQuotesUpdaterJob.run();

        verify(currencyQuoteListener).update(AUD_JPYBuyPriceOption);
    }
}
