package com.softwareapplication.remarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RemarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemarketApplication.class, args);
	}

}
