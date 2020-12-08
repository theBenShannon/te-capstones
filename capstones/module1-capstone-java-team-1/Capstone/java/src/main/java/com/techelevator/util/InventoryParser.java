package com.techelevator.util;

import com.techelevator.model.item.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryParser {

    public static List<Item> parseInventoryFile(String fileName) throws IOException {
        List<Item> items = new ArrayList<>();
        Path file = Paths.get(fileName);

        //Classifying items based on class of food
        try (Scanner fileInput = new Scanner(file)) {
            while (fileInput.hasNextLine()) {
                String[] splitLine = fileInput.nextLine().split("\\|");
                if (splitLine[3].equals("Chip")) {
                    items.add(new Chip(splitLine[0], splitLine[1], new BigDecimal(splitLine[2])));
                } else if (splitLine[3].equals("Candy")) {
                    items.add(new Candy(splitLine[0], splitLine[1], new BigDecimal(splitLine[2])));
                } else if (splitLine[3].equals("Drink")) {
                    items.add(new Drink(splitLine[0], splitLine[1], new BigDecimal(splitLine[2])));
                } else if (splitLine[3].equals("Gum")) {
                    items.add(new Gum(splitLine[0], splitLine[1], new BigDecimal(splitLine[2])));
                }
            }
        } catch (IOException e) {
            throw new IOException();
        }

        return items;
    }

}
