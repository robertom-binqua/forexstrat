package com.binqua.forexstrat.strategy;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class DistanceInPips {
    private final int distance;

    private DistanceInPips(int distance) {
        this.distance = distance;
    }

    public static DistanceInPips aDistanceInPipsOf(int distanceInPips) {
        return new DistanceInPips(distanceInPips);
    }

    public boolean isMultipleOf(int value) {
        return distance % value == 0;
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
}
