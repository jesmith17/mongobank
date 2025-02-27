# Find a Customer

#### 2.1  Search by ID

Locate the `getCustomerById()` method in the customer service class and lets make the necessary code changes to do our search.


```java
public Customer getCustomerById(String id) {
    return customerRepository.findById(id).orElse(null);
} 
```

This leverages the default methods provided by the repository pattern to do the ID lookup.

#### 2.2 Search by other fields

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

