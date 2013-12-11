package com.binqua.forexstrat.feedreader.core.util.renders;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPairs;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import org.junit.Test;
import org.objenesis.ObjenesisStd;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class XmlRenderCurrenciesQuoteRepositoryTest {

    @Test
    public void asXmlIsCorrectWhenThereIsOneCurrency() throws CurrencyPairQuotationNotAvailableException {

        String expectedXml = replaceSingleQuotes("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<quotes>\n" +
                "    <quote currency-pair='EUR/USD' buy='1.1200' sell='1.1100' average='1.1150' time='' />\n" +
                "</quotes>");

        CurrencyPairQuotationsRepository currenciesQuoteRepository = mock(CurrencyPairQuotationsRepository.class);
        CurrencyPairs currencyPairs = mock(CurrencyPairs.class);
        CurrencyPair theCurrencyPair = mock(CurrencyPair.class);

        when(currenciesQuoteRepository.quoteFor(theCurrencyPair)).thenReturn(CurrencyPairQuotation.aCurrencyPairValue(theCurrencyPair, "1.1100", "1.1200"));

        when(theCurrencyPair.asString()).thenReturn("EUR/USD");
        when(currencyPairs.asArray()).thenReturn(new CurrencyPair[]{theCurrencyPair});

        assertEquals("xml", expectedXml, new XmlRenderCurrenciesQuoteRepository(currenciesQuoteRepository, currencyPairs).render());
    }

    @Test
    public void asXmlIsCorrectWhenThereIsOneValidCurrencyAndOneNotAvailableCurrency() throws CurrencyPairQuotationNotAvailableException {

        String expectedXml = replaceSingleQuotes("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<quotes>\n" +
                "    <quote currency-pair='EUR/USD' buy='1.1200' sell='1.1100' average='1.1150' time='' />\n" +
                "    <quote currency-pair='EUR/JPN' />\n" +
                "</quotes>");

        CurrencyPairQuotationsRepository currenciesQuoteRepository = mock(CurrencyPairQuotationsRepository.class);
        CurrencyPairs currencyPairs = mock(CurrencyPairs.class);
        CurrencyPair theValidCurrencyPair = mock(CurrencyPair.class);
        CurrencyPair theUnavailableCurrencyPair = mock(CurrencyPair.class);


        when(theValidCurrencyPair.asString()).thenReturn("EUR/USD");
        when(theUnavailableCurrencyPair.asString()).thenReturn("EUR/JPN");

        when(currenciesQuoteRepository.quoteFor(theValidCurrencyPair)).thenReturn(CurrencyPairQuotation.aCurrencyPairValue(theValidCurrencyPair, "1.1100", "1.1200"));
        when(currenciesQuoteRepository.quoteFor(theUnavailableCurrencyPair)).thenThrow(aCurrencyQuoteNotAvailableException());

        when(currencyPairs.asArray()).thenReturn(new CurrencyPair[]{theValidCurrencyPair, theUnavailableCurrencyPair});

        assertEquals("xml", expectedXml, new XmlRenderCurrenciesQuoteRepository(currenciesQuoteRepository, currencyPairs).render());

    }

    private Throwable aCurrencyQuoteNotAvailableException() {
        return (CurrencyPairQuotationNotAvailableException) new ObjenesisStd().getInstantiatorOf(CurrencyPairQuotationNotAvailableException.class).newInstance();
    }


    private String replaceSingleQuotes(String content) {
        return content.replace("'", "\"");
    }
}
