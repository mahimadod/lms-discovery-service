package com.example.discovery_service;

import jakarta.annotation.PostConstruct;
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
			SpringApplication.run(DiscoveryServiceApplication.class, args);
	}
	@PostConstruct
	public void initMDC() {
		MDC.put("service", "discovery-service");
	}
}
