package com.domanski.juniorofferproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JuniorOfferProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JuniorOfferProjectApplication.class, args);
	}

}
