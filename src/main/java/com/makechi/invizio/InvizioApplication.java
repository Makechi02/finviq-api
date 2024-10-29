package com.makechi.invizio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
@EnableMongoAuditing
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class InvizioApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Africa/Nairobi")));
		SpringApplication.run(InvizioApplication.class, args);
	}

}
