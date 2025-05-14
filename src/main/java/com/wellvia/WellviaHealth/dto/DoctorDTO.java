package com.wellvia.WellviaHealth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

public class DoctorDTO {

    @NotNull
    private Long doctorId; 

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "Date of Birth is required")
    private Date dob;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Mobile number is required")
    private String mobile;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Education details are required")
    private String education;

    private String bio;

    private String specialization;

    private String registrationNumber;

    private String profileImage;

    @NotNull(message = "Experience is required")
    private Integer experience;

    private String certificate;

    private String signature;

    private String practiceAddress;

    private String agreementFile;

    // New fields for appointments
    private List<AppointmentSlotDTO> appointmentSlots;
    private List<AppointmentDTO> appointments;

    // Getters and Setters

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPracticeAddress() {
        return practiceAddress;
    }

    public void setPracticeAddress(String practiceAddress) {
        this.practiceAddress = practiceAddress;
    }

    public String getAgreementFile() {
        return agreementFile;
    }

    public void setAgreementFile(String agreementFile) {
        this.agreementFile = agreementFile;
    }

    public List<AppointmentSlotDTO> getAppointmentSlots() {
        return appointmentSlots;
    }

    public void setAppointmentSlots(List<AppointmentSlotDTO> appointmentSlots) {
        this.appointmentSlots = appointmentSlots;
    }

    public List<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }
}
