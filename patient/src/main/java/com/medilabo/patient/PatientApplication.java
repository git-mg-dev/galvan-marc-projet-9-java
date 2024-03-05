package com.medilabo.patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.medilabo.patient")
public class PatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}

}
