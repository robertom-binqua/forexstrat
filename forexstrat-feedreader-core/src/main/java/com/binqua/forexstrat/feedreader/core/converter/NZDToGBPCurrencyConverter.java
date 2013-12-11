package com.binqua.forexstrat.feedreader.core.converter;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.GBP_USD;
import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.NZD_USD;

public class NZDToGBPCurrencyConverter extends AbstractCurrencyConverter {
    private CurrencyPairQuotationsRepository currenciesQuoteRepository;

    public NZDToGBPCurrencyConverter(CurrencyPairQuotationsRepository currenciesQuoteRepository) {
        this.currenciesQuoteRepository = currenciesQuoteRepository;
    }

    BigDecimal sellValue() throws CurrencyPairQuotationNotAvailableException {
        BigDecimal costToSell1NZDInUSD = new BigDecimal(currenciesQuoteRepository.quoteFor(NZD_USD).getSellValue());
        BigDecimal costToSell1GBPInUSD = new BigDecimal(currenciesQuoteRepository.quoteFor(GBP_USD).getSellValue());
        return costToSell1NZDInUSD.divide(costToSell1GBPInUSD, 4, BigDecimal.ROUND_HALF_UP);
    }

    BigDecimal buyValue() throws CurrencyPairQuotationNotAvailableException {
        BigDecimal costToBuy1NZDInUSD = new BigDecimal(currenciesQuoteRepository.quoteFor(NZD_USD).getBuyValue());
        BigDecimal costToBuy1GBPInUSD = new BigDecimal(currenciesQuoteRepository.quoteFor(EnumBasedCurrencyPair.GBP_USD).getBuyValue());
        return costToBuy1NZDInUSD.divide(costToBuy1GBPInUSD, 4, BigDecimal.ROUND_HALF_UP);
    }
}
