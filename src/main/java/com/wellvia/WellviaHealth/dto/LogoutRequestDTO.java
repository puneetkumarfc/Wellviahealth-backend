package com.wellvia.WellviaHealth.dto;

import jakarta.validation.constraints.NotNull;

public class LogoutRequestDTO {
    @NotNull(message = "User ID is required")
    private Long userId;
    
    private String deviceInfo;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
} 