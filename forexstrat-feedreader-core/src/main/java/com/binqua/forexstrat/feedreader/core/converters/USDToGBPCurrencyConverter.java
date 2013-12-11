package com.binqua.forexstrat.feedreader.core.converters;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;

import java.math.BigDecimal;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.GBP_USD;

public class USDToGBPCurrencyConverter extends AbstractCurrencyConverter {
    private CurrencyPairQuotationsRepository currenciesQuoteRepository;

    public USDToGBPCurrencyConverter(CurrencyPairQuotationsRepository currenciesQuoteRepository) {
        this.currenciesQuoteRepository = currenciesQuoteRepository;
    }

    BigDecimal sellValue() throws CurrencyPairQuotationNotAvailableException {
        BigDecimal costToSell1GBPInUSD = new BigDecimal(currenciesQuoteRepository.quoteFor(GBP_USD).getSellValue());
        return BigDecimal.ONE.divide(costToSell1GBPInUSD, 4, BigDecimal.ROUND_HALF_UP);
    }

    BigDecimal buyValue() throws CurrencyPairQuotationNotAvailableException {
        BigDecimal costToBuy1GBPInUSD = new BigDecimal(currenciesQuoteRepository.quoteFor(GBP_USD).getBuyValue());
        return BigDecimal.ONE.divide(costToBuy1GBPInUSD, 4, BigDecimal.ROUND_HALF_UP);
    }

}