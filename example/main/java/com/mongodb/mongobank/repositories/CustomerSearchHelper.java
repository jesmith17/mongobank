package com.mongodb.mongobank.repositories;

import com.mongodb.mongobank.models.Address;
import com.mongodb.mongobank.models.Customer;
import com.mongodb.mongobank.models.Phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerSearchHelper {


    public static HashMap<String, Object> buildSearchMap(Customer customer) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("firstName", customer.getFirstName());
        params.put("lastName", customer.getLastName());
        params.put("title", customer.getTitle());
        params.put("email", customer.getEmail());
        params.values().removeIf(entry -> entry == null || entry.toString().isEmpty());

        List<HashMap<String, Object>> andList = new ArrayList<>();
        if (customer.getAddresses() != null ) {
            for (Address address : customer.getAddresses()) {
                HashMap<String, Object> addrParams = new HashMap<>();
                HashMap<String, Object> subParams = new HashMap<>();
                subParams.put("city", address.getCity());
                subParams.put("state", address.getState());
                subParams.put("street", address.getStreet());
                subParams.put("zip", address.getZip());
                subParams.values().removeIf(entry -> entry == null || entry.toString().isEmpty());
                addrParams.put("$elemMatch", subParams);
                HashMap<String, Object> finalParams = new HashMap<>();
                finalParams.put("addresses", addrParams);
                andList.add(finalParams);

            }
        }


        if (customer.getPhones() != null){
            for (Phone phone : customer.getPhones()) {
                HashMap<String, Object> phoneParams = new HashMap<>();
                HashMap<String, Object> subParams = new HashMap<>();
                subParams.put("number", phone.getNumber());
                subParams.put("type", phone.getType());
                phoneParams.put("$elemMatch", subParams);
                HashMap<String, Object> finalParams = new HashMap<>();
                finalParams.put("phones", phoneParams);
                andList.add(finalParams);

            }
        }
        params.put("$and", andList.toArray());

        return params;
    }




}
