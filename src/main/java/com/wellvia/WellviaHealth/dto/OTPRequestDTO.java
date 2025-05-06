package com.wellvia.WellviaHealth.dto;

import jakarta.validation.constraints.NotBlank;

public class OTPRequestDTO {
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
} 