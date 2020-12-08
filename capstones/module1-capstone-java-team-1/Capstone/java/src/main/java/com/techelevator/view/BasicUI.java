package com.techelevator.view;

import java.math.BigDecimal;

public interface BasicUI {

    void output(String content);

    void pauseOutput();

    String promptForSelection(String[] options);

    String promptForSelectionString(String message);

    BigDecimal promptForSelectionInt(String message);
}
