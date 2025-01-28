# Persistence

First step of the lab is to add in some basic persistence to our existing system. 

## Goals
By the end of this section you will have been able to 

* Successfully receive a customer via a REST call, and persist that record to the DB. 
* You will be able to retrieve that customer by ID
* You will be able to search for customers by a variety of fields
* Update an existing object. 
* Will have experienced the process for updating a schema in MongoDB to include new data attributes


## Table of Contents

1. [**Persist** ](#1--persist-a-customer--update)
2. [**Query** ](#2-retrieve-customer)
3. [**Update Schema**](#3-update-schema)



## Steps


### 1.  Persist a Customer & Update

Using your editor of choice, open the `Customer.java` file located in `src/main/com/mongodb/mongobank/models`

Update your `Customers` class to match the following. (some content omitted to breivity)

```java
import org.springframework.data.annotation.Id;

@Document("customers")
public class Customer {
    
    @Id
    private String id;

    ...

}
```

`@Document()` tells spring-data that we want to treat this object as a document and with the collection mame of "customers"

`@Id` identifies the field that we want spring-data to map our `_id` field to in MongoDB (required field in each document, serves as the primary key)

Create a new package for the repository classes. And inside create a new `CustomerRepository` class with the signature found below. 

```java 
com.mongodb.mongobank.repositories
```

Repository class snippet
```java 
public interface CustomerRepository extends MongoRepository<Customer, String> {

}
```

Finally Update the `CustomerService` class

``` java
@Autowired
CustomerRepository customerRepository

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
```


There are example JSON data sets for the objects in the data folder in the project. Use those for examples of the data payloads. 

```shell

curl --header "Content-Type: application/json" --request POST --data <example data payload goes here> http://localhost:8080/api/customers
  
```

Response should come back and show you are customer record with the ID field populated. 

** Note: You can also use the example payloads in tools like Postman if you prefer


#### Updating a customer

Execute the above REST call but change some of the data attributes and observe the behavior of the customer record. 

### ? What could be done to prevent the update from happening ? ### 



### 2. Retrieve Customer

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

### 3. Update Schema

Add support for a collection of `Phone` objects to be stored as part of the `Customer` class. 


```java
    List<Phone> phones

    List<Phone> getPhones() { return this.phones }
    void setPhones(List<Phone> phones) { this.phones = phones}
```

Add appropriate info the customer payload and re-run the save operation. 
Validate that the saved customer now has a collection of phone objects  (should be similar to the Email collection already persisted)












