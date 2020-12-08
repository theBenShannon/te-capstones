package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class JdbcTransferDAO implements TransferDAO{
private AccountDAO accountDAO;

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDAO(AccountDAO accountDAO, JdbcTemplate jdbcTemplate){
        this.accountDAO = accountDAO;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void transferMoney(Long userIdFrom, Long userIdTo, BigDecimal amount) {
        if(accountDAO.canWithdraw(userIdFrom, amount)){
            accountDAO.withdrawMoney(userIdFrom, amount);
            accountDAO.depositMoney(userIdTo, amount);
            try{
                String sql = "INSERT into transfers (account_from, account_to, amount, transfer_type_id, transfer_status_id) VALUES " +
                        "((SELECT account_id FROM accounts WHERE user_id = ?),(SELECT account_id FROM accounts WHERE user_id = ?),?,2,2);";
                jdbcTemplate.update(sql,userIdTo, userIdFrom, amount);
            } catch (DataAccessException e) {

            }
        }
    }

    @Override
    public boolean createTransfer(Long userIdFrom, Long userIdTo, BigDecimal amount) {
        return false;
    }

    @Override
    public List<Transfer> listTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfers.transfer_id, transfers.transfer_type_id, transfers.transfer_status_id, transfers.account_from, transfers.account_to, transfers.amount, userFrom.username as fromUserName, userTo.username as toUserName FROM transfers\n" +
                "JOIN accounts AS accountFrom ON accountFrom.account_id = transfers.account_from\n" +
                "JOIN users AS userFrom ON accountFrom.user_id = userFrom.user_id\n" +
                "JOIN accounts AS accountTo ON accountTo.account_id = transfers.account_to\n" +
                "JOIN users AS userTo ON accountTo.user_id = userTo.user_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public List<Transfer> listTransfersByUserId(Long userId) {
        List<Transfer> result = new ArrayList<>();

        String sql = "SELECT transfers.transfer_id, transfers.transfer_type_id, transfers.transfer_status_id, transfers.account_from, transfers.account_to, transfers.amount, userFrom.username as fromUserName, userTo.username as toUserName FROM transfers\n" +
                "JOIN accounts AS accountFrom ON accountFrom.account_id = transfers.account_from\n" +
                "JOIN users AS userFrom ON accountFrom.user_id = userFrom.user_id\n" +
                "JOIN accounts AS accountTo ON accountTo.account_id = transfers.account_to\n" +
                "JOIN users AS userTo ON accountTo.user_id = userTo.user_id\n" +
                "WHERE accountFrom.user_id = ? OR accountTo.user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while(rowSet.next()){
            Transfer transfer = mapRowToTransfer(rowSet);
            result.add(transfer);
        }
        return result;
    }

    public Long getAccountId(Long userId) {
        String sql = "SELECT account_id FROM accounts WHERE user_id = ?;";
        Long result = jdbcTemplate.queryForObject(sql,  Long.class, userId);
        return result;
    }


    @Override
    public Transfer searchByTransferId(Long transferId) {
        Transfer result = new Transfer();
        List<Transfer> transfers = listTransfers();
        for(Transfer transfer : transfers) {
            if(transfer.getTransferId() == transferId) {
                result = transfer;
            }
        }
        return result;
    }
//    private void getUsernameForTransfer(){
//        String sql = "SELECT username FROM users WHERE "
//    }
    @Override
    public Long findIdByAccountNumber(Long accountId) {
        String sql = "SELECT user_id FROM accounts WHERE account_id = ?;";
        Long result = jdbcTemplate.queryForObject(sql, Long.class, accountId);
        return result;
    }



    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getLong("transfer_id"));
        transfer.setTransferTypeId(rowSet.getLong("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getLong("transfer_status_id"));
        transfer.setAccountFrom(rowSet.getLong("account_from"));
        transfer.setAccountTo(rowSet.getLong("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        transfer.setFromUsername(rowSet.getString("fromUserName"));
        transfer.setToUsername(rowSet.getString("toUserName"));
        return transfer;
    }


}
