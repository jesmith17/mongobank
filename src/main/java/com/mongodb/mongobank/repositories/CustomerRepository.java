package com.mongodb.mongobank.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.mongodb.mongobank.models.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
