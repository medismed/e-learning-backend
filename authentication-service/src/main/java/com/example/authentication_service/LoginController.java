package com.example.authentication_service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.DiscoveryClient;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    UserRepo userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {

        User user = userRepository.findByEmail(email);
        if (user == null )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        if (!user.getPassword().equals(password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/hi")
    public String sayhi(){
        return "hi";
    }

 
}
