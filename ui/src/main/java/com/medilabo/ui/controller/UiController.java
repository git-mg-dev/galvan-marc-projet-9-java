package com.medilabo.ui.controller;

import com.medilabo.ui.beans.PatientBean;
import com.medilabo.ui.proxies.PatientProxy;
import com.medilabo.ui.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/home")
    public String getPatients(HttpServletRequest httpServletRequest, Model model) {

        String token = JwtUtil.findToken(httpServletRequest);
        List<PatientBean> patients = patientProxy.getAllPatient(token);
        model.addAttribute("patients", patients);

        return "home";
    }

    @GetMapping("/patient_form")
    public String getPatientInfo(@RequestParam(required = false) Integer id, HttpServletRequest httpServletRequest, Model model) {
        PatientBean patient = new PatientBean();
        String token = JwtUtil.findToken(httpServletRequest);

        if(id != null) {
            patient = patientProxy.getPatientById(token, id);
        }

        model.addAttribute("patientBean", patient);

        //TODO: handle null patient -> redirect to home with error ?

        return "patient_form";
    }

    @PostMapping("/patient_form")
    public String updatePatient(@Valid PatientBean patient, BindingResult bindingResult, HttpServletRequest httpServletRequest, Model model) {
        String token = JwtUtil.findToken(httpServletRequest);

        if(!bindingResult.hasErrors()) {
            if(patient.getId() > 0) {
                patientProxy.updatePatient(token, patient);
            } else {
                try {
                    ResponseEntity<PatientBean> savedPatient = patientProxy.addPatient(token, patient);
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
