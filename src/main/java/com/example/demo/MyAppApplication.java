package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class MyAppApplication {

	@GetMapping("hello")
	public String hello(){
		return "World!!!";
	}

	public static void main(String[] args) {
		SpringApplication.run(MyAppApplication.class, args);
	}
}
