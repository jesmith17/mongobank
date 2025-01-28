package com.mongodb.mongobank.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "accountType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditAccount.class, name = "CREDIT"),
        @JsonSubTypes.Type(value = SavingsAccount.class, name = "SAVINGS")
})
public abstract class Account {

    @Id
    protected String id;

    private String accountName;

    protected boolean overDraft = false;

    @Field(targetType = DECIMAL128)
    protected BigDecimal balance;

    private Customer customer;

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
