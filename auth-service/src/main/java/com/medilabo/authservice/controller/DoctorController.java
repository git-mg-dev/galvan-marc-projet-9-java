package com.medilabo.authservice.controller;

import com.medilabo.authservice.configuration.AuthRequest;
import com.medilabo.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoctorController {
    @Autowired
    private AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody AuthRequest request) {
        String token = authService.authenticate(request);

        if(!token.isEmpty()) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
