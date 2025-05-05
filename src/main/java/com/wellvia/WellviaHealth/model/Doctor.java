package com.wellvia.WellviaHealth.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_doctor_user_id"))
    private Users user;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "dob", nullable = false)
    private Date dob;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "education", nullable = false)
    private String education;

    @Column(name = "bio")
    private String bio;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "experience", nullable = false)
    private Integer experience;

    @Column(name = "certificate")
    private String certificate;

    @Column(name = "signature")
    private String signature;

    @Column(name = "practice_address")
    private String practiceAddress;

    @Column(name = "agreement_file")
    private String agreementFile;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    // Constructors
    public Doctor() {}

    // Getters and Setters

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // Enum for gender
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
