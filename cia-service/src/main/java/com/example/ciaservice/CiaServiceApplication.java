package com.example.ciaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CiaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CiaServiceApplication.class, args);
    }

}
