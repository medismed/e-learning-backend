package com.example.authentication_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    UserRepo userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found. Please check your email and try again.");
        }

        if (!user.getPassword().equals(loginRequest.password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials. Please check your password and try again.");
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/hi")
    public String sayHi() {
        return "Hello from Authentication Service!";
    }

    @RequestMapping(method = RequestMethod.OPTIONS, path = "/**")
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity.ok().build();
    }
}
