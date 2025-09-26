package com.example.demo2.interfaces.rest;

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
        return "Demo 2";
    }
}