package com.medilabo.ui.controller;

import com.medilabo.ui.beans.PatientBean;
import com.medilabo.ui.proxies.PatientProxy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UiController {
    @Autowired
    private PatientProxy patientProxy;

    @GetMapping("/")
    public String getPatients(Model model) {
        List<PatientBean> patients = patientProxy.getAllPatient();
        model.addAttribute("patients", patients);

        return "home";
    }

    @GetMapping("/patient_form")
    public String getPatientInfo(@RequestParam(required = false) Integer id, Model model) {
        PatientBean patient = new PatientBean();

        if(id != null) {
            patient = patientProxy.getPatientById(id);
        }

        model.addAttribute("patientBean", patient);

        //TODO: handle null patient -> redirect to home with error ?

        return "patient_form";
    }

    @PostMapping("/patient_form")
    public String updatePatient(@Valid PatientBean patient, BindingResult bindingResult, Model model) {
        if(!bindingResult.hasErrors()) {
            if(patient.getId() > 0) {
                patientProxy.updatePatient(patient);
            } else {
                try {
                    ResponseEntity<PatientBean> savedPatient = patientProxy.addPatient(patient);
                } catch (Exception e) {
                    return "redirect:/?error";
                }
            }
            return "redirect:/?success";
        }

        model.addAttribute("patientBean", patient);
        return "/patient_form";
    }
}
