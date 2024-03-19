package com.medilabo.notes;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.service.NoteService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.bson.assertions.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "/test.properties")
class NoteApplicationTests {
	@Autowired
	private NoteService noteService;

	@Test
	void contextLoads() {
	}

	@Test
	public void getNotesByPatientIdTest_OK() {
		List<Note> notes = noteService.getNotesByPatientId(1);

		assertNotNull(notes);
		assertEquals(1, notes.size());
	}

	@Test
	public void saveNoteTest_OK() {
		Note newNote = new Note(5, "TestPatient", "Test note: lorem ipsum...");

		Note savedNote = noteService.SaveNote(newNote);

		assertNotNull(savedNote);
		assertNotNull(savedNote.getId());
		Assertions.assertEquals(newNote.getPatient(), savedNote.getPatient());
	}

	@Test
	public void getTriggerForPatientTest_OK() {
		List<String> triggers = noteService.getTriggerForPatient(2);

		assertNotNull(triggers);
		assertEquals(2, triggers.size());
	}
}
