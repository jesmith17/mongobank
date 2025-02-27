
# Subset Pattern

Using the [Subset Pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-subset-pattern) add a method to the Account
to store the 5 most recent transactions the account has had.  This can be an effective
approach to optimizing frequently used screens where showing some portion of the activity is sufficent without needing
to do a join or duplicate large portions of data.


`Transaction`
```java
    List<Transaction> recentTransactions:

    public List<Transaction> getRecentTransactions(){
        return this.recentTransactions;
    }

    public  void setRecentTransactions(List<Transaction> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }
```

Logic done with array work in Java

`TransactionService`
```java
...
    if (account.getRecentTransactions().size()>= 5){
        account.getRecentTransactions().removeLast();
    }
    account.getRecentTransactions().add(txn);
```

Logic done in the DB.

`TransactionRepository`
```java
    @Query(value = "{'_id': ?0 }")
    @Update("{ '$inc' : { 'balance' : ?1 }, '$set': {'overDraft':  ?2}, '$push': {'recentTransactions': { '$each': [?3], '$sort':  {'transactionDate': -1}, '$slice':  5}} }")
    long updateAccountBalance(String accountId, BigDecimal amount, boolean overLimit);
```
`TransactionService`
```java
    @Transactional
    public Transaction recordTransaction(Transaction txn){
        ...    
        Transaction mdbTxn= transactionRepository.save(txn);
        accountRepository.updateAccountBalance( account.getId(), amount, false, mdbTxn);
        return mdbTxn;
    }
```

The second option takes advantage of native actions in the DB to update the array values and manage its size. 

