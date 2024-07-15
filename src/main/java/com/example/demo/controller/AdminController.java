package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/admin/")
@RestController
public class AdminController {

    @GetMapping
    public String get() {
        return "GET :: ADMIN CONTROLLER";
    }

    @PostMapping
    public String post() {
        return "POST :: ADMIN CONTROLLER";
    }

    @PutMapping
    public String put() {
        return "PUT :: ADMIN CONTROLLER";
    }

    @DeleteMapping
    public String delete() {
        return "delete :: ADMIN CONTROLLER";
    }

}
