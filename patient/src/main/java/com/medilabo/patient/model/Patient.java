package com.medilabo.patient.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "firstname")
    @NotNull
    @NotEmpty(message = "Patient firstname is mandatory")
    private String firstname;
    @Column(name = "lastname")
    @NotNull
    @NotEmpty(message = "Patient lastname is mandatory")
    private String lastname;
    @Column(name = "birthdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Patient birthdate is mandatory")
    private Date birthdate;
    @Column(name = "gender")
    @NotNull
    @NotEmpty(message = "Patient gender is mandatory")
    private String gender;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;

    public Patient() {
    }

    public Patient(String firstname, String lastname, Date birthdate, String gender, String address, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
