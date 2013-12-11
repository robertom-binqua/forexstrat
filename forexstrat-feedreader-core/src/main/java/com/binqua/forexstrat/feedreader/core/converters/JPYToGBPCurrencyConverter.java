package com.binqua.forexstrat.feedreader.core.converters;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.GBP_JPY;

public class JPYToGBPCurrencyConverter extends AbstractCurrencyConverter {
    private CurrencyPairQuotationsRepository currenciesQuoteRepository;

    public JPYToGBPCurrencyConverter(CurrencyPairQuotationsRepository currenciesQuoteRepository) {
        this.currenciesQuoteRepository = currenciesQuoteRepository;
    }

    BigDecimal sellValue() throws CurrencyPairQuotationNotAvailableException {
        BigDecimal costToSell1GBPInJPY = new BigDecimal(currenciesQuoteRepository.quoteFor(GBP_JPY).getSellValue());
        return BigDecimal.ONE.divide(costToSell1GBPInJPY, 4, BigDecimal.ROUND_HALF_UP);
    }

    BigDecimal buyValue() throws CurrencyPairQuotationNotAvailableException {
        BigDecimal costToBuy1GBPInJPY = new BigDecimal(currenciesQuoteRepository.quoteFor(GBP_JPY).getBuyValue());
        return BigDecimal.ONE.divide(costToBuy1GBPInJPY, 4, BigDecimal.ROUND_HALF_UP);
    }
}
