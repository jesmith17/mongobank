
# Update the schema

Evolving product requirements are a common challenge in software systems. In this section we will make changes to the Object model, and review
the process for persisting those changes to the DB. 



### Add Phone Support

Add support for a collection of `Phone` objects to be stored as part of the `Customer` class.


```java
    List<Phone> phones

    List<Phone> getPhones() { return this.phones }
    void setPhones(List<Phone> phones) { this.phones = phones}
```

Add appropriate info the customer payload and re-run the save operation.
Validate that the saved customer now has a collection of phone objects  (should be similar to the Email collection already persisted)


:::info
Using searches you have already created, search for a record that existed before the `phones` attribute was added. Notice if any 
errors or challenges occur from the change. 

:::

### $elemMatch

To best leverage the new phones structure we need to update our existing customer queries to use the [$elemMatch](https://www.mongodb.com/docs/manual/reference/operator/query/elemMatch/) operator. This operator allows you to apply conditions to multiple attributes on the 
nested phone object that all must evaluate to true before the document is considered to match.  For example to search for customers where the phone type is `mobile` and the value is `913-634-5789` the mongodb query would be

```mongodb 

db.customers.find({ phone: {$elemMatch: { type: "mobile", number: "913-634-5789"}}})

```
To recreate that in Spring-Data it looks like. 

```java
    Query query = new Query();
    query.addCriteria(Criteria.where("phones").elemMatch(Criteria.where("type").is("mobile")
         .and("number").is("913-634-5789")));
    mongoTemplate.find(query, Customer.class);

```

:::tip

The advanced search approach from the previous lab can also be used here to combine this with the repository pattern. 
:::

Because MQL natively supports varied structures in the document model, queries for a specific phone combination are simply treated as not matching when
the query experiences a document that doesn't contain the attributes. This flexbility in the query language is a significant difference from SQL and can 
be a key value when evolving a data model

:::tip

 MongoDB Data Modeling patterns use a Schema Versioning pattern when making structure changes
 See [Maintain Different Schema Versions](https://www.mongodb.com/docs/manual/data-modeling/design-patterns/data-versioning/schema-versioning/)
 and [Building with Patterns: The Schema Versioning Pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-schema-versioning-pattern) for details
:::


### Email Support

An optional activity is to add in support for an array of email addresses that can be associated
with the customer as well. 




