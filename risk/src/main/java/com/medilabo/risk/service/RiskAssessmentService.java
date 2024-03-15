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

        // 3 levels map: Gender > Age > Score --> risk level
        Map<String, TreeMap<Integer, TreeMap<Integer, RiskLevelEnum>>> mapping = new TreeMap<>();
        mapping.put("F", new TreeMap<>());
        mapping.put("M", new TreeMap<>());

        TreeMap<Integer, RiskLevelEnum> fUnder30 = new TreeMap<>();
        fUnder30.put(0, RiskLevelEnum.None);
        fUnder30.put(4, RiskLevelEnum.InDanger);
        fUnder30.put(7, RiskLevelEnum.EarlyOnset);

        TreeMap<Integer, RiskLevelEnum> mUnder30 = new TreeMap<>();
        mUnder30.put(0, RiskLevelEnum.None);
        mUnder30.put(3, RiskLevelEnum.InDanger);
        mUnder30.put(5, RiskLevelEnum.EarlyOnset);

        TreeMap<Integer, RiskLevelEnum> over30 = new TreeMap<>();
        over30.put(0, RiskLevelEnum.None);
        over30.put(2, RiskLevelEnum.BorderLine);
        over30.put(6, RiskLevelEnum.InDanger);
        over30.put(8, RiskLevelEnum.EarlyOnset);

        mapping.get("F").put(0, fUnder30);
        mapping.get("F").put(30, over30);
        mapping.get("M").put(0, mUnder30);
        mapping.get("M").put(30, over30);

        if(!mapping.containsKey(gender)) {
            return RiskLevelEnum.Error;
        } else {
            return mapping.get(gender).floorEntry(age).getValue().floorEntry(score).getValue();
        }
    }
}
