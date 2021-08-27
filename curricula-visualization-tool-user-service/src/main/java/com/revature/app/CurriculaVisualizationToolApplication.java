package com.revature.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan("com.revature.app")
@EnableJpaRepositories("com.revature.app.dao")
@SpringBootApplication
@EntityScan("com.revature.app.model")
public class CurriculaVisualizationToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurriculaVisualizationToolApplication.class, args);
	}

}
