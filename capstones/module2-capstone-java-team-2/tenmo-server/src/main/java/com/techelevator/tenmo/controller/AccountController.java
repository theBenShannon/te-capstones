package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    private final AccountDAO accountDAO;
    private final UserDAO userDAO;

    public AccountController(AccountDAO accountDAO, UserDAO userDAO){
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
    }
    @RequestMapping(path = "/users/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal){
        String username = principal.getName();
        Long userId = userDAO.findIdByUsername(username);
        return accountDAO.getAccountBalance(userId);
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public HashMap<String, Long> getUsers(){
        HashMap<String, Long> result = new HashMap<>();

        for(User user: userDAO.displayUsers()){
            result.put(user.getUsername(), user.getId());
        }
        return result;
    }
//    @RequestMapping( path = "/whoami")
//    public String whoAmI(Principal principal) {
//        return principal.getName();
//    }
}
