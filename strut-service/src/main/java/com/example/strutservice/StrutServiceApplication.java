package com.example.strutservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StrutServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrutServiceApplication.class, args);
    }

}
