package com.techelevator.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class MoneyTests {

    private Money sut;

    @Before
    public void setup() {
        sut = new Money();
    }

    @Test
    public void no_purchase_if_no_money() {
        //Try to purchase $0.25 item
        Assert.assertFalse(sut.purchase(new BigDecimal("0.25")));
    }

    @Test
    public void balance_updated_on_withdraw_and_deposit() {
        sut.deposit(BigDecimal.TEN);
        Assert.assertEquals("10", sut.getBalance());

        sut.purchase(BigDecimal.ONE);
        Assert.assertEquals("9", sut.getBalance());
    }

    @Test
    public void correct_amount_of_change_dispensed() {
        sut.deposit(new BigDecimal("5"));
        String result = "Returning: "
                + 20 + " quarters, "
                + 0 + " dimes, "
                + 0 + " nickels.";
        Assert.assertEquals(result, sut.calculateChange());

        sut.deposit(new BigDecimal("5.15"));
        result = "Returning: "
                + 20 + " quarters, "
                + 1 + " dimes, "
                + 1 + " nickels.";
        Assert.assertEquals(result, sut.calculateChange());
    }

}
