package com.Eureka.Server.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication // Indique que c'est une application Spring Boot, activant l'auto-configuration et le scan des composants
@EnableEurekaServer
public class EurekaServerApplication {
	//N?NN
	// waaaaaaaaaaaaa
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}
