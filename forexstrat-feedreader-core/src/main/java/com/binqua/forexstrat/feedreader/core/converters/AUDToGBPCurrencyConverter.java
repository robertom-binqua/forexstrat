package com.binqua.forexstrat.feedreader.core.converters;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.AUD_JPY;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.GBP_JPY;

public class AUDToGBPCurrencyConverter extends AbstractCurrencyConverter {
    private CurrencyPairQuotationsRepository currenciesQuoteRepository;

    public AUDToGBPCurrencyConverter(CurrencyPairQuotationsRepository currenciesQuoteRepository) {
        this.currenciesQuoteRepository = currenciesQuoteRepository;
    }

    BigDecimal sellValue() throws CurrencyPairQuotationNotAvailableException {
        BigDecimal costToSell1AUDInJPY = new BigDecimal(currenciesQuoteRepository.quoteFor(AUD_JPY).getSellValue());
        BigDecimal costToSell1GBPInJPY = new BigDecimal(currenciesQuoteRepository.quoteFor(GBP_JPY).getSellValue());
        return costToSell1AUDInJPY.divide(costToSell1GBPInJPY, 4, BigDecimal.ROUND_HALF_UP);
    }

    BigDecimal buyValue() throws CurrencyPairQuotationNotAvailableException {
        BigDecimal costToBuy1AUDInJPY = new BigDecimal(currenciesQuoteRepository.quoteFor(AUD_JPY).getBuyValue());
        BigDecimal costToBuy1GBPInJPY = new BigDecimal(currenciesQuoteRepository.quoteFor(GBP_JPY).getBuyValue());
        return costToBuy1AUDInJPY.divide(costToBuy1GBPInJPY, 4, BigDecimal.ROUND_HALF_UP);
    }
}
