package com.mongodb.mongobank.services;

import com.mongodb.mongobank.models.Account;
import com.mongodb.mongobank.models.Customer;
import com.mongodb.mongobank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {


    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account, String customerId) {
        return null;
    }

    public List<Account> getAccountsByCustomer(String customerId){
        return null;
    }

    public Account getAccountById(String accountId) {
        return null;
    }


}
