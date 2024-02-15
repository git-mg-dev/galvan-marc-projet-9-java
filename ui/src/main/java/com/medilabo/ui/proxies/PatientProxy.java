package com.medilabo.ui.proxies;

import com.medilabo.ui.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "medilabo-patient", url = "localhost:9001")
public interface PatientProxy {
    @GetMapping("/patient")
    List<PatientBean> getAllPatient();

    @GetMapping("/patient/{id}")
    PatientBean getPatientById(@PathVariable int id);

    @PostMapping("/patient")
    ResponseEntity<PatientBean> addPatient(@RequestBody PatientBean patient);

    @PutMapping("/patient")
    void updatePatient(@RequestBody PatientBean patient);
}
