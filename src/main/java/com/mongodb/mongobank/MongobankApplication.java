package com.mongodb.mongobank;

import org.bson.types.Decimal128;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
@EnableMongoRepositories(basePackages= "com.mongodb.mongobank.repositories")
public class MongobankApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongobankApplication.class, args);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(

            new Converter<BigDecimal, Decimal128>() {

                @Override
                public Decimal128 convert(@NonNull BigDecimal source) {
                    return new Decimal128(source);
                }
            },

            new Converter<Decimal128, BigDecimal>() {

                @Override
                public BigDecimal convert(@NonNull Decimal128 source) {
                    return source.bigDecimalValue();
                }

            },

            new Converter<Date, Timestamp>() {

                @Override
                public Timestamp convert(@NonNull Date source) {
                    return new Timestamp(source.getTime());
                }

            },
            new Converter<Timestamp, Date>() {

                @Override
                public Date convert(@NonNull Timestamp source) {
                    return new Date(source.getTime());
                }

            }


        ));

    }
}
