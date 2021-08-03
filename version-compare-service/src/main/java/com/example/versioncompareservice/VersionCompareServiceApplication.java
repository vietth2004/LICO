package com.example.versioncompareservice;

import com.example.versioncompareservice.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableEurekaClient
public class VersionCompareServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VersionCompareServiceApplication.class, args);
    }

}






















