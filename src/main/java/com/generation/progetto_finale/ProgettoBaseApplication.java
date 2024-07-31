package com.generation.progetto_finale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProgettoBaseApplication 
{

	public static void main(String[] args) {
		SpringApplication.run(ProgettoBaseApplication.class, args);
	}

}
