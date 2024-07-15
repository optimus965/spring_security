package com.example.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticated")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AutheticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        String hello = "HELLO, WORLD";
        return ResponseEntity.ok(hello);
    }

}
