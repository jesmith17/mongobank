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

        Customer mdbCustomer = customerRepository.getCustomerById(customerId);
        account.setCustomer(mdbCustomer);
        mdbCustomer.getAccounts().add(account);
        accountRepository.save(account);
        customerRepository.save(mdbCustomer);
        return  account;
    }

    public List<Account> getAccountsByCustomer(String customerId){
        Customer mdbCustomer = customerRepository.getCustomerById(customerId);
        return mdbCustomer.getAccounts();

    }

    public Account getAccountById(String accountId) {
        return accountRepository.findById(accountId).get();
    }


}
