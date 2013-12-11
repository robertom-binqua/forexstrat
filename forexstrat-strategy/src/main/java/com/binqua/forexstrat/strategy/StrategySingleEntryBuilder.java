package com.binqua.forexstrat.strategy;

import java.math.BigDecimal;

public class StrategySingleEntryBuilder {

    private String strategySingleEntryValue;
    private String numberOfContracts;

    public StrategySingleEntryBuilder withValue(String actualStrategyEntryPointValue) {
        this.strategySingleEntryValue = actualStrategyEntryPointValue;
        return this;
    }

    public StrategySingleEntryBuilder withNumberOfContracts(String numberOfContracts) {
        this.numberOfContracts = numberOfContracts;
        return this;
    }

    public StrategySingleEntry build() {
        if (numberOfContracts == null && strategySingleEntryValue == null) {
            return new StrategySingleEntry();
        }
        return new StrategySingleEntry(new BigDecimal(strategySingleEntryValue), new BigDecimal(numberOfContracts));
    }

}
