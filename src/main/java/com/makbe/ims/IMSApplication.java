package com.makbe.ims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class IMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(IMSApplication.class, args);
	}

}
