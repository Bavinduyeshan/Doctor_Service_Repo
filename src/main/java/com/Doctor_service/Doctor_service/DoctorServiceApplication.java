package com.Doctor_service.Doctor_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.Doctor_service.Doctor_service.service")
public class DoctorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorServiceApplication.class, args);
	}

}
