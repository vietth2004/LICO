package com.example.gitservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitServiceApplication.class, args);
    }

}
