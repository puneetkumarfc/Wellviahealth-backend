package com.wellvia.WellviaHealth.dto;

import jakarta.validation.constraints.NotNull;

public class LoginRequestDTO {
    private String phoneNumber;  // Optional for social login
    
    private Integer provider;  // 1 for Facebook, 2 for Google
    private String providerUserId;  // Social login provider's user ID
    
    private String deviceInfo;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
} 