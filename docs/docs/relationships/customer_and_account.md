# Customer and Account

In the previous section we create a relationship between the `Cusotmer` and their associated `Phone` entries. This type of `owned-by` relationship
 is easy to model in MongoDB as it only requires creating the object structure. But not all relationships that need to be modeled
are `owned-by` relationships. This next section will focus on how to create and manage relationships where embedding is not the best practice. 

## Referencing Relationships

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
:::note
This behavior should feel very familiar for those users accustomed to Spring. The underlying DB (MongoDB or RDBMS) doesn't actually require that the cusotmer object 
is retrieved from the DB first. You could simply write the document directly to the DB ensuring that the foreign key field was correctly populated, and the retrieval DB operations would succeed. This pattern of
forcing object retrieval is a requirement of spring and how it wants to be aware of an object so it can manage those relationships at persistence. 
:::


 `@DocumentReference`
 Spring-data provides 2 ways to enable relationships between MongoDB collections.
 * `@DBRef`
 * `@DocumentReference`

:::tip
`@DocumentReference` simply uses and ObjectId (or array of them depending on the relationship) which does not block other uses.
`@DBRef` uses a custom object type to store the linkage information. This format is harder to use in other operations (like aggregations).   
For those reasons I recommend using `@DocumentReference` and that's why we are using it here.
:::


:::warning
`@JsonBackReference` is needed to ensure that we don't get a infinite loop of Spring trying to resolve the Transaction -> Account -> Transaction -> Account loop. If you find that 
your query is timing out and/or hitting an OOM exception, check that you are correctly using the `@JsonBackReference` annotation
:::

























