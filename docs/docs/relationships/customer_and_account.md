# Customer and Account

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


























