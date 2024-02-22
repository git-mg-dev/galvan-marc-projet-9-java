package com.medilabo.authservice.controller;

import com.medilabo.authservice.configuration.AuthRequest;
import com.medilabo.authservice.model.Doctor;
import com.medilabo.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class DoctorController {
    @Autowired
    private AuthService authService;

    @GetMapping("/getUser")
    public ResponseEntity<Doctor> getDoctorByUsername(@RequestParam String username) {
        Doctor doctor = authService.findByUsername(username);

        if(doctor != null) {
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/validateToken")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        String username = "";
        if(authService.isValidToken(token)) {
            username = authService.getUsername(token);
        }
        return new ResponseEntity<>(username, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody AuthRequest request) {
        String token = authService.authenticate(request);

        if(!token.isEmpty()) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
