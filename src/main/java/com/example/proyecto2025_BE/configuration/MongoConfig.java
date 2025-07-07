package com.example.proyecto2025_BE.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@Profile("dev")
public class MongoConfig {

//    @Bean
//    public CommandLineRunner clearDatabase(MongoTemplate mongoTemplate) {
//        return args -> {
//            mongoTemplate.getDb().drop();
//            mongoTemplate.createCollection("preguntas");
//            PreguntasData.PREGUNTAS.forEach(mongoTemplate::insert);
//        };
//    }
}
