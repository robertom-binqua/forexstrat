package com.binqua.forexstrat.feedreader.core.converter;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.CAD_JPY;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.GBP_JPY;

public class CADToGBPCurrencyConverter extends AbstractCurrencyConverter {
    private CurrencyPairQuotationsRepository currenciesQuoteRepository;

    public CADToGBPCurrencyConverter(CurrencyPairQuotationsRepository currenciesQuoteRepository) {
        this.currenciesQuoteRepository = currenciesQuoteRepository;
    }

    BigDecimal sellValue() throws CurrencyPairQuotationNotAvailableException {
        return calculate(currenciesQuoteRepository.quoteFor(CAD_JPY).getSellValue(), currenciesQuoteRepository.quoteFor(GBP_JPY).getSellValue());
    }

    BigDecimal buyValue() throws CurrencyPairQuotationNotAvailableException {
        return calculate(currenciesQuoteRepository.quoteFor(CAD_JPY).getBuyValue(), currenciesQuoteRepository.quoteFor(GBP_JPY).getBuyValue());
    }

    private BigDecimal calculate(String costOfSelling1CADInJPY, String costOfSelling1GBPInJPY) throws CurrencyPairQuotationNotAvailableException {
        return new BigDecimal(costOfSelling1CADInJPY).divide(new BigDecimal(costOfSelling1GBPInJPY), 4, BigDecimal.ROUND_HALF_UP).setScale(4);
    }
}
