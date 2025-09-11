package com.example.discovery_service;

import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {
	public static void main(String[] args) {
		org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DiscoveryServiceApplication.class);
		MDC.put("service", "discovery-service");
		SpringApplication.run(DiscoveryServiceApplication.class, args);
		logger.info("Application started : Discovery service" );
	}

}
