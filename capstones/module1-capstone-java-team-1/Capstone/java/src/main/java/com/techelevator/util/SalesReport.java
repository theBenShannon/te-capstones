package com.techelevator.util;

import com.techelevator.model.VendingMachine;
import com.techelevator.model.item.Item;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class SalesReport {

    public static void createReport(VendingMachine machine) {
        try (PrintWriter writer = new PrintWriter("sales-report/salesreport.txt")) {

            Map<Item, Integer> inventorySold = machine.getInventorySold();
            for (Map.Entry<Item, Integer> item : inventorySold.entrySet()) {
                writer.println(item.getKey().getName() + " | " + item.getValue());
            }
            writer.println("\n**TOTAL SALES** $" + machine.getTotalSales());

        } catch (IOException e) {
            System.out.println("Failed to generate sales report");
        }
    }

}
