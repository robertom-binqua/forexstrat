package com.binqua.forexstrat.strategy;

import com.binqua.forexstrat.strategy.configuration.AmountWonType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;

public class ReportModelEntry {
    private AmountWon amountWon;
    private CurrencyQuote quoteValue;
    private boolean isTheStrategyEntryPoint;
    private boolean isTheCurrentMarketValue;
    private boolean isHighlighted;
    private String message;

    public ReportModelEntry(AmountWon amountWon, CurrencyQuote quoteValue, boolean isTheStrategyEntryPoint, boolean isCurrentMarketValue, boolean isHighlighted) {
        this.amountWon = amountWon;
        this.quoteValue = quoteValue;
        this.isTheStrategyEntryPoint = isTheStrategyEntryPoint;
        this.isTheCurrentMarketValue = isCurrentMarketValue;
        this.isHighlighted = isHighlighted;
    }

    public ReportModelEntry(String message) {
        this.message = message;
    }

    public AmountWon getAmountWon() {
        return amountWon;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public boolean isTheStrategyEntryPoint() {
        return isTheStrategyEntryPoint;
    }

    public boolean isTheCurrentMarketValue() {
        return isTheCurrentMarketValue;
    }

    public CurrencyQuote getQuoteValue() {
        return quoteValue;
    }

    public String asString() {
        if (message != null) {
            return message;
        }
        if (amountWon.getTypeOfWin() == AmountWonType.LOSS) {
            return asText("-");
        }
        if (amountWon.getTypeOfWin() == AmountWonType.WIN) {
            return asText("+");
        }
        if (amountWon.getTypeOfWin() == AmountWonType.DRAW) {
            return "\u0040 " + quoteValue + " 0 \u00A3";
        }
        throw new IllegalStateException(amountWon.getTypeOfWin() + " value is not valid");
    }

    public DistanceInPips distanceInPipsFrom(CurrencyQuote currencyQuote) {
        return quoteValue.absoluteDistanceInPipsFrom(currencyQuote);
    }

    private String asText(String lostOrWin) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\u0040 ");
        stringBuilder.append(quoteValue);
        stringBuilder.append(" ");
        stringBuilder.append(lostOrWin);
        stringBuilder.append(amountWon.asBigDecimal().abs().setScale(0, BigDecimal.ROUND_HALF_EVEN));
        stringBuilder.append(" \u00A3");
        return stringBuilder.toString();
    }

    public void highlight() {
        isHighlighted = true;
    }

    public void markAsTheStrategyMarketEntryValue() {
        isTheStrategyEntryPoint = true;
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
        return ToStringBuilder.reflectionToString(this);
    }

    public void markAsTheCurrentMarketValue() {
        isTheCurrentMarketValue = true;

    }
}
