package com.mongodb.mongobank.repositories;

import com.mongodb.mongobank.models.Customer;
import com.mongodb.mongobank.models.Transaction;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query(value="?0", sort = "{'firstName': -1, 'lastName': 1, 'address.city':  1}")
    Page<Customer> searchCustomers(HashMap<String, Object> params, Pageable paging);


    Customer getCustomerById(String id);

    Customer getCustomerByAccountsId(Integer accountNumber);


    @Query(value = "{'_id': ?0, 'accounts._id': ?1 }")
    @Update("{ '$inc' : { 'accounts.$.balance' : ?2 }, '$set': {'accounts.$.overDraft':  ?3}, '$push': {'accounts.$.recentTransactions': { '$each': [?4], '$sort':  {'transactionDate': -1}, '$slice':  5}} }")
    long updateCustomerBalance(String customerId, String accountId, BigDecimal amount, boolean overLimit, Transaction transaction);

}

