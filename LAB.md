# Hands-On Lab



## Lab 1 : Adding Persistence

First step of the lab is to add in some basic persistence to our existing system. 


### Goals
By the end of this section you will have been able to 

* successfully receive a customer via a REST call, and persist that record to the DB. 
* You will be able to retrieve that customer by ID
* You will be able to search for customers by a variety of fields
* Will have experienced the process for updating a schema in MongoDB to include new data attributes


## Table of Contents

1. [**Map Objects** ](#map-existing-models-to-mongodb-collections)
2. [**Create Repository** ](#create-the-repository-implementation)


### Steps

1. #### Map existing models to MongoDB collections

Using your editor of choice, open the `Customer.java` file located in `src/main/com/mongodb/mongobank/models`

Add the `@Document` annotation to the class

```java

@Document()
public class Customer {

    ...
}
```

This annotation tells MongoDB that we want to persist this model as a document. 

To define the primary key field for the Customer class we need to add a second annotation

```java

@Id
private String id;

```

Be sure that the import matches 
```java
import org.springframework.data.annotation.Id;
```

2. #### Create the repository implementation

We will be using the Repository pattern for handling our persistence, so we need to create our repository class

2.1. Create a new package

```java 
com.mongodb.mongobank.repositories
```

2.2. Create a repository class

```java 

public interface CustomerRepository extends MongoRepository<Customer, String> {

}

```

2.3.  Update the `CustomerService` class

```java

@Autowired
CustomerService service


public Customer createCustomer(Customer customer) {
    return service.save(customer);

}


```

2.4. Run the app and test. 

There are example JSON data sets for the objects in the data folder in the project. Use those for examples of the data payloads. 

```shell

curl --header "Content-Type: application/json" --request POST --data <example data payload goes here> http://localhost:8080/api/customers
  
```

Response should come back and show you are customer record with the ID field populated. 

** Note: You can also use the example payloads in tools like Postman if you prefer











