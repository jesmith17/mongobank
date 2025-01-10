package com.mongodb.mongobank.controllers;

import com.mongodb.mongobank.models.Account;
import com.mongodb.mongobank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/accounts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestBody Account account, @RequestParam(name="customer_id") String customerId) {
        return accountService.createAccount(account, customerId);
    }


    @GetMapping
    public List<Account> getAccountsByCustomer(@RequestParam(name="customer_id") String customerId){
        return accountService.getAccountsByCustomer(customerId);
    }

    @GetMapping("{id}")
    public Account getAccountById(@PathVariable(name="id") String accountId) {
        return accountService.getAccountById(accountId);
    }


}
