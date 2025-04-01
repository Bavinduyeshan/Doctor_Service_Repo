package com.Doctor_service.Doctor_service.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Doctors")
public class Doctor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctor_Id;

    private String firstname;

    private String lastname ;


    private String phonenumber ;

    private String email;

    private String specilization ;

    @Column(name = "user_id", nullable = false) // Store userId instead of full User object
    private Integer userId;





    private String experience;

    private String education;

    public Integer getDoctor_Id() {
        return doctor_Id;
    }

    public void setDoctor_Id(Integer doctor_Id) {
        this.doctor_Id = doctor_Id;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmial() {
        return email;
    }

    public void setEmial(String emial) {
        this.email = emial;
    }

    public String getSpecilization() {
        return specilization;
    }

    public void setSpecilization(String specilization) {
        this.specilization = specilization;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
