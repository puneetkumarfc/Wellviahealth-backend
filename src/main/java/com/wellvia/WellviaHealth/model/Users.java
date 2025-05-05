package com.wellvia.WellviaHealth.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(length = 6)
    private String otp;

    @Column(name = "otp_verified")
    private Boolean otpVerified = false;

    @Column(name = "otp_created_at")
    private LocalDateTime otpCreatedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private Integer provider; // 1 -> Facebook, 2 -> Google

    @Column(name = "provider_user_id")
    private String providerUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Boolean getOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(Boolean otpVerified) {
        this.otpVerified = otpVerified;
    }

    public LocalDateTime getOtpCreatedAt() {
        return otpCreatedAt;
    }

    public void setOtpCreatedAt(LocalDateTime otpCreatedAt) {
        this.otpCreatedAt = otpCreatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getProvider() {
        return provider;
    }

    public void setProvider(Integer provider) {
        this.provider = provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
