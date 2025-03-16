package com.mongodb.mongobank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import com.mongodb.mongobank.models.Customer;

@Service
public class CustomerService {



    @Autowired
    MongoTemplate mongoTemplate;

    public Customer createCustomer(Customer customer) {
        return null;
    }

    public Customer getCustomerById(String id) {
        return null;
    }

    public Page<Customer> searchCustomers(Customer customer, int page){ return null;}

}
