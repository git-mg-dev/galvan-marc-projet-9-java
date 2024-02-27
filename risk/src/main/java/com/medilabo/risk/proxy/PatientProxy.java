package com.medilabo.risk.proxy;

import com.medilabo.risk.model.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "medilabo-patient", url = "localhost:9001")
public interface PatientProxy {
    @GetMapping("/patient/{id}")
    PatientBean getPatientById(@PathVariable int id);
}
