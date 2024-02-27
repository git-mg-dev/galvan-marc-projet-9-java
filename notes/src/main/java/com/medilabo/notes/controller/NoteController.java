package com.medilabo.notes.controller;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/notes/{patientId}")
    public List<Note> getNotesByPatientId(@PathVariable int patientId) {
        return noteService.getNotesByPatientId(patientId);
    }

    @GetMapping("/notes/trigger/{patientId}")
    public List<String> getTriggersByPatientId(@PathVariable int patientId) {
        return noteService.getTriggerForPatient(patientId);
    }

    @PostMapping("/notes")
    private ResponseEntity<Note> addNote(@RequestBody Note note) {
        try {
            Note newNote = noteService.SaveNote(note);
            return new ResponseEntity<Note>(newNote, HttpStatus.CREATED);

        } catch (Exception exception) {
            return new ResponseEntity<Note>((Note) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
