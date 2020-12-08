package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDAO {
    BigDecimal getAccountBalance(Long userId);
    boolean canWithdraw(Long userId, BigDecimal amount);
    void withdrawMoney(Long userId, BigDecimal amount);
    void depositMoney(Long userId, BigDecimal amount);

}
