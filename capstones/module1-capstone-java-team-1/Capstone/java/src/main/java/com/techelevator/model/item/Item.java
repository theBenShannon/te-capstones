package com.techelevator.model.item;

import java.math.BigDecimal;

public abstract class Item {

    private String slot;
    private String name;
    private BigDecimal price;

    public Item(String slot, String name, BigDecimal price) {
        this.slot = slot;
        this.name = name;
        this.price = price;
    }

    public abstract String purchaseMessage();

    @Override
    public String toString() {
        return name + " - " + slot + " - $" + price + "\n";
    }

    //Getters and Setters
    public String getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
