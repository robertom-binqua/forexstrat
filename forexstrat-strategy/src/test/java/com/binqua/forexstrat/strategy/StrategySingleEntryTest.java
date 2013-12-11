package com.binqua.forexstrat.strategy;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class StrategySingleEntryTest {

    private static final BigDecimal COST_OF_THE_RIGHT_CURRENCY_POUNDS = new BigDecimal(2);

    @Test
    public void createReportReturnsRightValueInCaseOfBuyingStrategyWithMarketValueWhenWeEndOurStrategyBiggerThanActualStrategyEntryPoint() throws Exception {
        String wholeStrategyEntryValue = "1.0050";
        String wholeStrategyExitValue = "1.0010";

        String strategySingleEntryValue = "1.0000";
        String lowestMarketValueConsidered = "0.9999";
        String numberOfContracts = "2";
        boolean wasABuyStrategy = true;
        int pipsToPayToTheBroker = 2;

        StrategySingleEntry strategySingleEntry = new StrategySingleEntry(new BigDecimal(strategySingleEntryValue), new BigDecimal(numberOfContracts));

        ReportEntry actualReportEntry = strategySingleEntry.createReportEntry(new BigDecimal(wholeStrategyEntryValue), new BigDecimal(wholeStrategyExitValue), wasABuyStrategy, pipsToPayToTheBroker,
                new BigDecimal(lowestMarketValueConsidered), COST_OF_THE_RIGHT_CURRENCY_POUNDS);

        assertEquals("2 contracts \u0040 -50 pips (1.0000) closed \u0040 1.0010 +320\u00A3 (8 pips) max -120\u00A3 3 pips", actualReportEntry.getTextToReport());
    }

    @Test
    public void createReportReturnsRightValueInCaseOfSellingStrategyWithMarketValueWhenWeEndOurStrategySmallerThanActualStrategyEntryPoint() throws Exception {
        String marketValueWhenWeStartOurStrategy = "1.0050";
        String actualStrategyEntryPointValue = "1.0000";
        String marketValueWhenWeEndOurStrategy = "0.9999";
        String marketExtremeValueReached = "0.9998";
        String numberOfContracts = "2";
        boolean wasABuyStrategy = true;
        int pipsToPayToTheBroker = 2;

        StrategySingleEntry strategySingleEntry = new StrategySingleEntry(new BigDecimal(actualStrategyEntryPointValue), new BigDecimal(numberOfContracts));
        ReportEntry actualReport = strategySingleEntry.createReportEntry(new BigDecimal(marketValueWhenWeStartOurStrategy), new BigDecimal(marketValueWhenWeEndOurStrategy), wasABuyStrategy, pipsToPayToTheBroker,
                new BigDecimal(marketExtremeValueReached), COST_OF_THE_RIGHT_CURRENCY_POUNDS);

        assertEquals("2 contracts \u0040 -50 pips (1.0000) closed \u0040 0.9999 -120\u00A3 (3 pips) max -160\u00A3 4 pips", actualReport.getTextToReport());
    }

    @Test
    public void createReportReturnsRightValueInCaseOfBuyStrategyWithMarketValueWhenWeEndOurStrategyBiggerThanActualStrategyEntryPointButSmallerThanMarketValueWhenWeStartOurStrategy()
            throws Exception {
        String marketValueWhenWeStartOurStrategy = "1.3116";
        String marketValueWhenWeEndOurStrategy = "1.3110";
        String strategyEntryValue = "1.3090";
        String marketExtremeValueReached = "1.3080";
        String numberOfContracts = "0.1";
        boolean wasABuyStrategy = true;
        int pipsToPayToTheBroker = 2;

        StrategySingleEntry strategySingleEntry = new StrategySingleEntry(new BigDecimal(strategyEntryValue), new BigDecimal(numberOfContracts));
        ReportEntry actualReport = strategySingleEntry.createReportEntry(new BigDecimal(marketValueWhenWeStartOurStrategy), new BigDecimal(marketValueWhenWeEndOurStrategy), wasABuyStrategy, pipsToPayToTheBroker,
                new BigDecimal(marketExtremeValueReached), COST_OF_THE_RIGHT_CURRENCY_POUNDS);

        assertEquals("0.1 contracts \u0040 -26 pips (1.3090) closed \u0040 1.3110 +36\u00A3 (18 pips) max -24\u00A3 12 pips", actualReport.getTextToReport());
    }

    @Test
    public void createReportReturnsRightValueInCaseOfBuyStrategyWithMarketValueWhenWeEndOurStrategyBiggerThanActualStrategyEntryPointAndBiggerThanMarketValueWhenWeStartOurStrategy()
            throws Exception {
        String marketValueWhenWeEndOurStrategy = "1.3117";
        String marketValueWhenWeStartOurStrategy = "1.3116";

        String strategyEntryValue = "1.3090";
        String marketExtremeValueReached = "1.3080";
        String numberOfContracts = "0.1";
        boolean wasABuyStrategy = true;
        int pipsToPayToTheBroker = 2;

        StrategySingleEntry strategySingleEntry = new StrategySingleEntry(new BigDecimal(strategyEntryValue), new BigDecimal(numberOfContracts));

        ReportEntry actualReport = strategySingleEntry.createReportEntry(new BigDecimal(marketValueWhenWeStartOurStrategy), new BigDecimal(marketValueWhenWeEndOurStrategy), wasABuyStrategy, pipsToPayToTheBroker,
                new BigDecimal(marketExtremeValueReached), COST_OF_THE_RIGHT_CURRENCY_POUNDS);

        assertEquals("0.1 contracts \u0040 -26 pips (1.3090) closed \u0040 1.3117 +50\u00A3 (25 pips) max -24\u00A3 12 pips", actualReport.getTextToReport());
    }

    @Test
    public void createReportReturnsRightValueInCaseOfSellingStrategyWithMarketValueWhenWeEndOurStrategyBiggerThanActualStrategyEntryPointAndBiggerThanMarketValueWhenWeStartOurStrategy()
            throws Exception {
        String marketLimitValueReached = "1.3005";
        String strategyEntryValue = "1.3004";
        String marketValueWhenWeEndOurStrategy = "1.3001";
        String marketValueWhenWeStartOurStrategy = "1.3000";

        String numberOfContracts = "0.1";
        boolean wasABuyStrategy = false;
        int pipsToPayToTheBroker = 2;

        StrategySingleEntry strategySingleEntry = new StrategySingleEntry(new BigDecimal(strategyEntryValue), new BigDecimal(numberOfContracts));

        ReportEntry actualReport = strategySingleEntry.createReportEntry(new BigDecimal(marketValueWhenWeStartOurStrategy), new BigDecimal(marketValueWhenWeEndOurStrategy), wasABuyStrategy, pipsToPayToTheBroker,
                new BigDecimal(marketLimitValueReached), COST_OF_THE_RIGHT_CURRENCY_POUNDS);

        assertEquals("0.1 contracts \u0040 4 pips (1.3004) closed \u0040 1.3001 +2\u00A3 (1 pips) max -6\u00A3 3 pips", actualReport.getTextToReport());
    }

    @Test
    public void calculateGainInPoundsForABuyingStrategyWhenMarketValueIsBiggerThanStrategyEntryValue() throws Exception {
        String theMarketValue = "1.3004";
        boolean wasABuyStrategy = true;
        String strategyEntryValue = "1.3000";

        String numberOfContracts = "1";
        int pipsToPayToTheBroker = 1;

        StrategySingleEntry strategySingleEntry = new StrategySingleEntry(new BigDecimal(strategyEntryValue), new BigDecimal(numberOfContracts));

        assertEquals("60.00", strategySingleEntry.calculateGainInPounds(new BigDecimal(theMarketValue), pipsToPayToTheBroker, wasABuyStrategy, COST_OF_THE_RIGHT_CURRENCY_POUNDS).toPlainString());
    }

    @Test
    public void createReportReturnsRightValueInCaseOfSellingStrategyWithMarketValueWhenWeEndOurStrategyBiggerThanActualStrategyEntryPointButSmallerThanMarketValueWhenWeStartOurStrategy()
            throws Exception {
        String marketLimitValueReached = "1.3010";
        String strategyEntryValue = "1.3004";
        String marketValueWhenWeStartOurStrategy = "1.3001";
        String marketValueWhenWeEndOurStrategy = "1.3000";
        String numberOfContracts = "1.5";
        boolean wasABuyStrategy = false;
        int pipsToPayToTheBroker = 2;

        StrategySingleEntry strategySingleEntry = new StrategySingleEntry(new BigDecimal(strategyEntryValue), new BigDecimal(numberOfContracts));

        ReportEntry actualReport = strategySingleEntry.createReportEntry(new BigDecimal(marketValueWhenWeStartOurStrategy), new BigDecimal(marketValueWhenWeEndOurStrategy), wasABuyStrategy, pipsToPayToTheBroker,
                new BigDecimal(marketLimitValueReached), COST_OF_THE_RIGHT_CURRENCY_POUNDS);

        assertEquals("1.5 contracts \u0040 3 pips (1.3004) closed \u0040 1.3000 +60\u00A3 (2 pips) max -240\u00A3 8 pips", actualReport.getTextToReport());
    }

    @Test
    public void calculateGainInPoundsForASellingStrategyWhenMarketValueIsBiggerThanStrategyEntryValue() throws Exception {
        BigDecimal costOfRightCurrencyInPounds = new BigDecimal(3);

        String strategyEntryValue = "1.3000";
        boolean wasABuyStrategy = false;
        String theMarketValue = "1.3004";

        String numberOfContracts = "1";
        int pipsToPayToTheBroker = 4;

        StrategySingleEntry strategySingleEntry = new StrategySingleEntry(new BigDecimal(strategyEntryValue), new BigDecimal(numberOfContracts));

        assertEquals("-240.00", strategySingleEntry.calculateGainInPounds(new BigDecimal(theMarketValue), pipsToPayToTheBroker, wasABuyStrategy, costOfRightCurrencyInPounds).toPlainString());
    }

    @Test
    public void calculateGainInPoundsForASellingStrategyWhenMarketValueIsSmallerThanStrategyEntryValue() throws Exception {
        String strategyEntryValue = "1.3004";
        boolean wasABuyStrategy = false;
        String theMarketValue = "1.3000";

        String numberOfContracts = "1";
        int pipsToPayToTheBroker = 2;

        StrategySingleEntry strategySingleEntry = new StrategySingleEntry(new BigDecimal(strategyEntryValue), new BigDecimal(numberOfContracts));

        assertEquals("40.00",
                strategySingleEntry.calculateGainInPounds(new BigDecimal(theMarketValue), pipsToPayToTheBroker, wasABuyStrategy, COST_OF_THE_RIGHT_CURRENCY_POUNDS).toPlainString());
    }

    @Test
    public void calculateGainInPoundsForABuyingStrategyWhenMarketValueIsSmallerThanStrategyEntryValue() throws Exception {
        String strategyEntryValue = "1.3004";
        boolean wasABuyStrategy = true;
        String theMarketValue = "1.3000";

        String numberOfContracts = "1";
        int pipsToPayToTheBroker = 2;

        StrategySingleEntry strategySingleEntry = new StrategySingleEntry(new BigDecimal(strategyEntryValue), new BigDecimal(numberOfContracts));

        assertEquals("-120.00", strategySingleEntry.calculateGainInPounds(new BigDecimal(theMarketValue), pipsToPayToTheBroker, wasABuyStrategy, COST_OF_THE_RIGHT_CURRENCY_POUNDS).toPlainString());
    }

    @Test
    public void calculateGainInPoundsIsCorrectInCaseOf2DecimalDigits() throws Exception {
        String strategyEntryValue = "102.27";
        boolean wasABuyStrategy = false;
        String strategyExitValue = "104.19";

        String numberOfContracts = "0.2";
        int pipsToPayToTheBroker = 2;

        StrategySingleEntry strategySingleEntry = new StrategySingleEntry(new BigDecimal(strategyEntryValue), new BigDecimal(numberOfContracts));

        BigDecimal costOfTheSecondCurrencyInPounds = new BigDecimal("0.007813");

        assertEquals("-303.14", strategySingleEntry.calculateGainInPounds(new BigDecimal(strategyExitValue), pipsToPayToTheBroker, wasABuyStrategy, costOfTheSecondCurrencyInPounds).toPlainString());
    }

}
