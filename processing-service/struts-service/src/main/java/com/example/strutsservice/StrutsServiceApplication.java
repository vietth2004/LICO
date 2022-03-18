package com.example.strutsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StrutsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrutsServiceApplication.class, args);
    }

}
