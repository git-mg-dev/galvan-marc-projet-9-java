package com.medilabo.ui.proxies;

import com.medilabo.ui.beans.DoctorBean;
import com.medilabo.ui.beans.NoteBean;
import com.medilabo.ui.beans.PatientBean;
import com.medilabo.ui.configuration.AuthRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "medilabo-gateway-service", url = "${proxy.gateway.url}")
public interface GatewayProxy {
    // User authentication
    @GetMapping("/authenticate/getUser")
    ResponseEntity<DoctorBean> getDoctorByUsername(@RequestParam String username);
    @GetMapping("/authenticate/validateToken")
    ResponseEntity<String> validateToken(@RequestParam String token);
    @PostMapping("/authenticate")
    ResponseEntity<String> authenticate(@RequestBody AuthRequest request);

    // Patient data
    @GetMapping("/patient")
    ResponseEntity<List<PatientBean>> getAllPatient(@RequestHeader(value = "Authorization", required = true) String token);
    @GetMapping("/patient/{id}")
    ResponseEntity<PatientBean> getPatientById(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable int id);
    @PostMapping("/patient")
    ResponseEntity<PatientBean> addPatient(@RequestHeader(value = "Authorization", required = true) String token, @RequestBody PatientBean patient);
    @PutMapping("/patient")
    void updatePatient(@RequestHeader(value = "Authorization", required = true) String token, @RequestBody PatientBean patient);

    // Patient history (notes)
    @GetMapping("/notes/{patientId}")
    ResponseEntity<List<NoteBean>> getNotesByPatientId(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable int patientId);
    @PostMapping("/notes")
    ResponseEntity<NoteBean> addNote(@RequestHeader(value = "Authorization", required = true) String token, @RequestBody NoteBean note);

    // Patient risk assessment
    @GetMapping("/risk/{patientId}")
    ResponseEntity<String> assessRisk(@RequestHeader(value = "Authorization", required = true) String token, @PathVariable Integer patientId);
}
