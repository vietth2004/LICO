package com.example.githubservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GithubServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubServiceApplication.class, args);
    }

}
