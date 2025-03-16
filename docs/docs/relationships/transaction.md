
# Record a Transaction

Lets records a banking transaction against our bank account. Start with the `TransactionService` class that we haven't used to this point. 

Using the method signature defined in the `recordTransaction` method, implement the additional logic to recording a banking transaction.

```java
public Transaction recordTransaction(Transaction txn){
    return null;
}
```

Key things to implement.

* Persisting of the banking transaction
* Update the account balance as appropriate
* Define if the account has a negative balance and updated the `overDraft` attribute accordingly.



```java
    @Document("transactions")
    public class Transaction {
        ...

        @DocumentReference
        @JsonBackReference
        private Account account;

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }
```

`TrasactionSevice` Example

```java
    public Transaction recordTransaction(Transaction txn){
        Account account = accountRepository.findById(txn.getAccountNumber()).get();
        BigDecimal amount = txn.getType().equals(Transaction.TransactionType.CREDIT) ? txn.getAmount() : txn.getAmount().multiply(BigDecimal.valueOf(-1L));
        txn.setAccount(account);
        Transaction mdbTxn = transactionRepository.insert(txn);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        return mdbTxn;
    }
```

#### Usage of `Save()`

Example above uses the `insert()` method insted of `save()` when inserting the `txn` record. The `insert()` method maps to the `insertOne` or `insertMany` calls in MongoDB where `save()` is a spring method can be either insert or udpate.

It would not be appropriate for an update to an existing transaction, so `insert()` is the appropriate method here.

The other impact of `save()` is that it will attempt to update every field of the document. This can be slower performing and not idempotent, which can be critical in a banking type app.

`$inc` is one operator that MongoDB offers that can be used to increment a specific field in the document. It updates only the single field, and can be more performant.   One way to do this is to combine the `@Query` and `@Update` annotations in the repository.


```java

    @Query(value = "{'_id': ?0 }")
    @Update("{ '$inc' : { 'balance' : ?1 }, '$set': {'overDraft':  ?2}}")
    long updateAccountBalance(String accountId, BigDecimal amount, boolean overLimit);

```
This approach allows you to just update the specific fields that you want,  instead of the entire document. It also doesn't require that the document be read into memory first in order to be updated.  Notice that this method does not return an object, but instead a `Long` representing the number of successfully updated records.

:::note


        You can also use the `inc` method with the `MongoTemplate` pattern

```java
UpdateResult result = mongoTemplate.update(Account.class)
                .matching(where("_id").is(txn.getAccountNumber()))
                .apply(new Update().inc("accounts.$.balance", 50.00))
                .all();


```
[Spring Data MongoDB Updating Documents](https://docs.spring.io/spring-data/mongodb/reference/mongodb/template-crud-operations.html#mongodb-template-update.update)
:::
