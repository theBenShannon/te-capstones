package com.techelevator.model;

import com.techelevator.model.exceptions.OutOfStockException;
import com.techelevator.model.item.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendingMachineTests {

    private VendingMachine sut;

    @Before
    public void setup() {
        List<Item> items = new ArrayList<>();
        items.add(new Chip("A1", "Chips", new BigDecimal("1.25")));
        items.add(new Gum("A2", "Gum", new BigDecimal("1.75")));
        items.add(new Candy("A3", "Candy", new BigDecimal("0.25")));
        items.add(new Drink("A4", "Drink", new BigDecimal("4.25")));
        sut = new VendingMachine(items);
    }

    @Test
    public void sold_out_items_cannot_be_purchased() {
        sut.getMoneyLog().deposit(BigDecimal.TEN);
        try {
            sut.purchaseItem("A1");
            sut.purchaseItem("A1");
            sut.purchaseItem("A1");
            sut.purchaseItem("A1");
            sut.purchaseItem("A1");
            sut.purchaseItem("A1"); //Try to purchase 6th item, should be out of stock
        } catch (Exception e) {
            Assert.assertEquals("Item is out of stock", e.getMessage());
        }
    }

    @Test
    public void null_item_throws_NPE() {
        try {
            sut.purchaseItem(null);
        } catch (NullPointerException e) {

        } catch (Exception e) {
            Assert.fail(); //Some other exception is thrown, should fail
        }
    }


}
