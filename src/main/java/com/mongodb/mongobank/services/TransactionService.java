package com.mongodb.mongobank.services;

import com.mongodb.client.result.UpdateResult;
import com.mongodb.mongobank.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class TransactionService {

    @Autowired
    MongoTemplate mongoTemplate;

    public Transaction recordTransaction(Transaction txn){return null;}

    public Transaction getTransactionById(String id) {
        return null;
    }

}
