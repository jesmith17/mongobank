package com.mongodb.mongobank.repositories;

import com.mongodb.mongobank.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
