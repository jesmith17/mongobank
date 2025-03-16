# Find a Customer

Now that you can successfully save a record to the DB, lets work on creating ways to retrieve records. Eventually we will want to query customers by 

* Primary Key (`_id`)
* Customer Name
* Combination of fields
* Paged results


## Search by Id

Locate the `getCustomerById()` method in the customer service class and lets make the necessary code changes to do our search.


```java
public Customer getCustomerById(String id) {
    return customerRepository.findById(id).orElse(null);
} 
```

This leverages the default methods provided by the repository pattern to do the ID lookup.

## Search by multiple fields

Select at least 2 fields of the customer object to search by and implement the appropriate code to execute your search.

Use the existing code examples as a reference. Examples of options are below.

#### Custom Repository Methods ####

```java
List<Customer> getCustomersByFirstNameAndLastName(String firstName, String lastName);
```

#### MongoTemplate ####
```java
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
 ...

List<Customer> result = mongoTemplate.query(Customer.class)
                .matching(query(where("firstName").is(customer.getFirstName()).and("lastName").is(customer.getLastName())))
                .all();
```

#### `@Query` Annotation ####

```java
@Query(value="{'firstName': ?0, 'lastName': ?1}")
    List<Customer> searchCustomers(String firstName, String lastName);
```

## Query by Example

Spring-Data offers a QueryByExample function, so why not use that when doing queries by multiple fields? 
To find out, lets try it

#### Query customers by example

Update the  `CustomerRepository` class and have it extend `QueryByExampleExecutor<Customer>` as well. This enables the `findOne(Example<T>)` and `findAll(Example<T>)` methods with extensions for pagination and sorting as well. 

Update your CustomerService class to use the Query By Example format. 

```java

public Page<Customer> searchCustomers(Customer customer, int page){
    Pageable paging = PageRequest.of(page, 100);
    return customerRepository.findAll(Example.of(customer), paging);
}
```
To understand the benefits, and tradeoffs of Query by Example, conduct a couple searches for customers and see what the results are.

:::warning
Query for customers by one of the nested address fields, using query by example, and see if it returns correct results. 

:::

Notice that Query By Example does not properly format a query for nested structures. Due to the flexible nature of MQL the query language doesn't return an error, it simply finds no records matching the criteria. 

### Flexible Query by any field

<details>
  <summary>Advanced Options</summary>

    There are libraries out that will help do these types of queries, but there are options to do it using standard structures like HashMap and reflection.  To see an example
    look in the `/example` folder in the project and the `CustomerSearchHelper` class in the repository package.
    
    This example uses reflection to build a HashMap of fields and values to query by. Notice that with the `Address` object it using the `$elemMatch` operator for MongoDB.  This ensure that any criteria applied to the address object have to be satisfied inside the same entry in the address
    array before it considers the document a match.

    Combine the output of the `CustomerSearchHelper` with this update to the `CustomerRepository` class

    ```java
        @Query(value="?0", sort = "{'firstName': -1, 'lastName': 1, 'address.city':  1}")
        Page<Customer> searchCustomers(HashMap<String, Object> params, Pageable paging);

    ```
    
    This approach leverages the fact that MongoDB queries are essentially just JSON structures. The HashMap is converted to JSON when passes to the `@Query` annotation and allows you to dynamicallyk
    build the entire query as needed.  


</details>






 






