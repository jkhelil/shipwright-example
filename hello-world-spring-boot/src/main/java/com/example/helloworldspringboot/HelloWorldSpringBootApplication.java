package com.example.helloworldspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class HelloWorldSpringBootApplication {

	@RequestMapping("/")
    String home() {
        return "Hello World - v3!";
    }
	public static void main(String[] args) {
		SpringApplication.run(HelloWorldSpringBootApplication.class, args);
	}

}
