package com.techelevator.model.item;

import java.math.BigDecimal;

public final class Gum extends Item {

    public Gum(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }

    @Override
    public String purchaseMessage() {
        return "Chew Chew, Yum!";
    }

}
