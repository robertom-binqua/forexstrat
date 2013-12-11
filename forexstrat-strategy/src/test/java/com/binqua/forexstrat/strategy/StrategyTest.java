package com.binqua.forexstrat.strategy;

import org.junit.Test;

import java.util.Arrays;

import static com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair.AUD_JPY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class StrategyTest {

    @Test
    public void ifMarketEntryValueIsNotValidThenStrategyExceptionIsThrown() {
        try {
            createStrategyWithMarketEntryValue("");
            fail(StrategyBuilderException.class + " should have been thrown!");
        } catch (StrategyBuilderException e) {
            assertThat(e.getMessage(), is("Please specified valid data for marketEntryValue"));
        }
    }

    @Test
    public void all() {
    }

    private void createStrategyWithMarketEntryValue(String marketEntryValue) throws StrategyBuilderException {
        new Strategy(AUD_JPY, marketEntryValue, "2", "3", true, "5", "6", "7", Arrays.asList(new StrategySingleEntry()));
    }

}
