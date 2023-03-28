package com.domanski.juniorofferproject;

import com.domanski.juniorofferproject.infrastucture.security.jwt.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {SecurityProperties.class})
public class JuniorOfferProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JuniorOfferProjectApplication.class, args);
	}

}
