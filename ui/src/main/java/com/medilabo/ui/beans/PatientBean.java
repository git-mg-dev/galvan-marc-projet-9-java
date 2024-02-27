package com.medilabo.ui.beans;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientBean {
    private int id;
    @NotNull
    @NotEmpty(message = "Patient firstname is mandatory")
    private String firstname;
    @NotNull
    @NotEmpty(message = "Patient lastname is mandatory")
    private String lastname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Select patient birthdate")
    @Past(message = "Birthdate must be in the past")
    private Date birthdate;
    @NotNull
    @NotEmpty(message = "Select patient gender")
    private String gender;
    private String address;
    private String phone;
    private String riskLevel;

    public Integer getAge() {
        Integer age = null;
        if(this.birthdate != null) {
            LocalDate birthdate = this.birthdate
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate today = LocalDate.now(ZoneId.systemDefault());
            age = Period.between(birthdate, today).getYears();
        }
        return age;
    }
}
