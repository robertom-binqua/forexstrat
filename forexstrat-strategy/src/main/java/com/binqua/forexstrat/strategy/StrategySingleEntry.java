package com.binqua.forexstrat.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.abs;

public class StrategySingleEntry {

    private BigDecimal strategySingleEntryValue;
    private BigDecimal numberOfContracts;
    private boolean isEmpty;

    public StrategySingleEntry(BigDecimal strategySingleEntryValue, BigDecimal numberOfContracts) {
        this.strategySingleEntryValue = strategySingleEntryValue;
        this.numberOfContracts = numberOfContracts;
        this.isEmpty = false;
    }

    private BigDecimal valueOfASingleContractPerASinglePipInRightCurrency(BigDecimal strategySingleEntryValue) {
        int scale = strategySingleEntryValue.scale();
        if (scale == 2) return new BigDecimal(1000);
        if (scale == 4) return new BigDecimal(10);
        throw new IllegalArgumentException("Only 4 or 2 digits are allowed after the comma. " + strategySingleEntryValue + " is not valid!");
    }

    public StrategySingleEntry() {
        this.isEmpty = true;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public ReportEntry createReportEntry(BigDecimal wholeStrategyEntryValue, BigDecimal wholeStrategyExitValue, boolean isABuyStrategy,
                                         int pipsToPayToTheBroker, BigDecimal lowestMarketValueConsidered, BigDecimal costOfTheRightCurrencyInPounds) {

        BigDecimal numberOfContractsWonOrLost = calculateTotalContractsForThisSingleStrategy(strategySingleEntryValue, lowestMarketValueConsidered, pipsToPayToTheBroker, isABuyStrategy);

        String reportEntryPrefix = calculateReportPrefix(wholeStrategyEntryValue, wholeStrategyExitValue, isABuyStrategy, pipsToPayToTheBroker,
                costOfTheRightCurrencyInPounds);

        String reportEntrySuffix = calculateReportSuffix(isABuyStrategy, pipsToPayToTheBroker, lowestMarketValueConsidered, costOfTheRightCurrencyInPounds, numberOfContractsWonOrLost);

        return new ReportEntry(reportEntryPrefix + reportEntrySuffix, isALost(numberOfContractsWonOrLost));
    }

    private String calculateReportSuffix(boolean isABuyStrategy, int pipsToPayToTheBroker, BigDecimal lowestMarketValueConsidered, BigDecimal costOfTheRightCurrencyInPounds, BigDecimal numberOfContractsWonOrLost) {
        return " max " + gainedOrLostString(numberOfContractsWonOrLost) + format(calculateAbsoluteGainInPounds(numberOfContractsWonOrLost, costOfTheRightCurrencyInPounds)) + "\u00A3 "
                + abs(pipsWonOrLost(strategySingleEntryValue, lowestMarketValueConsidered, pipsToPayToTheBroker, isABuyStrategy)) + " pips";
    }

    private String format(BigDecimal bigDecimal) {
        return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_EVEN).toPlainString();
    }

    private String calculateReportPrefix(BigDecimal wholeStrategyEntryValue, BigDecimal wholeStrategyExitValue, boolean isABuyStrategy, int pipsToPayToTheBroker,
                                         BigDecimal costOfTheRightCurrencyInPounds) {

        BigDecimal numberOfContractsWonOrLost = calculateTotalContractsForThisSingleStrategy(strategySingleEntryValue, wholeStrategyExitValue, pipsToPayToTheBroker, isABuyStrategy);

        return numberOfContracts + " contracts \u0040 " + distanceInPipsBetween(strategySingleEntryValue, wholeStrategyEntryValue) + " pips " +
                "(" + strategySingleEntryValue.toPlainString() + ") closed \u0040 " + wholeStrategyExitValue.toPlainString() + " "
                + gainedOrLostString(numberOfContractsWonOrLost) + format(calculateAbsoluteGainInPounds(numberOfContractsWonOrLost, costOfTheRightCurrencyInPounds)) +
                "\u00A3 (" + abs(pipsWonOrLost(strategySingleEntryValue, wholeStrategyExitValue, pipsToPayToTheBroker, isABuyStrategy)) + " pips)";
    }

    private boolean isALost(BigDecimal contractsWonOrLost) {
        return contractsWonOrLost.signum() == -1;
    }

    private String gainedOrLostString(BigDecimal calculateContractsWon) {
        if (isALost(calculateContractsWon)) {
            return "-";
        }
        return "+";
    }

    private BigDecimal calculateAbsoluteGainInPounds(BigDecimal contractsPerPipsWonOrLost, BigDecimal costOfTheRightCurrencyInPounds) {
        return valueOfASingleContractPerASinglePipInRightCurrency(strategySingleEntryValue)
                .multiply(contractsPerPipsWonOrLost)
                .multiply(costOfTheRightCurrencyInPounds)
                .setScale(2, RoundingMode.HALF_DOWN)
                .abs();
    }

    private int pipsWonOrLost(BigDecimal strategyEntryValue, BigDecimal wholeStrategyExitValue, int pipsToBePayedToTheBroker, boolean isABuyStrategy) {
        BigDecimal differenceBetweenEntryAndExitValue = isABuyStrategy ? wholeStrategyExitValue.subtract(strategyEntryValue) : strategyEntryValue.subtract(wholeStrategyExitValue);
        return differenceBetweenEntryAndExitValue.movePointRight(strategyEntryValue.scale()).intValue() - pipsToBePayedToTheBroker;
    }

    private BigDecimal calculateTotalContractsForThisSingleStrategy(BigDecimal strategySingleEntryValue, BigDecimal marketValue, int pipsToPayToTheBroker, boolean wasABuy) {
        return numberOfContracts.multiply(new BigDecimal(pipsWonOrLost(strategySingleEntryValue, marketValue, pipsToPayToTheBroker, wasABuy))).setScale(2, RoundingMode.HALF_DOWN);
    }

    private int distanceInPipsBetween(BigDecimal strategySingleEntryValue, BigDecimal wholeStrategyEntryValue) {
        BigDecimal difference = strategySingleEntryValue.subtract(wholeStrategyEntryValue);
        return difference.movePointRight(wholeStrategyEntryValue.scale()).intValue();
    }

    public BigDecimal calculateGainInPounds(BigDecimal theMarketValue, int pipsToPayToTheBroker, boolean isABuyStrategy, BigDecimal costOfTheRightCurrencyInPounds) {
        BigDecimal numberOfContractsWonOrLost = calculateTotalContractsForThisSingleStrategy(strategySingleEntryValue, theMarketValue, pipsToPayToTheBroker, isABuyStrategy);
        BigDecimal absoluteGainInPounds = calculateAbsoluteGainInPounds(numberOfContractsWonOrLost, costOfTheRightCurrencyInPounds);
        if (isALost(numberOfContractsWonOrLost)) {
            return absoluteGainInPounds.multiply(new BigDecimal("-1"));
        }
        return absoluteGainInPounds;
    }

}
