package com.medilabo.patient.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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
    @Column(name = "birth_date")
    @NotNull(message = "Patient birth date is mandatory")
    private Date birthDate;
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

    public Patient(String firstname, String lastname, Date birthDate, String gender, String address, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
