package com.medilabo.ui.controller;

import com.medilabo.ui.beans.PatientBean;
import com.medilabo.ui.proxies.PatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UiController {
    @Autowired
    private PatientProxy patientProxy;

    @GetMapping("/")
    private String getPatients(Model model) {
        List<PatientBean> patients = patientProxy.getAllPatient();
        model.addAttribute("patients", patients);

        return "Home";
    }
}
