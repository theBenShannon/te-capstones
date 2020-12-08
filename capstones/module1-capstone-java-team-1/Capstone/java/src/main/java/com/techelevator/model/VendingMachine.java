package com.techelevator.model;

import com.techelevator.model.exceptions.OutOfMoneyException;
import com.techelevator.model.exceptions.OutOfStockException;
import com.techelevator.model.item.Item;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {

    private static final int STARTING_INVENTORY = 5;

    private Money moneyLog; //Current amount of money in the machine
    private BigDecimal totalSales = BigDecimal.ZERO;
    private Map<Item, Integer> inventory = new LinkedHashMap<>();     //Item and number of units remaining
    private Map<Item, Integer> inventorySold = new LinkedHashMap<>(); //Item and number of units sold
    private Map<String, Item> itemLocations = new HashMap<>();  //Slot ID and Item

    public VendingMachine(List<Item> items) {
        updateInventory(items);
        moneyLog = new Money();
    }

    //Fully stock items to starting inventory value and put items in correct slots
    private void updateInventory(List<Item> items) {
        for(Item item : items) {
            inventory.put(item, STARTING_INVENTORY);
            inventorySold.put(item, 0);
            itemLocations.put(item.getSlot(), item);
        }
    }

    public Item purchaseItem(String slot) throws OutOfStockException, OutOfMoneyException{
        Item item = itemLocations.get(slot.toUpperCase());

        if (item == null) {
            throw new NullPointerException();
        }

        if (isItemInStock(item)) { //Make sure item is in stock
            if (moneyLog.purchase(item.getPrice())) { //Make sure there is enough money in the machine to purchase item
                removeOneItemFromStock(item);
            } else  {
                throw new OutOfMoneyException("Not enough money to purchase this item");
            }
        } else {
            throw new OutOfStockException("Item is out of stock");
        }

        return item;
    }

    private void removeOneItemFromStock(Item item) {
        int currentStock = inventory.get(item);
        inventory.put(item, currentStock - 1);
        addToItemSales(item);
    }

    private void addToItemSales(Item item) {
        int currentSold = inventorySold.get(item);
        inventorySold.put(item, currentSold + 1);
        totalSales = totalSales.add(item.getPrice());
    }

    private boolean isItemInStock(Item item) {
        return inventory.get(item) > 0;
    }

    //For displaying contents of vending machine to the console
    @Override
    public String toString() {
        String result = "";
        for (Map.Entry<Item, Integer> item : inventory.entrySet()) {
            if (item.getValue() == 0) {
                result += "SOLD OUT\n";
            } else {
                result += item.getKey();
            }
        }
        return result;
    }

    //Getters and Setters
    public Money getMoneyLog() {
        return moneyLog;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public Map<Item, Integer> getInventorySold() {
        return inventorySold;
    }
}
