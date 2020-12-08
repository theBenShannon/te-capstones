package com.techelevator.model.item;

import java.math.BigDecimal;

public final class Chip extends Item {

    public Chip(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }

    @Override
    public String purchaseMessage() {
        return "Crunch Crunch, Yum!";
    }

}
