package com.medilabo.notes.service;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;
    public List<Note> getNotesByPatientId(Integer patientId) {
        return noteRepository.findByPatientId(patientId);
    }

    public Note SaveNote(Note note) {
        return noteRepository.save(note);
    }

    public List<String> getTriggerForPatient(Integer patientId) {
        HashSet<String> result = new HashSet<>();
        List<String> triggers = List.of("Anormal", "Anticorps", "Cholestérol", "Fume", "Hémoglobine A1C",
                "Microalbumine", "Poids", "Réaction", "Rechute", "Taille", "Vertige");

        if(patientId != null) {
            List<Note> notes = noteRepository.findByPatientId(patientId);
            if(!notes.isEmpty()) {
                triggers.forEach(trigger -> {
                    if(notes.stream().anyMatch(note -> note.getNote().toLowerCase().contains(trigger.toLowerCase()))) {
                        result.add(trigger);
                    }
                });
            }
        }

        return new ArrayList<>(result);
    }
}
