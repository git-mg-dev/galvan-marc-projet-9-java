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
        PatientBean patient = new PatientBean();
        List<NoteBean> notes = new ArrayList<>();
        NoteBean noteBean = new NoteBean();
        String token = JwtUtil.findToken(httpServletRequest);

        if(id != null) {
            patient = gatewayProxy.getPatientById(token, id);
            if(patient != null) {
                notes = gatewayProxy.getNotesByPatientId(token, id);
                noteBean.setPatientId(patient.getId());
                noteBean.setPatient(patient.getLastname());

                model.addAttribute("notes", notes); // list of notes
                model.addAttribute("noteBean", noteBean); // new noteBean for form
                return "notes";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/notes")
    private String addNote(@Valid NoteBean noteBean, BindingResult bindingResult, HttpServletRequest httpServletRequest, Model model) {
        String token = JwtUtil.findToken(httpServletRequest);
        List<NoteBean> notes = gatewayProxy.getNotesByPatientId(token, noteBean.getPatientId());

        if(!bindingResult.hasErrors()) {
            try {
                    ResponseEntity<NoteBean> savedNote = gatewayProxy.addNote(token, noteBean);
                    if(savedNote.getStatusCode().is2xxSuccessful()) {
                        return "redirect:/notes?id=" + noteBean.getPatientId() + "&success";
                    }
                } catch (Exception e) {

                model.addAttribute("notes", notes); // list of notes
                model.addAttribute("noteBean", noteBean); // new note for form
                return "redirect:/notes?id=" + noteBean.getPatientId() + "&error";
            }
        }

        model.addAttribute("notes", notes); // list of notes
        model.addAttribute("noteBean", noteBean); // new note for form
        return "notes";
    }
}
