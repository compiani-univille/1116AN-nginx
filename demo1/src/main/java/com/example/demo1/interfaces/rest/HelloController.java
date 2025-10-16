package com.example.demo1.interfaces.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/name")
    public String name() {
        return "Demo 1";
    }

    @GetMapping("/waiter")
    public String waiter() {
        return "Demo 1 - waiter";
    }

    @GetMapping("/customer")
    public String customer() {
        return "Demo 1 - customer";
    }
}