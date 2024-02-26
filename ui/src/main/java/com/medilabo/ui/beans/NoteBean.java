package com.medilabo.ui.beans;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteBean {
    private String id;
    private Integer patientId;
    private String patient;
    @NotNull
    @NotEmpty(message = "Enter some text before saving")
    private String note;
}
