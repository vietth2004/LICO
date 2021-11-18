package com.jcia.xmlservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class XmlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmlServiceApplication.class, args);
    }

}
