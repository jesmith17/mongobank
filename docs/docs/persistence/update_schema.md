
# Update the schema

Add support for a collection of `Phone` objects to be stored as part of the `Customer` class.


```java
    List<Phone> phones

    List<Phone> getPhones() { return this.phones }
    void setPhones(List<Phone> phones) { this.phones = phones}
```

Add appropriate info the customer payload and re-run the save operation.
Validate that the saved customer now has a collection of phone objects  (should be similar to the Email collection already persisted)


> [!TIP]
>
> MongoDB Data Modeling patterns use a Schema Versioning pattern when making structure changes
> See [Maintain Different Schema Versions](https://www.mongodb.com/docs/manual/data-modeling/design-patterns/data-versioning/schema-versioning/)
> and [Building with Patterns: The Schema Versioning Pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-schema-versioning-pattern) for details
> 



