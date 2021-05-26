package com.revature.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.revature")
@EntityScan("com.revature.app.model")
@EnableJpaRepositories("com.revature.app.dao")
public class CurriculaVisualizationToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurriculaVisualizationToolApplication.class, args);
	}

}
