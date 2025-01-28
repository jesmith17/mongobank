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

    @Query(value="?0", sort = "{'firstName': -1, 'lastName': 1, 'address.city':  1}")
    Page<Customer> searchCustomers(HashMap<String, Object> params, Pageable paging);


    Customer getCustomerById(String id);

    Customer getCustomerByAccountsId(String accountNumber);

    Page<Customer> getCustomerByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);



}
