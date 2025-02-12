
## Lab 2:  Relationships

### Goals
By the end of this section you will have been able to 

* Create embedded and related relationships between classes
* Leverage `@Transactional` to enforce ACID transactions when updating mulitple records
* Explore options for using the **Subset** data modeling pattern



## Steps

### 1. Customer and Account 

Create relationship between `Customer` and `Account` classes. 

* A customer can have more than 1 account
* An account can belong to only 1 customer

`Account` class
```java
    @DocumentReference
    private Customer customer;
```
`Customer` class

```java
    @DocumentReference(lazy = true)
    @JsonBackReference
    List<Account> accounts;
```

Update logic in `AccountService` class to complete creation of an account object. 

Ensure that both the `Customer` object and the `Account` object end up with correct references to each other. 

```java
    public Account createAccount(Account account, String customerId) {
        Customer customer = customerRepository.getCustomerById(customerId);
        account.setCustomer(customer);
        mdbCustomer.getAccounts().add(account);
        accountRepository.save(account);
        customerRepository.save(mdbCustomer);
        return  account;
    }
```



> [!NOTE]
> `@DocumentReference`
> Spring-data provides 2 ways to enable relationships between MongoDB collections.
> * `@DBRef`
> * `@DocumentReference`
> 
> `@DBRef` uses a custom object type to store the linkage information. This format is harder to use in other operations (like aggregations).  `@DocumentReference` simply uses and ObjectId (or array of them depending on the relatinoship) which does not block other uses. That's why we are using it here.
>
> `@JsonBackReference` is needed to ensure that we don't get a infinite loop of Spring trying to resolve the Transaction -> Account -> Transaction -> Account loop.












### 3 Persist Transaction


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

#### Useage of `Save()`

Example above uses the `insert()` method insted of `save()`. The `insert()` method maps to the `insertOne` or `insertMany` calls in MongoDB where `save()` is a spring method can be either insert or udpate. 

It would not be appropriate for an update to an existing transction, so `insert()` is the appopriate method here. 

The other imapct of `save()` is that it will attempt to udpate every field of the document. This can be slower performing and not idempotent, which can be critical in a banking type app. 

`$inc` is one operator that MongoDB offers that can be used to increment a specific field in the document. It updates only the single field, and can be more performant.   One way to do this is to combine the `@Query` and `@Update` annotations in the repository. 


```java

    @Query(value = "{'_id': ?0 }")
    @Update("{ '$inc' : { 'balance' : ?1 }, '$set': {'overDraft':  ?2}}")
    long updateAccountBalance(String accountId, BigDecimal amount, boolean overLimit);

```
This approach allows you to just update the specific fields that you want,  instead of the entire document. It also doesn't require that the document be read into memory first in order to be updated.  Notice that this method does not return an object, but instead a `Long` representing the number of successfully updated records. 


### 4. ACID Transactions

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


### 5. Subset pattern

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














