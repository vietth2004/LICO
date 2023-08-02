package com.example.unittesting;

import com.example.unittesting.Sevice.project.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class UnitTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnitTestingApplication.class, args);
    }

}
