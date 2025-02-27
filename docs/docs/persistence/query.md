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



