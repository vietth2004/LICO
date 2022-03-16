package com.example.apigateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.InetSocketAddress;

@Configuration
public class APIGatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder)
    {
        return routeLocatorBuilder.routes()
                .route("cia-service", rt -> rt.path("/api/cia-service/**")
                        .uri("http://localhost:6001/"))
                .route("file-service", rt -> rt.path("/api/file-service/**")
                        .uri("http://localhost:5001/"))
                .route("git-service", rt -> rt.path("/api/git-service/**")
                        .uri("http://localhost:8004/"))
                .route("java-service", rt -> rt.path("/api/java-service/**")
                        .uri("http://localhost:7002/"))
                .route("parser-service", rt -> rt.path("/api/parser-service/**")
                        .uri("http://localhost:7001/"))
                .route("project-service", rt -> rt.path("/api/project-service/**")
                        .uri("http://localhost:8003/"))
                .route("spring-service", rt -> rt.path("/api/spring-service/**")
                        .uri("http://localhost:7003/"))
                .route("user-service", rt -> rt.path("/api/user-service/**")
                        .uri("http://localhost:8002/"))
                .route("utility-service", rt -> rt.path("/api/utility-service/**")
                        .uri("http://localhost:8001/"))
                .route("version-compare-service", rt -> rt.path("/api/version-compare-service/**")
                        .uri("http://localhost:6002/"))
                .route("jsf-service", rt -> rt.path("/api/jsf-service/**")
                        .uri("http://localhost:7004/"))
                .route("strut-service", rt -> rt.path("/api/strut-service/**")
                        .uri("http://localhost:7005/"))
                .build();

    }

}
