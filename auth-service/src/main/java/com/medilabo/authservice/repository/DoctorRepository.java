package com.medilabo.authservice.repository;

import com.medilabo.authservice.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findByUsername(String username);
}
