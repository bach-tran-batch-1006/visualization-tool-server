package com.revature.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.revature.app.model")
public class CurriculaVisualizationToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurriculaVisualizationToolApplication.class, args);
	}

}
