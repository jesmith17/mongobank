package com.mongodb.mongobank.models;


import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

import static org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128;

public class CreditAccount extends Account {

    private static final AccountType accountType = AccountType.CREDIT;

    @Field(targetType = DECIMAL128)
    private BigDecimal creditLimit;


    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getBalacne() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    @Override
    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public CreditAccount applyTransaction(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();
        if (transaction.getType().equals(Transaction.TransactionType.CREDIT)){
            this.balance = this.balance.subtract(amount);
        } else {
            this.balance = this.balance.add(amount);
        }
        if (balance.compareTo(creditLimit) > 0){
            this.overDraft = true;
        }
        return this;
    }

}
