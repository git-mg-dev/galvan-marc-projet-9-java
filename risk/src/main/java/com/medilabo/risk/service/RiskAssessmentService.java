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
import java.util.Map;
import java.util.TreeMap;

@Service
public class RiskAssessmentService {
    @Autowired
    private PatientProxy patientProxy;
    @Autowired
    private NotesProxy notesProxy;

    @Autowired
    private RiskLevelProperties riskLevelProperties;

    public RiskLevelEnum assessRisk(String token, Integer patientId) {
        // Get data
        if(patientId != null) {
            PatientBean patientBean = patientProxy.getPatientById(token, patientId);
            List<String> triggers = notesProxy.getTriggersByPatientId(token, patientId);

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
                return RiskLevelEnum.Error;
            }
        } else {
            return RiskLevelEnum.Error;
        }
    }

    public RiskLevelEnum calculateRisk(int score, int age, String gender) {
        RiskLevelEnum riskLevel = RiskLevelEnum.None;
        String keyAge = "under30";
        if(age >= 30) {
            keyAge = "over30";
        }

        if(riskLevelProperties.getEarlyOnset().containsKey(gender) &&
                riskLevelProperties.getEarlyOnset().get(gender).containsKey(keyAge) &&
                riskLevelProperties.getEarlyOnset().get(gender).get(keyAge) <= score) {
            riskLevel = RiskLevelEnum.EarlyOnset;
        } else if (riskLevelProperties.getInDanger().containsKey(gender) &&
                riskLevelProperties.getInDanger().get(gender).containsKey(keyAge) &&
                riskLevelProperties.getInDanger().get(gender).get(keyAge) <= score) {
            riskLevel = RiskLevelEnum.InDanger;
        } else if (riskLevelProperties.getBorderline().containsKey(gender) &&
                riskLevelProperties.getBorderline().get(gender).containsKey(keyAge) &&
                riskLevelProperties.getBorderline().get(gender).get(keyAge) <= score) {
            riskLevel = RiskLevelEnum.BorderLine;
        }

        return riskLevel;
    }
}
