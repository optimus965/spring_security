package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello/test")
public class Controller {

    @GetMapping("/world")
    public ResponseEntity<String> hello() {
        String hello = "hello, world";
        return ResponseEntity.ok(hello);
    }
}
