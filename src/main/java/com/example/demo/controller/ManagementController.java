package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management/")
public class ManagementController {
    @GetMapping
    public String get() {
        return "GET :: MANAGEMENT CONTROLLER";
    }
    @PostMapping
    public String post() {
        return "POST :: MANAGEMENT CONTROLLER";
    }

    @PutMapping
    public String put() {
        return "PUT :: MANAGEMENT CONTROLLER";
    }

    @DeleteMapping
    public String delete() {
        return "delete :: MANAGEMENT CONTROLLER";
    }
}
