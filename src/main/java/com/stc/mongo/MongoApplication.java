package com.stc.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;

@SpringBootApplication
public class MongoApplication {

    @Autowired
    MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(MyMongoRepository repository) {
        return args -> {
            repository.insert(new Post("Post1", Arrays.asList("Comment 1 (post1)", "Comment 2 (post1)")));
            repository.insert(new Post("Post2", Arrays.asList("Comment 1 (post2)", "Comment 2 (post2)")));
            System.out.println("FindAll!");
            System.out.println(repository.findAll());
            System.out.println("FindByComment!");
            System.out.println(repository.findByCommentsContains("Comment 1 (post2)"));


            System.out.println("===========MongoTemplate FindByComment============");
            Query search = new Query(Criteria.where("comments").is("Comment 1 (post2)"));
            System.out.println(mongoTemplate.find(search, Post.class));

        };
    }

}
