package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    private final AccountDAO accountDAO;
    private final TransferDAO transferDAO;
    private final UserDAO userDAO;

    public TransferController(AccountDAO accountDAO, TransferDAO transferDAO, UserDAO userDAO) {
        this.accountDAO = accountDAO;
        this.transferDAO = transferDAO;
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public void doTransfer(@RequestBody TransferDTO transferDTO){
       transferDAO.transferMoney(transferDTO.getIdFrom(),
               transferDTO.getIdTo(), transferDTO.getAmount());
    }

    @RequestMapping(value = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer listTransfersByTransferId(@PathVariable Long id){
        return transferDAO.searchByTransferId(id);
    }

    @RequestMapping(value = "/users/transfer", method = RequestMethod.GET)
    public List<Transfer> listTransfersOfUser(Principal principal){
        String username = principal.getName();
        Long userId = userDAO.findIdByUsername(username);
        List<Transfer> result = transferDAO.listTransfersByUserId(userId);

        return result;
    }



}
