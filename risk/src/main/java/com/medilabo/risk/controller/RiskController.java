package com.medilabo.risk.controller;

import com.medilabo.risk.service.RiskAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiskController {
    @Autowired
    private RiskAssessmentService riskAssessmentService;

    @GetMapping("/risk/{patientId}")
    public String assessRisk(@PathVariable Integer patientId) {
        return riskAssessmentService.assessRisk(patientId).toString();
    }
}
