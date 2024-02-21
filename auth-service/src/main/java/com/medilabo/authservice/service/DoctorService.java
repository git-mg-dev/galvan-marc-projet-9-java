package com.medilabo.authservice.service;

import com.medilabo.authservice.model.Doctor;
import com.medilabo.authservice.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor getDoctorByUsername(String username) {
        return doctorRepository.findByUsername(username).orElse(null);
    }
}
