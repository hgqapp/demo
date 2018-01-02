package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MyAppApplication {

	@GetMapping("hello")
	public String hello(){
		return "World!!!";
	}

    @GetMapping("download")
    public ResponseEntity<byte[]> download(String fileName) throws IOException {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "abc.txt");
	    return new ResponseEntity<byte[]>("Hello".getBytes(), headers, HttpStatus.CREATED);
	}


	public static void main(String[] args) {
		SpringApplication.run(MyAppApplication.class, args);
	}
}
