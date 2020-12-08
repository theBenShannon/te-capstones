package com.techelevator.model.item;

import java.math.BigDecimal;

public final class Candy extends Item {

    public Candy(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }

    @Override
    public String purchaseMessage() {
        return "Munch Munch, Yum!";
    }

}
