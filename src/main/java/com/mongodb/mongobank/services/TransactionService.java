package com.mongodb.mongobank.services;

import com.mongodb.mongobank.models.*;
import com.mongodb.mongobank.repositories.AccountRepository;
import com.mongodb.mongobank.repositories.CustomerRepository;
import com.mongodb.mongobank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {

    public Transaction recordTransaction(Transaction txn){
        return null;
    }

    public Transaction getTransctionById(String id) {
        return null;
    }

}
