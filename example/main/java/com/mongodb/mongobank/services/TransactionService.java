package com.mongodb.mongobank.services;

import com.mongodb.mongobank.models.*;
import com.mongodb.mongobank.repositories.CustomerRepository;
import com.mongodb.mongobank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {


    @Autowired
    TransactionRepository transactionMongoRepository;

    @Autowired
    CustomerRepository customerMongoRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Transaction recordTransaction(Transaction txn){
        Customer customer = customerMongoRepository.getCustomerByAccountsId(txn.getAccountNumber());
        Account account = customer.getAccounts().stream().filter(data -> data.getId().equals(txn.getAccountNumber())).findFirst().get();
        BigDecimal amount = txn.getType().equals(Transaction.TransactionType.CREDIT) ? txn.getAmount() : txn.getAmount().multiply(BigDecimal.valueOf(-1L));
        Transaction mdbTxn = transactionMongoRepository.save(txn);
        customerMongoRepository.updateCustomerBalance(customer.getId(), account.getId(), amount, false, txn);
        return mdbTxn;




    }




}
