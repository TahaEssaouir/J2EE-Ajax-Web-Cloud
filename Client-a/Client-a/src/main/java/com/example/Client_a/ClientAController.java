package com.example.Client_a;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientAController {
    
    @GetMapping("/greet")
    public String greet() {
        return "Hello from Client A!";
    }
}
