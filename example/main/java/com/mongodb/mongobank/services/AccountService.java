package com.mongodb.mongobank.services;

import com.mongodb.mongobank.models.Account;
import com.mongodb.mongobank.models.Customer;
import com.mongodb.mongobank.repositories.AccountRepository;
import com.mongodb.mongobank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;


    public Account createAccount(Account account, String customerId) {

        Customer customer = customerRepository.getCustomerById(customerId);
        account.setCustomer(customer);
        accountRepository.save(account);
        return  account;
    }

    public List<Account> getAccountsByCustomer(String customerId){
        return null;

    }

    public Account getAccountById(String accountId) {
        return accountRepository.findById(accountId).get();
    }


}
