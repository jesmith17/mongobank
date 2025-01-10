package com.mongodb.mongobank.services;

import com.mongodb.mongobank.repositories.CustomerRepository;
import com.mongodb.mongobank.repositories.CustomerSearchHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mongodb.mongobank.models.Customer;

import java.util.HashMap;

@Service
public class CustomerService {


    @Autowired
    CustomerRepository mongoRepository;


    public Customer createCustomer(Customer customer) {
        return mongoRepository.save(customer);

    }

    public Customer getCustomerById(String id) {
        return mongoRepository.getCustomerById(id);


    }


    public Page<Customer> searchCustomers(Customer customer, int page){
        Pageable paging = PageRequest.of(page, 100);
        HashMap<String, Object> params = CustomerSearchHelper.buildSearchMap(customer);
        return mongoRepository.searchCustomers(params, paging);




    }


}
