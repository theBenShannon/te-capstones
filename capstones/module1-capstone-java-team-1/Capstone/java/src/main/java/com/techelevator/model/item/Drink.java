package com.techelevator.model.item;

import java.math.BigDecimal;

public final class Drink extends Item {

    public Drink(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }

    @Override
    public String purchaseMessage() {
        return "Glug Glug, Yum!";
    }

}
