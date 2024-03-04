package com.medilabo.ui.proxies;

import com.medilabo.ui.beans.NoteBean;
import com.medilabo.ui.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "medilabo-gateway-service", url = "${proxy.gateway.url}")
public interface GatewayProxy {
    @GetMapping("/patient")
    ResponseEntity<List<PatientBean>> getAllPatient(@RequestHeader(value = "Authorization", required = true) String token);

    @GetMapping("/patient/{id}")
    ResponseEntity<PatientBean> getPatientById(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable int id);

    @PostMapping("/patient")
    ResponseEntity<PatientBean> addPatient(@RequestHeader(value = "Authorization", required = true) String token, @RequestBody PatientBean patient);

    @PutMapping("/patient")
    void updatePatient(@RequestHeader(value = "Authorization", required = true) String token, @RequestBody PatientBean patient);

    @GetMapping("/notes/{patientId}")
    ResponseEntity<List<NoteBean>> getNotesByPatientId(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable int patientId);

    @PostMapping("/notes")
    ResponseEntity<NoteBean> addNote(@RequestHeader(value = "Authorization", required = true) String token, @RequestBody NoteBean note);

    @GetMapping("/risk/{patientId}")
    ResponseEntity<String> assessRisk(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable Integer patientId);
}
