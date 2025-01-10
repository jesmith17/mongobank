package com.mongodb.mongobank.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;
import java.util.List;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "accountType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditAccount.class, name = "CREDIT"),
        @JsonSubTypes.Type(value = SavingsAccount.class, name = "SAVINGS")
})
public abstract class Account {

    protected String id;

    private String accountName;

    protected boolean overDraft = false;

    protected BigDecimal balance;

    private Customer customer;

    private List<Transaction> recentTransactions;

    public abstract Account applyTransaction(Transaction transaction);


    public boolean isOverDraft() {
        return overDraft;
    }

    public void setOverDraft(boolean overDraft) {
        this.overDraft = overDraft;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Transaction> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<Transaction> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountName(){
        return accountName;
    }

    public void setAccountName(String accountName){
        this.accountName = accountName;
    }

    public abstract AccountType getAccountType();


    enum AccountType {
        SAVINGS,
        CHECKING,
        CREDIT,
        MONEY_MARKET,
        BUSINESS,
        INVESTMENT
    }




}
