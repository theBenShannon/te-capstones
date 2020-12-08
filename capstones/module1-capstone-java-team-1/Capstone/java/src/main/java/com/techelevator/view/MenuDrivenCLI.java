package com.techelevator.view;

import java.math.BigDecimal;
import java.util.Scanner;

public class MenuDrivenCLI implements BasicUI{

    private final Scanner userInput = new Scanner(System.in);
    private final Menu menu = new Menu(System.in, System.out);

    @Override
    public void output(String content) {
        System.out.println(); //Print blank line
        System.out.println(content);
    }

    @Override
    public void pauseOutput() {
        System.out.println("(Press enter to continue)");
        userInput.nextLine();
    }

    @Override
    public String promptForSelection(String[] options) {
        return (String) menu.getChoiceFromOptions(options);
    }

    @Override
    public String promptForSelectionString(String message) {
        System.out.print(message);
        return userInput.nextLine();
    }

    @Override
    public BigDecimal promptForSelectionInt(String message) {
        System.out.print(message);

        int amount = 0;
        boolean invalidInput = true;
        while(invalidInput) {
            try {
                amount = Integer.parseInt(userInput.nextLine());
                if (amount == 1 || amount == 5 || amount == 10) {
                    invalidInput = false;
                } else {
                    System.out.print("Please enter in a valid bill (1, 5, 10): ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter in a valid bill (1, 5, 10): ");
            }
        }
        return BigDecimal.valueOf(amount);
    }

}
