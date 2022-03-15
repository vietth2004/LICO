package com.example.jspservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class JspServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JspServiceApplication.class, args);
    }

}
