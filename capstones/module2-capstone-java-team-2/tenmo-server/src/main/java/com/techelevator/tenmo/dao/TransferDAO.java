package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDAO {
    void transferMoney(Long userIdFrom, Long userIdTo, BigDecimal amount);
    boolean createTransfer(Long userIdFrom, Long userIdTo, BigDecimal amount);
    List<Transfer> listTransfers();
    List<Transfer> listTransfersByUserId(Long userId);
    Transfer searchByTransferId(Long transferId);
    Long findIdByAccountNumber(Long accountId);
}
