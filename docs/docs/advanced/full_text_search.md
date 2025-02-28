# Advanced Topics

Cover additional advanced topics for individuals who want to address more complex scenarios. 


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





