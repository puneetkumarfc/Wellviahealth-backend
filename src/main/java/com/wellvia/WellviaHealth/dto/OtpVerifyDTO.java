package com.wellvia.WellviaHealth.dto;

import jakarta.validation.constraints.NotBlank;

public class OtpVerifyDTO {
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String otp;

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
} 