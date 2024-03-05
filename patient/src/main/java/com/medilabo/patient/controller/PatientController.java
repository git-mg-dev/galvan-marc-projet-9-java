package com.medilabo.patient.controller;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.proxy.AuthProxy;
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
    @Autowired
    private AuthProxy authProxy;

    @GetMapping("/patient")
    private ResponseEntity<List<Patient>> getAllPatient(@RequestHeader("medilabo-token") String token) {
        HttpStatus checkToken = validateToken(token);

        if (checkToken.is2xxSuccessful()) {
            return new ResponseEntity<>(patientService.getAllPatient(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(checkToken);
        }
    }

    @GetMapping("/patient/{id}")
    private ResponseEntity<Patient> getPatientById(@RequestHeader("medilabo-token") String token, @PathVariable int id) {
        HttpStatus checkToken = validateToken(token);

        if (checkToken.is2xxSuccessful()) {
            return new ResponseEntity<>(patientService.getPatientById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(checkToken);
        }
    }

    @PostMapping("/patient")
    private ResponseEntity<Patient> addPatient(@RequestHeader("medilabo-token") String token, @RequestBody Patient patient) {
        HttpStatus checkToken = validateToken(token);

        if (checkToken.is2xxSuccessful()) {
            try {
                Patient newPatient = patientService.SavePatient(patient);
                return new ResponseEntity<Patient>(newPatient, HttpStatus.CREATED);
            } catch (ConstraintViolationException exception) {
                return new ResponseEntity<Patient>((Patient) null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(checkToken);
        }
    }

    @PutMapping("/patient")
    private void updatePatient(@RequestHeader("medilabo-token") String token, @RequestBody Patient patient) {
        if(validateToken(token).is2xxSuccessful()) {
            patientService.SavePatient(patient);
        }
    }

    private HttpStatus validateToken(String token) {
        try {
            ResponseEntity<String> authUserResponse = authProxy.validateToken(token);
            if(authUserResponse.hasBody() && !authUserResponse.getBody().isEmpty()) {
                return HttpStatus.OK;
            } else {
                return HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            return HttpStatus.NOT_FOUND;
        }
    }
}
