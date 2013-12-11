package com.binqua.forexstrat.feedreader.core.converter;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.AUD_JPY;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.CAD_JPY;

public class AUDToCADCurrencyConverter extends AbstractCurrencyConverter {
    private CurrencyPairQuotationsRepository currenciesQuoteRepository;

    public AUDToCADCurrencyConverter(CurrencyPairQuotationsRepository currenciesQuoteRepository) {
        this.currenciesQuoteRepository = currenciesQuoteRepository;
    }

    BigDecimal sellValue() throws CurrencyPairQuotationNotAvailableException {
        return calculateIt(currenciesQuoteRepository.quoteFor(AUD_JPY).getSellValue(), currenciesQuoteRepository.quoteFor(CAD_JPY).getSellValue());
    }

    BigDecimal buyValue() throws CurrencyPairQuotationNotAvailableException {
        return calculateIt(currenciesQuoteRepository.quoteFor(AUD_JPY).getBuyValue(), currenciesQuoteRepository.quoteFor(CAD_JPY).getBuyValue());
    }

    private BigDecimal calculateIt(String costToSell1AUDInJPY, String costToSell1CADInJPY) {
        return new BigDecimal(costToSell1AUDInJPY).divide(new BigDecimal(costToSell1CADInJPY), 4, BigDecimal.ROUND_HALF_UP).setScale(4, BigDecimal.ROUND_HALF_UP);
    }
}
