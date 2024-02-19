package com.medilabo.ui.controller;

import com.medilabo.ui.beans.PatientBean;
import com.medilabo.ui.proxies.PatientProxy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        PatientBean patient = patientProxy.getPatientById(id);
        model.addAttribute("patient", patient);

        //TODO: handle null patient -> redirect to home with error

        return "patient_form";
    }

    @PostMapping("/patient_form")
    public String updatePatient(@Valid PatientBean patient, BindingResult bindingResult, Model model) {
        if(!bindingResult.hasErrors()) {
            if(patient.getId() > 0) {
                //TODO : handle date saved at day-1
                patientProxy.updatePatient(patient);
            }
            return "redirect:/?success";
        }

        model.addAttribute("patient", patient);
        return "/patient_form";
    }
}
