package com.auth1.auth.learning.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @PostMapping("/hi")
    public String sayHello(){
        return "Hello ";
    }

    @GetMapping("/users/tapas")
    public Void sayaboutMyself(){
        System.out.println("Hello my name is tapas and I am from Bhubaneswar");
        return null;
    }
}
