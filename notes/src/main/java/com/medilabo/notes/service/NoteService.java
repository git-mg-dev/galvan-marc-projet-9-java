package com.medilabo.notes.service;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
