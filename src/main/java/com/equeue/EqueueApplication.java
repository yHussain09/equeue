package com.equeue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EqueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(EqueueApplication.class, args);
	}

}
