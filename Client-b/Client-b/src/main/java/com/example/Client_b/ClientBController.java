package com.example.Client_b;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientBController {

    @Autowired
    private DiscoveryClient discoveryClient;

    private final RestTemplate restTemplate = new RestTemplate();

    
    @GetMapping("/call-a")
    public String callClientA() {
        List<ServiceInstance> instances = discoveryClient.getInstances("client-a");
        if (instances != null && !instances.isEmpty()) {
            String clientAUrl = instances.get(0).getUri().toString() + "/greet";
            return restTemplate.getForObject(clientAUrl, String.class);
        }
        return "Client A not available";
    }
}
