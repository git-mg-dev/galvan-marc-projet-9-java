package com.medilabo.patient;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "/test.properties")
@Sql(scripts = "/init_db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PatientApplicationTests {
	@Autowired
	private PatientService patientService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getAllPatientTest_OK() {
		List<Patient> patients = new ArrayList<>();

		patients = patientService.getAllPatient();

		assertEquals(4, patients.size());
	}

	@Test
	public void getPatientByIdTest_OK() {
		Patient patient = patientService.getPatientById(1);

		assertNotNull(patient);
		assertEquals("TestNone", patient.getLastname());
	}

	@Test
	public void addPatientTest_OK() {
		Patient patient = new Patient("Patientest", "Test", new Date(), "F", "2 rue du test", "");

		Patient savedPatient = patientService.SavePatient(patient);

		assertNotNull(savedPatient);
		assertEquals(patient.getFirstname(), savedPatient.getFirstname());
	}

	@Test
	public void addPatientTest_Fail() {
		Patient patient = new Patient("PatientSansDate", "Test", null, "F", "2 rue du test", "");

		assertThrows(ConstraintViolationException.class, () -> patientService.SavePatient(patient));
	}

	@Test
	public void updatePatientTest_OK() {
		Patient patient = patientService.getPatientById(1);
		patient.setLastname("UpdateTest");

		Patient savedPatient = patientService.SavePatient(patient);

		assertNotNull(savedPatient);
		assertEquals(patient.getLastname(), savedPatient.getLastname());
	}

}
