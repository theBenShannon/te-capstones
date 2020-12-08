package com.techelevator.model;

import com.techelevator.model.item.*;
import com.techelevator.util.SalesReport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SalesReportTests {

    @Test
    public void file_sales_txt_exists() {
        VendingMachine sut;

        List<Item> items = new ArrayList<>();
        items.add(new Chip("A1", "Chips", new BigDecimal("1.25")));
        items.add(new Gum("A2", "Gum", new BigDecimal("1.75")));
        items.add(new Candy("A3", "Candy", new BigDecimal("0.25")));
        items.add(new Drink("A4", "Drink", new BigDecimal("4.25")));
        sut = new VendingMachine(items);

        sut.getMoneyLog().calculateChange();

        SalesReport.createReport(sut);

        try {
            sut.purchaseItem("A1");
            sut.purchaseItem("A2");
        } catch (Exception e) {}

        File salesReport = new File("sales-report/salesreport.txt");
        Assert.assertTrue(salesReport.exists() && !salesReport.isDirectory());
    }

}
