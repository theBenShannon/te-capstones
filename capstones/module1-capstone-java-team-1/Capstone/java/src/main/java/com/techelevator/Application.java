package com.techelevator;

import com.techelevator.model.VendingMachine;
import com.techelevator.model.item.Item;
import com.techelevator.util.AuditLog;
import com.techelevator.util.InventoryParser;
import com.techelevator.util.SalesReport;
import com.techelevator.view.BasicUI;
import com.techelevator.view.MenuDrivenCLI;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class Application {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_EXIT = "Exit";
    public static final String MAIN_MENU_SALES = "Sales Report";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS,
            MAIN_MENU_OPTION_PURCHASE,
            MAIN_MENU_EXIT,
            MAIN_MENU_SALES};

    private static final String PURCHASE_MENU_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_FINISH = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_FEED_MONEY,
            PURCHASE_MENU_SELECT_PRODUCT,
            PURCHASE_MENU_FINISH};

    private static final String PURCHASE_MESSAGE = "Select a slot number: ";

    private final BasicUI ui;

    private VendingMachine machine;

    public Application(BasicUI ui) {
        this.ui = ui;
    }

    public static void main(String[] args) {
        BasicUI cli = new MenuDrivenCLI();
        Application application = new Application(cli);
        application.run();
    }

    public void run() {

        prepareVendingMachine();

        boolean mainMenuRunning = true;
        boolean purchaseMenuRunning = false;
        while (mainMenuRunning) {
            String mainMenuSelection = ui.promptForSelection(MAIN_MENU_OPTIONS);

            if (mainMenuSelection.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                ui.output(machine.toString());
            } else if (mainMenuSelection.equals(MAIN_MENU_OPTION_PURCHASE)) {

                purchaseMenuRunning = true;
                while (purchaseMenuRunning) {
                    String purchaseMenuSelection = ui.promptForSelection(PURCHASE_MENU_OPTIONS);
                    if (purchaseMenuSelection.equals(PURCHASE_MENU_FEED_MONEY)) {
                        feedMoney();
                    } else if (purchaseMenuSelection.equals(PURCHASE_MENU_SELECT_PRODUCT)) {
                        purchaseProduct();
                    } else if (purchaseMenuSelection.equals(PURCHASE_MENU_FINISH)) {
                        finishTransaction();
                        purchaseMenuRunning = false;
                    }
                }
            } else if (mainMenuSelection.equals(MAIN_MENU_EXIT)) {
                mainMenuRunning = false;
            } else if (mainMenuSelection.equals(MAIN_MENU_SALES)) { //Hidden menu option
                SalesReport.createReport(machine);
                ui.output("** Sales report created **");
            }

        }
    }

    private void prepareVendingMachine() {
        try {
            List<Item> items = InventoryParser.parseInventoryFile("inventory.txt");
            machine = new VendingMachine(items);
        } catch (IOException e) {
            ui.output("Failed to parse inventory file.");
        }
    }

    private void feedMoney() {
        BigDecimal depositAmount = ui.promptForSelectionInt("Please enter the denomination of your bill (1, 5, 10): ");
        machine.getMoneyLog().deposit(depositAmount);
        ui.output(machine.getMoneyLog().toString()); //Print remaining balance
        AuditLog.log("Feed Money: $" + depositAmount.toString() + ", " + machine.getMoneyLog().toString());
    }

    private void purchaseProduct() {
        ui.output(machine.toString());
        String itemSlotSelection = ui.promptForSelectionString(PURCHASE_MESSAGE);
        try {
            Item purchasedItem = machine.purchaseItem(itemSlotSelection);
            ui.output(purchasedItem.purchaseMessage()); //Crunch Crunch, Yum! etc.
            AuditLog.log(purchasedItem.getName() + " " + purchasedItem.getSlot()
                    + " $" + purchasedItem.getPrice() + ", " + machine.getMoneyLog().toString());
        } catch (NullPointerException e) {
            ui.output("Item does not exist");
        } catch (Exception e) { //Print message from purchaseItem() throws
            ui.output(e.getMessage());
        }
        ui.output(machine.getMoneyLog().toString()); //Print remaining balance
    }

    private void finishTransaction() {
        String balanceBeforeChangeIsGiven = machine.getMoneyLog().getBalance();
        ui.output(machine.getMoneyLog().calculateChange());
        AuditLog.log("Give Change: $" + balanceBeforeChangeIsGiven + ", " + machine.getMoneyLog().toString());
    }

}
