package com.medilabo.risk.proxy;

import com.medilabo.risk.model.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "medilabo-patient")
public interface PatientProxy {
    @GetMapping("/patient/{id}")
    PatientBean getPatientById(@RequestHeader(value = "medilabo-token", required = true) String token, @PathVariable int id);
}
