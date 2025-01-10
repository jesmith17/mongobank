package com.mongodb.mongobank.services;

import com.mongodb.mongobank.models.Account;
import com.mongodb.mongobank.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {



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
