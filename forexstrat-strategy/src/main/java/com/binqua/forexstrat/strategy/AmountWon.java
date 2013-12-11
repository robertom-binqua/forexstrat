package com.binqua.forexstrat.strategy;

import com.binqua.forexstrat.strategy.configuration.AmountWonType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.math.BigDecimal;

public class AmountWon {

    private final BigDecimal value;

    private AmountWon(String value) {
        this(new BigDecimal(value));
    }

    private AmountWon(BigDecimal value) {
        this.value = value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public static AmountWon amountWon(BigDecimal value) {
        return new AmountWon(value);
    }

    public static AmountWon amountWon(String value) {
        return new AmountWon(value);
    }

    public BigDecimal asBigDecimal() {
        return value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Boolean isBiggerThan(AmountWon amountWon) {
        return this.asBigDecimal().subtract(amountWon.asBigDecimal()).signum() > 0;
    }

    public AmountWonType getTypeOfWin() {
        if (value.signum() > 0) return AmountWonType.WIN;
        if (value.signum() < 0) return AmountWonType.LOSS;
        return AmountWonType.DRAW;
    }

    public AmountWon add(String value) {
        return add(amountWon(value));
    }

    public AmountWon add(AmountWon amountWon) {
        return amountWon(this.asBigDecimal().add(amountWon.asBigDecimal()));
    }

    public AmountWon subtract(String value) {
        return subtract(amountWon(value));
    }

    public AmountWon subtract(AmountWon amountWon) {
        return amountWon(this.asBigDecimal().subtract(amountWon.asBigDecimal()));
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    public String toString() {
        return value.toPlainString();
    }

}
