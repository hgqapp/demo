package com.example.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MyAppApplication.class);
	}

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        int count = 10;
        String groupName = Base64Utils.encodeToString("动态节点".getBytes());
        System.out.println(groupName);
        String path = "/ss.json?_=" + System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
//        headers.add(":authority","free-ss.site");
//        headers.add(":method","GET");
//        headers.add(":path",path);
//        headers.add(":scheme","https");
//        headers.add("accept","application/json, text/javascript, */*; q=0.01");
//        headers.add("accept-language","zh-CN,zh;q=0.9,en;q=0.8,ja;q=0.7,mg;q=0.6");
//        headers.add("cache-control","no-cache");
//        headers.add("pragma","no-cache");
        headers.add("referer","https://free-ss.site/");
        headers.add("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
//        headers.add("x-requested-with","XMLHttpRequest");
        //headers.add("cookie","__cfduid=d4adfb1e6f98d8c167a02f557a15fbe121514960819; cf_clearance=3d8c20feac3cac494903eae1c46f22b54067508a-1514963169-1800");
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> check = restTemplate.exchange("https://free-ss.site/cdn-cgi/l/chk_jschl?jschl_vc=fda5942a42b15f244a20883f7580b177&pass=1514967161.442-D9uw4wOk2c&jschl_answer=147", HttpMethod.GET, entity, String.class);
        List<String> cookie = check.getHeaders().get("set-cookie");
        System.out.println(cookie);

        ResponseEntity<Map>  exchange = restTemplate.exchange("https://free-ss.site" + path, HttpMethod.GET, (HttpEntity<?>) entity, Map.class);



        Map<String,List<List>> body = exchange.getBody();
        List<List> data = body.get("data");
        if (data != null) {
            StringBuilder ssr = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                if (i >= count) {
                    break;
                }
                List item = data.get(i);
                if (((int)item.get(0)) == 100) {
                    String password = Base64Utils.encodeToString(item.get(3).toString().getBytes()).replace("=", "");
                    String remarks = Base64Utils.encodeToString((item.get(6).toString() + "_" + item.get(5).toString()).getBytes()).replace("=", "");
                    StringBuilder sb = new StringBuilder();
                    sb.append(item.get(1)).append(":").append(item.get(2)).append(":origin:").append(item.get(4)).append(":plain:")
                            .append(password)
                            .append("/?obfsparam=&remarks=").append(remarks)
                            .append("&group=").append(groupName);
                    ssr.append("ssr://").append(Base64Utils.encodeToString(sb.toString().getBytes()).replace("=", "").replace("/","_"));
                    ssr.append(System.getProperty("line.separator"));
                }
            }
            System.out.println(ssr);
        }
        System.out.println(body);
    }
    public static List<List> getSsJson(String cookie){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(":authority","free-ss.site");
        headers.add(":method","GET");
        //headers.add(":path",path);
        headers.add(":scheme","https");
        headers.add("accept","application/json, text/javascript, */*; q=0.01");
        headers.add("accept-language","zh-CN,zh;q=0.9,en;q=0.8,ja;q=0.7,mg;q=0.6");
        headers.add("cache-control","no-cache");
        headers.add("pragma","no-cache");
        headers.add("referer","https://free-ss.site/");
        headers.add("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
        headers.add("x-requested-with","XMLHttpRequest");
        if (cookie != null) {
            headers.add("cookie",cookie);
        }
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Map> exchange = restTemplate.exchange("https://free-ss.site/ss.json", HttpMethod.GET, (HttpEntity<?>) entity, Map.class);
        Map<String,List<List>> body = exchange.getBody();
        return body.get("data");
	}
}
