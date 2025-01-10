package com.mongodb.mongobank.models;


import org.springframework.data.mongodb.core.mapping.MongoId;

public class Phone {

    @MongoId
    private String id;

    private String type;
    private String number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
