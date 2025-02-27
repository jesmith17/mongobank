package com.mongodb.mongobank.repositories;

import com.mongodb.mongobank.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.mongodb.mongobank.models.Customer;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.math.BigDecimal;
import java.util.HashMap;

public interface CustomerRepository extends MongoRepository<Customer, String> {


}
