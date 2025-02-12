# Advanced Topics

Cover additional advanced topics for individuals who want to address more complex scenarios. 

## Table of Contents

1. [**Polymorphic** ](#1--persist-a-customer--update)
2. [**Full Text Search** ](#2-retrieve-customer)




## Polymorphic

Update the account object to be polymorhpic structure that can support multiple types of accounts structures
* Savings
* Credit
* Retirement/Investment

Start by adding in support for a `SavingsAccount` and a `CreditAccount` classes to extend from `Account`

Make `Account` an abstract class

```java
public abstract class Account {
```

Create an ENUM for the account type identifiers and add the property to the account class
```java

public abstract AccountType getAccountType();

enum AccountType {
        SAVINGS,
        CHECKING,
        CREDIT,
        MONEY_MARKET,
        BUSINESS,
        INVESTMENT
    }
```

Implement the new instances that extend from `Account` using the example below

```java
public class CreditAccount extends Account {
    
    public AccountType getAccountType() { return AccountType.SAVINGS; }
    
    // Additional methods as appropriate
}
```

Lastly add logic to the account class to allow Spring to know which type to instantiate in the controller level. 

```java
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "accountType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditAccount.class, name = "CREDIT"),
        @JsonSubTypes.Type(value = SavingsAccount.class, name = "SAVINGS")
})
public abstract class Account {
```

> [!NOTE]
>  Adding additional properties to the individual class types will automatically ask MongoDB to persist them to the DB.

This enables you to be able to persist account types in the same collection. Some of the benefits include

* Ability query all accounts by customer without need for a union operation
* Ability to do aggregations and operations across all account types
* Simplified indexes and reduce index overhead

> [!TIP]
> MongoDB supports Partial indexes that can be applied only to object matching certain criteria. Use this to create indexes just for `CreditAccount` specific fields
> Allows reduced index size without need to break data into it's own collection. 




## Full Text Search

Add in support for full text search using Atlas Search to gain ability to search text blocks for content. 

Using the `Customer` model, add in support for fuzzy matching on customer name.

Create the following method in your `CustomerRepository` class

```java

List<Customer> searchCustomersByName(String searchText);

```

Leveraging the `@Aggregation` method we will implement an Atlas Search query against the data. Directly above the new method add this

```java

@Aggregation(pipeline= {"{'$search': {index:'customer_search', text:{ query:?0, fuzzy:{},path:['lastName','firstName','address.city','address.state','address.zip','phones.number','emails.email']}}}"})

```
At the terminal run the following command

```shell

mongosh "mongodb://localhost:27017/?directConnection=true"
```

Once inside the mongosh run the following to create the index

```shell
use bank;

db.customers.createSearchIndex("customer_search", {mappings: {dynamic: true}})
```

This creates an Atlas Search index using the dynamic mappings, which indexes all of the fields in the document.

Update additional controller and service code as needed to use the new search component. 

> [!TIP]
> Have the controller accept a single string input that would be similar to a Google Search type interface. 
>





