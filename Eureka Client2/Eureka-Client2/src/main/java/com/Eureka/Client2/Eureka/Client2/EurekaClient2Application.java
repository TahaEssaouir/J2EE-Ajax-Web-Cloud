package com.Eureka.Client2.Eureka.Client2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients 
public class EurekaClient2Application {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClient2Application.class, args);
	}
	
	@RestController // Indique que cette classe est un contrôleur Spring MVC avec des capacités RESTful
	class AggregatorController { // Contrôleur pour gérer les requêtes entrantes et agréger les données

	    @Autowired // Injecte le bean GreetingClient
	    private GreetingClient greetingClient; // Client Feign pour appeler le Service A

	    @GetMapping("/aggregate") // Mappe les requêtes HTTP GET vers cette méthode à l'endpoint "/aggregate"
	    public String getAggregateData() { // Méthode pour agréger les données des deux services
	        String greeting = greetingClient.getGreeting(); // Appelle le Service A pour obtenir le message de salutation
	        return greeting ; // Retourne la réponse agrégée
	    }
	}

	@FeignClient(name = "Eureka-Client") // Déclare un client Feign pour le Service A avec le nom "service-a"
	interface GreetingClient {
	    @GetMapping("/greeting") // Mappe la requête HTTP GET vers l'endpoint "/greeting" du Service A
	    String getGreeting(); // Méthode pour obtenir le message de salutation
	}

}
