package com.medilabo.gateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Autowired
    private AuthenticationFilter filter;

    @Value("${gateway.patient.serviceUrl}")
    private String patientServiceUrl;
    @Value("${gateway.notes.serviceUrl}")
    private String notesServiceUrl;
    @Value("${gateway.risk.serviceUrl}")
    private String riskServiceUrl;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("medilabo-patient", r -> r.path("/patient/**")
                        .filters(f -> f.filter(filter))
                        .uri(patientServiceUrl))

                .route("medilabo-notes", r -> r.path("/notes/**")
                        .filters(f -> f.filter(filter))
                        .uri(notesServiceUrl))

                .route("medilabo-risk", r -> r.path("/risk/**")
                        .filters(f -> f.filter(filter))
                        .uri(riskServiceUrl))

                .build();
    }
}
