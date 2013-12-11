package com.binqua.forexstrat.strategy;

import org.junit.Test;

import static com.binqua.forexstrat.strategy.AmountWon.amountWon;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AmountWonTest {

    @Test
    public void add() {
        assertThat(amountWon("1.2222").add("1.1111"), is(amountWon("2.3333")));
        assertThat(amountWon("1.2222").add(amountWon("1.1111")), is(amountWon("2.3333")));
    }

    @Test
    public void subtract() {
        assertThat(amountWon("1.2222").subtract("1.1111"), is(amountWon("0.1111")));
        assertThat(amountWon("1.2222").subtract(amountWon("1.1111")), is(amountWon("0.1111")));
    }
}
