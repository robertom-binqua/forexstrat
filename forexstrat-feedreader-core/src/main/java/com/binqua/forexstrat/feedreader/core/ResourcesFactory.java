package com.binqua.forexstrat.feedreader.core;

import com.binqua.forexstrat.feedreader.core.client.*;
import com.binqua.forexstrat.feedreader.core.converters.AUDToCADCurrencyConverter;
import com.binqua.forexstrat.feedreader.core.converters.CurrenciesConverter;
import com.binqua.forexstrat.feedreader.core.converters.CurrencyConverter;
import com.binqua.forexstrat.feedreader.core.converters.ToPoundsConverter;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPairs;
import com.binqua.forexstrat.feedreader.core.actions.implementation.CurrencyPairQuotationsRepositoryUpdaterAction;
import com.binqua.forexstrat.feedreader.core.repositories.*;
import com.binqua.forexstrat.feedreader.core.support.Support;

import java.util.HashMap;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class ResourcesFactory {

    private static EnumBasedCurrencyPairs theCurrencyPairs = new EnumBasedCurrencyPairs();

    private static CurrencyPairQuotationsRepositoryForNonDerivedCurrencyPairs currenciesQuoteRepositoryForNonDerivedCurrencyPairs = new CurrencyPairQuotationsRepositoryForNonDerivedCurrencyPairs();

    private static CurrencyPairQuotationsRepositoryForDerivedCurrencyPairs currenciesQuoteRepositoryForDerivedCurrencyPairs = new CurrencyPairQuotationsRepositoryForDerivedCurrencyPairs(new HashMap<CurrencyPair, CurrencyConverter>() {
        {
            put(EnumBasedCurrencyPair.AUD_CAD, new AUDToCADCurrencyConverter((currenciesQuoteRepositoryForNonDerivedCurrencyPairs)));
        }
    });

    private static ModifiableCurrencyPairQuotationsRepository modifiableCurrenciesQuoteRepository = new ConcurrentModifiableCurrencyPairQuotationsRepository(
            theCurrencyPairs.asArray(),
            currenciesQuoteRepositoryForDerivedCurrencyPairs,
            currenciesQuoteRepositoryForNonDerivedCurrencyPairs);

    private static CurrenciesConverter toPoundsConverter = new ToPoundsConverter(modifiableCurrenciesQuoteRepository);

    private static Configuration configuration = new PropertyFileConfiguration();

    private static ClientRunner clientExecutor = new ExecutorServiceClientRunner(newFixedThreadPool(theCurrencyPairs.asArray().length), new ClientFactory());

    private static Support support = new Support() {
        @Override
        public void feedLoginProblem(String message, String details) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void feedReadingProblem(String message) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void info(String message) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    private static ClientCommandsFactory clientCommandsFactory = new ClientCommandsFactory(configuration, theCurrencyPairs, support);

    private static HttpClientManager httpClientManager = new HttpClientManager(theCurrencyPairs.nonDerivedValues().length, 5000, 5000);

    private static CurrencyPairFeedReader singleCurrencyPairFeedReader = new CurrencyPairFeedReader(clientExecutor,
            clientCommandsFactory,
            httpClientManager,
            new CurrencyPairQuotationsRepositoryUpdaterAction(modifiableCurrenciesQuoteRepository));

    private static CurrencyPairsFeedReader feedReader = new CurrencyPairsFeedReader(configuration, theCurrencyPairs, singleCurrencyPairFeedReader);

    public static CurrenciesConverter getToPoundsConverter() {
        return toPoundsConverter;
    }

    public static CurrencyPairsFeedReader getFeedReader() {
        return feedReader;
    }

    public static CurrencyPairQuotationsRepository getCurrenciesQuoteRepository() {
        return modifiableCurrenciesQuoteRepository;
    }

    public static ModifiableCurrencyPairQuotationsRepository getModifiableCurrenciesQuoteRepository() {
        return modifiableCurrenciesQuoteRepository;
    }
}
