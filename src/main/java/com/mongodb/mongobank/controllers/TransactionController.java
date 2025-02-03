package com.mongodb.mongobank.controllers;

import com.mongodb.mongobank.models.Transaction;
import com.mongodb.mongobank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="api/transactions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return this.transactionService.recordTransaction(transaction);
    }

    @GetMapping("{id}")
    public Transaction getTransaction(@PathVariable String id) {
        return this.transactionService.getTransactionById(id);
    }



}
