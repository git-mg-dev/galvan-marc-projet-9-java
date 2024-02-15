package com.medilabo.patient.service;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(int patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }

    public Patient SavePatient(Patient patient) {
        return patientRepository.save(patient);
    }
}
