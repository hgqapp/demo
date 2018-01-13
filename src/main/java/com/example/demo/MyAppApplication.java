package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

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

	@ResponseBody
    @GetMapping("get")
    public String get(String fileName) throws IOException {
        String path = "/ss.json?_=" + System.currentTimeMillis();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(":authority","free-ss.site");
        headers.add(":method","GET");
        headers.add(":path",path);
        headers.add(":scheme","https");
        headers.add("accept","application/json, text/javascript, */*; q=0.01");
        //		headers.add("accept-encoding","gzip, deflate, br");
        headers.add("accept-language","zh-CN,zh;q=0.9,en;q=0.8,ja;q=0.7,mg;q=0.6");
        headers.add("cache-control","no-cache");
        headers.add("pragma","no-cache");
        headers.add("referer","https://free-ss.site/");
        headers.add("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
        headers.add("x-requested-with","XMLHttpRequest");
        headers.add("cookie","__cfduid=d0e6007b9a6363a659f65b3cfc8cba0d71514959674; cf_clearance=36b48517c59f1ff8d0db0b3af39e667ba3f16f2e-1514959678-1800");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> exchange = restTemplate.exchange("https://free-ss.site" + path, HttpMethod.GET, (HttpEntity<?>) entity, String.class);
        return exchange.getBody();
    }

	private String getUrl(){
	    return "https://free-ss.site/ss.json?_=" + System.currentTimeMillis();
    }


	public static void main(String[] args) {
		SpringApplication.run(MyAppApplication.class, args);
	}
}
