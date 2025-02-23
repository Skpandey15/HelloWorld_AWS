package com.example.helloworld_aws;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping
    public String helloWorld() {
        return "Hello, World! Deployed on AWS 🚀";
    }
}