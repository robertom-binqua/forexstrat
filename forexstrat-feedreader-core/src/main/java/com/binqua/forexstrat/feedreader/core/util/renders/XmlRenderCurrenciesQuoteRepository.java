package com.binqua.forexstrat.feedreader.core.util.renders;

import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationsRepository;
import com.binqua.forexstrat.feedreader.core.repositories.CurrencyPairQuotationNotAvailableException;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPairs;
import com.binqua.forexstrat.feedreader.core.model.impl.CurrencyPairQuotation;

import static java.lang.String.format;

public class XmlRenderCurrenciesQuoteRepository implements Render<String> {

    private CurrencyPairQuotationsRepository currenciesQuoteRepository;
    private CurrencyPairs currencyPairs;

    public XmlRenderCurrenciesQuoteRepository(CurrencyPairQuotationsRepository currenciesQuoteRepository, CurrencyPairs currencyPairs) {
        this.currenciesQuoteRepository = currenciesQuoteRepository;
        this.currencyPairs = currencyPairs;
    }

    public String render() {
        XmlRender xmlRender = new XmlRender();
        for (CurrencyPair aCurrencyPair : currencyPairs.asArray()) {
            try {
                xmlRender.add(currenciesQuoteRepository.quoteFor(aCurrencyPair));
            } catch (CurrencyPairQuotationNotAvailableException e) {
                xmlRender.addNotAvailableQuoteFor(aCurrencyPair);
            }
        }
        return xmlRender.render();
    }

    private class XmlRender {

        private StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<quotes>\n");

        public void add(CurrencyPairQuotation quote) {
            builder.append(format("    <quote currency-pair=\"%s\" buy=\"%s\" sell=\"%s\" average=\"%s\" time=\"\" />\n", quote.getCurrencyPair().asString(), quote.getBuyValue(), quote.getSellValue(), quote.getAverageValue()));
        }

        public void addNotAvailableQuoteFor(CurrencyPair aCurrencyPair) {
            builder.append(format("    <quote currency-pair=\"%s\" />\n", aCurrencyPair.asString()));
        }

        public String render() {
            builder.append("</quotes>");
            return builder.toString();
        }
    }
}
