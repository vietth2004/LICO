package com.example.jsfservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class JsfServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsfServiceApplication.class, args);
    }

}
