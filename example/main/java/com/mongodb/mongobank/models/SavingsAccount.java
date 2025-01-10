package com.mongodb.mongobank.models;

import java.math.BigDecimal;


public class SavingsAccount extends Account {

    private static final AccountType type = AccountType.SAVINGS;

    @Override
    public AccountType getAccountType() {
        return type;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public SavingsAccount applyTransaction(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();
        if (transaction.getType() == Transaction.TransactionType.CREDIT){
            this.balance = this.balance.add(amount);
        } else {
            this.balance = this.balance.subtract(amount);
        }
        if (this.balance.compareTo(BigDecimal.ZERO) < 0){
            this.overDraft = true;
        }
        return this;

    }
}
