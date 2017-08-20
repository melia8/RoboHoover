package com.melia.yoti.robohoover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RobohooverApplication {

	public static void main(String[] args) {
		SpringApplication.run(RobohooverApplication.class, args);
	}
}
