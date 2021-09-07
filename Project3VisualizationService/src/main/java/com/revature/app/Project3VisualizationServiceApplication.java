package com.revature.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Project3VisualizationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Project3VisualizationServiceApplication.class, args);
	}

}
