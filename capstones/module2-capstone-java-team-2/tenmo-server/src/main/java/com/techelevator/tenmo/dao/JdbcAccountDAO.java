package com.techelevator.tenmo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDAO implements AccountDAO{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public BigDecimal getAccountBalance(Long userId) {
        BigDecimal balance = new BigDecimal("0");
        String sql = "SELECT balance FROM accounts where user_id = ?;";
        balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);//null from here
        return balance;
    }
    @Override
    public boolean canWithdraw(Long userId, BigDecimal amount) {
        boolean hasEnuff = false;
        BigDecimal balance = new BigDecimal("0");
        balance = getAccountBalance(userId);
        if(balance.compareTo(amount) >= 0){
            hasEnuff = true;
        }
        return hasEnuff;
    }

    @Override
    public void withdrawMoney(Long userId, BigDecimal amount) {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE user_id = ?";
        jdbcTemplate.update(sql, amount, userId);
    }

    @Override
    public void depositMoney(Long userId, BigDecimal amount) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
        jdbcTemplate.update(sql, amount, userId);
    }


}
