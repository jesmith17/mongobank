# ACID Transactions

Implement ACID transaction support for this method to ensure that both writes occur as an Atomic operation.

Add `@Transactional` annotation
```java 
    @Transactional
    public Transaction recordTransaction(Transaction txn){
        Account account = accountRepository.findById(txn.getAccountNumber()).get();
        BigDecimal amount = txn.getType().equals(Transaction.TransactionType.CREDIT) ? txn.getAmount() : txn.getAmount().multiply(BigDecimal.valueOf(-1L));

```
Enable the MongoDBTransactionManager in `MongoBankApplication` class (it's already provided simple uncomment and restart)
```java

        @Bean
        MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
            return new MongoTransactionManager(dbFactory);
        }

```


Test that transcations are now atomic by setting the `txnId` attribute of the REST payload to the ID of
and existing DB transaction.

```
ObjectId('6797b33659ee7c44a74d63c3')

```

#### Didn't get an error?

If you didn't get an error when running this, check the the logs for the statement that was issued. Do you see something like

```
Command "update" started on database "bank" ...

```

If so, the go review your code. In the transaction service you are probably issuing a `.save()` command.
The behavior of `.save()` is that it will either do an insert or an update. So when you provided the Txn with the primary key field
already populated it turned it into a save.

But that method should never do a save, only inserts. So replace `.save()` with `.insert()` and run the test again. 

