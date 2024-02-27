package com.medilabo.gateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("medilabo-patient", r -> r.path("/patient/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:9001/"))

                .route("medilabo-notes", r -> r.path("/notes/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:9002/"))

                .route("medilabo-risk", r -> r.path("/risk/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:9003/"))

                .build();
    }
}
