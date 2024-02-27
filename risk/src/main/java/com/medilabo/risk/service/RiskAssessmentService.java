package com.medilabo.risk.service;

import com.medilabo.risk.model.PatientBean;
import com.medilabo.risk.model.RiskLevelEnum;
import com.medilabo.risk.proxy.NotesProxy;
import com.medilabo.risk.proxy.PatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

@Service
public class RiskAssessmentService {
    @Autowired
    private PatientProxy patientProxy;
    @Autowired
    private NotesProxy notesProxy;

    public RiskLevelEnum assessRisk(Integer patientId) {
        // Get data
        if(patientId != null) {
            PatientBean patientBean = patientProxy.getPatientById(patientId);
            List<String> triggers = notesProxy.getTriggersByPatientId(patientId);

            if(patientBean != null && patientBean.getBirthdate() != null) {

                // Calculate age of patient
                LocalDate birthdate = patientBean.getBirthdate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                LocalDate today = LocalDate.now(ZoneId.systemDefault());
                int age = Period.between(birthdate, today).getYears();

                // Assess risk
                return calculateRisk(triggers.size(), age, patientBean.getGender());
            } else {
                return RiskLevelEnum.ErrorNoData;
            }
        } else {
            return RiskLevelEnum.ErrorNoData;
        }
    }

    private RiskLevelEnum calculateRisk(int score, int age, String gender) {
        RiskLevelEnum riskLevel = RiskLevelEnum.None;

        if(age < 30) {
            if(gender.equals("F")) {
                if(score < 4) {
                    riskLevel = RiskLevelEnum.None;
                } else if (score < 7) {
                    riskLevel = RiskLevelEnum.InDanger;
                } else { // 8 and over
                    riskLevel = RiskLevelEnum.EarlyOnset;
                }
            } else if (gender.equals("M")) {
                if(score < 3) {
                    riskLevel = RiskLevelEnum.None;
                } else if (score < 5) {
                    riskLevel = RiskLevelEnum.InDanger;
                } else { // 8 and over
                    riskLevel = RiskLevelEnum.EarlyOnset;
                }
            } else {
                riskLevel = RiskLevelEnum.ErrorGenderUnknown;
            }
        } else {
            // the risk is the same for both genders
            if(score < 2) {
                riskLevel = RiskLevelEnum.None;
            } else if (score < 6) {
                riskLevel = RiskLevelEnum.BorderLine;
            } else if (score < 8) {
                riskLevel = RiskLevelEnum.InDanger;
            } else { // 8 and over
                riskLevel = RiskLevelEnum.EarlyOnset;
            }
        }

        return riskLevel;
    }
}
