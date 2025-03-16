# Saving Objects

Focus of this section will be on learning how to save a Java object to MongoDB using the Repository pattern in Spring. Multiple approaches exist
for persistence in Spring-Data, but the repository pattern offers some unique advantages, and is typically familiar to Spring-data users due to is wide adoption in JPA. 
We will cover how to use the pattern with MongoDB, and where some of the other options have challenges or benefits. 

## Update Customer Object

Using your editor of choice, open the `Customer.java` file located in `src/main/com/mongodb/mongobank/models`

Update your `Customers` class to match the following. (some content omitted to brevity)

```java
import org.springframework.data.annotation.Id;

@Document("customers")
public class Customer {
    
    @Id
    private String id;

    ...

}
```

`@Document()` tells spring-data that we want to treat this object as a document and with the collection mame of "customers". 


`@Id` identifies the field that we want spring-data to map our `_id` field to in MongoDB (required field in each document, serves as the primary key)

:::tip
The collection name attribute is optional, but used here to make sure the collection name is plural, which tends to align with most organization naming standards.

:::


## Create Customer Repository

Create a new package for the repository classes. And inside create a new `CustomerRepository` class with the signature found below.

```java 
com.mongodb.mongobank.repositories
```

Repository class snippet
```java 
public interface CustomerRepository extends MongoRepository<Customer, String> {

}
```

## Update service class

Finally Update the `CustomerService` class

``` java
@Autowired
CustomerRepository customerRepository

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
```

## Testing


There are example JSON data sets for the objects in the data folder in the project. Use those for examples of the data payloads.

```shell

curl --header "Content-Type: application/json" --request POST --data <example data payload goes here> http://localhost:8080/api/customers
  
```



Response should come back and show you are customer record with the ID field populated.

:::tip
You can also use the example payloads in tools like Postman if you prefer
:::


#### Updating a customer

Execute the above REST call but change some of the data attributes and observe the behavior of the customer record. 

## Complete for Remaining Classes

Using the pattern above, complete the changes needed for the following objects

* Account
* Transaction
