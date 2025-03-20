
# Polymorphic

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

Spring-Data will automatically add an attribute to each class specifying what concrete implementation is should be serialized back into. 

```json

{
  ...,
  _class: "com.mongodb.mongobank.models.SavingsAccount"
}

```




:::note
  Adding additional properties to the individual class types will automatically ask MongoDB to persist them to the DB.
:::

This enables you to be able to persist account types in the same collection. Some of the benefits include

* Ability query all accounts by customer without need for a union operation
* Ability to do aggregations and operations across all account types
* Simplified indexes and reduce index overhead

:::tip
 MongoDB supports [Partial indexes](https://www.mongodb.com/docs/manual/core/index-partial/) that can be applied only to object matching certain criteria. Use this to create indexes just for `CreditAccount` specific fields
 Allows reduced index size without need to break data into it's own collection. 
:::