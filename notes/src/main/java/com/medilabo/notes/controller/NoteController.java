package com.medilabo.notes.controller;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.proxy.AuthProxy;
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
    @Autowired
    private AuthProxy authProxy;

    @GetMapping("/notes/{patientId}")
    public ResponseEntity<List<Note>> getNotesByPatientId(@RequestHeader("medilabo-token") String token, @PathVariable int patientId) {
        HttpStatus checkToken = validateToken(token);

        if (checkToken.is2xxSuccessful()) {
            return new ResponseEntity<List<Note>>(noteService.getNotesByPatientId(patientId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(checkToken);
        }
    }

    @GetMapping("/notes/trigger/{patientId}")
    public ResponseEntity<List<String>> getTriggersByPatientId(@RequestHeader("medilabo-token") String token, @PathVariable int patientId) {
        HttpStatus checkToken = validateToken(token);

        if (checkToken.is2xxSuccessful()) {
            return new ResponseEntity<List<String>>(noteService.getTriggerForPatient(patientId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(checkToken);
        }
    }

    @PostMapping("/notes")
    private ResponseEntity<Note> addNote(@RequestHeader("medilabo-token") String token, @RequestBody Note note) {
        HttpStatus checkToken = validateToken(token);

        if (checkToken.is2xxSuccessful()) {
            try {
                Note newNote = noteService.SaveNote(note);
                return new ResponseEntity<Note>(newNote, HttpStatus.CREATED);

            } catch (Exception exception) {
                return new ResponseEntity<Note>((Note) null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(checkToken);
        }
    }

    private HttpStatus validateToken(String token) {
        try {
            ResponseEntity<String> authUserResponse = authProxy.validateToken(token);
            if(authUserResponse.hasBody() && !authUserResponse.getBody().isEmpty()) {
                return HttpStatus.OK;
            } else {
                return HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            return HttpStatus.NOT_FOUND;
        }
    }
}
