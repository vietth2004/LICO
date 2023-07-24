package com.example.unittesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UnitTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnitTestingApplication.class, args);
    }

}
