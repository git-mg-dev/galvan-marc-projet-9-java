package com.medilabo.authservice.service;

import com.medilabo.authservice.configuration.AuthRequest;
import com.medilabo.authservice.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private DoctorService doctorService;

    public String authenticate(AuthRequest authRequest) {
        Doctor doctor = doctorService.getDoctorByUsername(authRequest.getUsername());
        if(doctor != null) {
            if(BCrypt.checkpw(authRequest.getPassword(), doctor.getPassword())) {
                return jwtService.generateToken(doctor);
            }
        }
        return "";
    }

    public Doctor findByUsername(String username) {
        return doctorService.getDoctorByUsername(username);
    }

    public Boolean isValidToken(String token) {
        try {
            return jwtService.validateToken(token);
        } catch (NullPointerException | JwtValidationException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return jwtService.getUsername(token);
    }
}
