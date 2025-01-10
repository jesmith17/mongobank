package com.mongodb.mongobank.controllers;

import com.mongodb.mongobank.models.Customer;
import com.mongodb.mongobank.services.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="api/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    Logger logger = LogManager.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PostMapping("/search")
    public Page<Customer> searchCustomers(@RequestBody Customer customer, @RequestParam(name="page", required = false, defaultValue = "0") int page){
        return this.customerService.searchCustomers(customer, page);

    }

    @GetMapping("{id}")
    public Customer getCustomerById(@PathVariable("id") String customerId) {
        return customerService.getCustomerById(customerId);
    }

}
