package com.mongodb.mongobank.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128;
public class Transaction {

    private String txnId;
    private String accountNumber;
    private TransactionType type;
    @Field(targetType = DECIMAL128)
    private BigDecimal amount;
    private Timestamp transactionDate = new Timestamp(System.currentTimeMillis());
    private String authCode;
    private String description;
    private String merchantId;


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    public enum TransactionType {
        CREDIT,
        DEBIT
    }



}
