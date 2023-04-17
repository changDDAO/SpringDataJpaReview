package com.changddao.SpringDataJpa2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringDataJpa2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpa2Application.class, args);
	}

}
