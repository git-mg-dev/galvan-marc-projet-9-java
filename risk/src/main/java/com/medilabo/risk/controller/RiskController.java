package com.medilabo.risk.controller;

import com.medilabo.risk.service.RiskAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiskController {
    @Autowired
    private RiskAssessmentService riskAssessmentService;

    @GetMapping("/risk/{patientId}")
    public ResponseEntity<String> assessRisk(@RequestHeader("medilabo-token") String token, @PathVariable Integer patientId) {
        return new ResponseEntity<String>(riskAssessmentService.assessRisk(token, patientId).toString(), HttpStatus.OK);
    }
}
