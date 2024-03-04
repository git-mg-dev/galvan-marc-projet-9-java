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
    public ResponseEntity<List<Note>> getNotesByPatientId(@RequestHeader("medilabo-token") String token, @PathVariable int patientId) {
        return new ResponseEntity<List<Note>>(noteService.getNotesByPatientId(patientId), HttpStatus.OK);
    }

    @GetMapping("/notes/trigger/{patientId}")
    public ResponseEntity<List<String>> getTriggersByPatientId(@RequestHeader("medilabo-token") String token, @PathVariable int patientId) {
        return new ResponseEntity<List<String>>(noteService.getTriggerForPatient(patientId), HttpStatus.OK);
    }

    @PostMapping("/notes")
    private ResponseEntity<Note> addNote(@RequestHeader("medilabo-token") String token, @RequestBody Note note) {
        try {
            Note newNote = noteService.SaveNote(note);
            return new ResponseEntity<Note>(newNote, HttpStatus.CREATED);

        } catch (Exception exception) {
            return new ResponseEntity<Note>((Note) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
