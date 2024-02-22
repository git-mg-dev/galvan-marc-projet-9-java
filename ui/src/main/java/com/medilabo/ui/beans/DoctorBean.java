package com.medilabo.ui.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorBean {
    private int id;
    private String username;
    private String password;
}
