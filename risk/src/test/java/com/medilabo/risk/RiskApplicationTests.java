package com.medilabo.risk;

import com.medilabo.risk.model.RiskLevelEnum;
import com.medilabo.risk.service.RiskAssessmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RiskApplicationTests {

	@Autowired
	private RiskAssessmentService riskAssessmentService;

	@Test
	void contextLoads() {
	}

	@Test
	public void calculateRiskTest() {
		RiskLevelEnum result = RiskLevelEnum.None;

		// F under 30
		assertEquals(RiskLevelEnum.None, riskAssessmentService.calculateRisk(1, 23, "F"));
		assertEquals(RiskLevelEnum.InDanger, riskAssessmentService.calculateRisk(5, 23, "F"));
		assertEquals(RiskLevelEnum.EarlyOnset, riskAssessmentService.calculateRisk(10, 23, "F"));

		// M under 30
		assertEquals(RiskLevelEnum.None, riskAssessmentService.calculateRisk(2, 23, "M"));
		assertEquals(RiskLevelEnum.InDanger, riskAssessmentService.calculateRisk(4, 23, "M"));
		assertEquals(RiskLevelEnum.EarlyOnset, riskAssessmentService.calculateRisk(8, 23, "M"));

		// F over 30
		assertEquals(RiskLevelEnum.None, riskAssessmentService.calculateRisk(1, 32, "F"));
		assertEquals(RiskLevelEnum.BorderLine, riskAssessmentService.calculateRisk(2, 32, "F"));
		assertEquals(RiskLevelEnum.InDanger, riskAssessmentService.calculateRisk(6, 32, "F"));
		assertEquals(RiskLevelEnum.EarlyOnset, riskAssessmentService.calculateRisk(8, 32, "F"));

		// M over 30
		assertEquals(RiskLevelEnum.None, riskAssessmentService.calculateRisk(1, 32, "M"));
		assertEquals(RiskLevelEnum.BorderLine, riskAssessmentService.calculateRisk(2, 32, "M"));
		assertEquals(RiskLevelEnum.InDanger, riskAssessmentService.calculateRisk(6, 32, "M"));
		assertEquals(RiskLevelEnum.EarlyOnset, riskAssessmentService.calculateRisk(8, 32, "M"));

		// Error
		assertEquals(RiskLevelEnum.Error, riskAssessmentService.calculateRisk(1, 23, "X"));
	}
}
