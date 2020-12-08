package com.techelevator.model;

import java.math.BigDecimal;

public class Money {

    private static final BigDecimal NICKEL = new BigDecimal("0.05");
    private static final BigDecimal DIME = new BigDecimal("0.10");
    private static final BigDecimal QUARTER = new BigDecimal("0.25");

    private BigDecimal balance;

    public Money() {
        balance = BigDecimal.ZERO;
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public boolean purchase(BigDecimal cost) {
        boolean successful = false;
        //Create temporary balance to ensure that balance is above 0 when purchase is made
        BigDecimal tempBalance = balance.subtract(cost);
        if (tempBalance.compareTo(BigDecimal.ZERO) >= 0) {
            balance = balance.subtract(cost);
            successful = true;
        }

        return successful;
    }

    public String calculateChange() {
        //Number of each coin to be dispensed
        int amountOfQuarters = 0;
        int amountOfDimes = 0;
        int amountOfNickels = 0;

        //Calculate the minimum number of coins for each denomination
        while (balance.compareTo(QUARTER) >= 0) {
            balance = balance.subtract(QUARTER);
            amountOfQuarters++;
        }
        while (balance.compareTo(DIME) >= 0) {
            balance = balance.subtract(DIME);
            amountOfDimes++;
        }
        while (balance.compareTo(NICKEL) >= 0) {
            balance = balance.subtract(NICKEL);
            amountOfNickels++;
        }

        return "Returning: "
                + amountOfQuarters + " quarters, "
                + amountOfDimes + " dimes, "
                + amountOfNickels + " nickels.";
    }

    public String getBalance() {
        return balance.toString();
    }

    @Override
    public String toString() {
        return "Current balance: $" + balance.toString();
    }

}
