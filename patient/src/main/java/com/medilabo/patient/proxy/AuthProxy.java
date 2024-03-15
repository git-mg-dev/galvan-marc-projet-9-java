package com.medilabo.patient.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "medilabo-authentication-service")
public interface AuthProxy {
    @GetMapping("/authenticate/validateToken")
    ResponseEntity<String> validateToken(@RequestParam String token);
}
