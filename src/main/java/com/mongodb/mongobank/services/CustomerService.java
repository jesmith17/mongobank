package com.mongodb.mongobank.services;

import com.mongodb.mongobank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.mongodb.mongobank.models.Customer;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.HashMap;
import java.util.List;

@Service
public class CustomerService {


    @Autowired
    CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return null;
    }

    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Page<Customer> searchCustomers(Customer customer, int page){
        return null;
    }

}
