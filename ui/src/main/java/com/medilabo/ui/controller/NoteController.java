package com.medilabo.ui.controller;

import com.medilabo.ui.beans.NoteBean;
import com.medilabo.ui.beans.PatientBean;
import com.medilabo.ui.proxies.GatewayProxy;
import com.medilabo.ui.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NoteController {
    @Autowired
    private GatewayProxy gatewayProxy;

    @GetMapping("/notes")
    public String getNotesByPatientId(@RequestParam(required = false) Integer id, HttpServletRequest httpServletRequest, Model model) {
        String token = JwtUtil.findToken(httpServletRequest);

        if(id != null) {
            PatientBean patient = calculateRisk(token, id);

            if(patient != null) {
                List<NoteBean> notes = gatewayProxy.getNotesByPatientId(token, id).getBody();
                NoteBean noteBean = new NoteBean();
                noteBean.setPatientId(patient.getId());
                noteBean.setPatient(patient.getLastname());

                addAttributesToModel(model, notes, noteBean, patient);

                if(patient.getRiskLevel() != null) {
                    model.addAttribute("riskLevel", patient.getRiskLevel());
                } else {
                    model.addAttribute("riskLevel", "");
                }

                return "notes";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/notes")
    private String addNote(@Valid NoteBean noteBean, BindingResult bindingResult, HttpServletRequest httpServletRequest, Model model) {
        String token = JwtUtil.findToken(httpServletRequest);
        PatientBean patient = calculateRisk(token, noteBean.getPatientId());
        List<NoteBean> notes = gatewayProxy.getNotesByPatientId(token, noteBean.getPatientId()).getBody();

        if(!bindingResult.hasErrors()) {
            try {
                    ResponseEntity<NoteBean> savedNote = gatewayProxy.addNote(token, noteBean);
                    if(savedNote.getStatusCode().is2xxSuccessful()) {
                        return "redirect:/notes?id=" + noteBean.getPatientId() + "&success";
                    }
                } catch (Exception e) {

                addAttributesToModel(model, notes, noteBean, patient);
                return "redirect:/notes?id=" + noteBean.getPatientId() + "&error";
            }
        }

        addAttributesToModel(model, notes, noteBean, patient);
        return "notes";
    }

    private PatientBean calculateRisk(String token, Integer id) {
        if(id != null) {
            PatientBean result = gatewayProxy.getPatientById(token, id).getBody();
            if (result != null) {
                result.setRiskLevel(gatewayProxy.assessRisk(token, result.getId()).getBody());
            }
            return result;
        } else {
            return null;
        }
    }

    private void addAttributesToModel(Model model, List<NoteBean> notes, NoteBean newNote, PatientBean patient) {
        model.addAttribute("notes", notes); // list of notes
        model.addAttribute("noteBean", newNote); // new noteBean for form

        model.addAttribute("firstname", patient.getFirstname());
        model.addAttribute("lastname", patient.getLastname());
        model.addAttribute("birthdate", patient.getBirthdate());
        model.addAttribute("age", patient.getAge());
        model.addAttribute("gender", patient.getGender());
    }
}
