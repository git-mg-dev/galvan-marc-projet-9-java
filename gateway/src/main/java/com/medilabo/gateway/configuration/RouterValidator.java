package com.medilabo.gateway.configuration;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> openApiEndPoints = List.of(
            "authenticate", "authenticate/getUser", "authenticate/validateToken"
    );

    public Predicate<ServerHttpRequest> isSecured =
            serverHttpRequest -> openApiEndPoints
                    .stream()
                    .noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));
}
