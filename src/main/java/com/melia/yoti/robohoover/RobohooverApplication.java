package com.melia.yoti.robohoover;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RobohooverApplication extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "YotiHooverDb";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.1", 27017);
    }


	public static void main(String[] args) {
		SpringApplication.run(RobohooverApplication.class, args);
	}
}
