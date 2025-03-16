# ACID Transactions

ACID compliant transcations are critical for systems like banking applications, where significant challenges can arise if not all related objects
are updated in a consistent and atomic manner. In this section we will add in the necessary logic to ensure ACID compliance is being enforced on our `recordTransaction` method




Add the `@Transactional` annotation
```java 
    @Transactional
    public Transaction recordTransaction(Transaction txn){
        Account account = accountRepository.findById(txn.getAccountNumber()).get();
        BigDecimal amount = txn.getType().equals(Transaction.TransactionType.CREDIT) ? txn.getAmount() : txn.getAmount().multiply(BigDecimal.valueOf(-1L));

```
Enable the `MongoTransactionManager` in the `MongoBankApplication` class (it's already provided simply uncomment and restart)
```java

        @Bean
        MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
            return new MongoTransactionManager(dbFactory);
        }

```


Test that transactions are now atomic by setting the `txnId` attribute of the REST payload to the ID of
an existing DB transaction.

```
ObjectId('6797b33659ee7c44a74d63c3')

```


:::tip
Most modern IDE's, including IntelliJ and Visual Studio Code, offer MongoDB plugins that allow you to browse the DB directly from your IDE. 
You can easily use one of those to lookup an existing value from your DB. 

Or you can use the returned ID value of your REST calls and simply format it like the example below. 

:::


:::warning
#### Didn't get an error?

If you didn't get an error when running this, check the logs for the statement that was issued. Do you see something like

```
Command "update" started on database "bank" ...

```
:::


If so, the go review your code. In the transaction service you are probably issuing a `.save()` command.
The behavior of `.save()` is that it will either do an insert or an update. So when you provided the Txn with the primary key field
already populated it turned it into a save.

But that method should never do a save, only inserts. So replace `.save()` with `.insert()` and run the test again. 

