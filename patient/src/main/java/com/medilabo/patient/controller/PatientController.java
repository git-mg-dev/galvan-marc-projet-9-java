package com.medilabo.patient.controller;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/patient")
    private List<Patient> getAllPatient() {

        return patientService.getAllPatient();
    }

    @GetMapping("/patient/{id}")
    private Patient getPatientById(@PathVariable int id) {
        return patientService.getPatientById(id);
    }

    @PostMapping("/patient")
    private ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        try {
            Patient newPatient = patientService.SavePatient(patient);
            return new ResponseEntity<Patient>(newPatient, HttpStatus.CREATED);

        } catch (ConstraintViolationException exception) {
            return new ResponseEntity<Patient>((Patient) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/patient")
    private void updatePatient(@RequestBody Patient patient) {
        patientService.SavePatient(patient);
    }
}
