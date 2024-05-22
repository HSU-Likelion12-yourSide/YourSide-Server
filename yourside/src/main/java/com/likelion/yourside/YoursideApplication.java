package com.likelion.yourside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YoursideApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoursideApplication.class, args);
	}

}
