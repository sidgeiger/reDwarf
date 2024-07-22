package com.MinerApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.MinerApp.repository")
public class MinerDwarfsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinerDwarfsApplication.class, args);
	}

}
