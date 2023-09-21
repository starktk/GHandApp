package com.example.GHand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class GHandApplication {

	public static void main(String[] args) {
		SpringApplication.run(GHandApplication.class, args);
	}

}
