package com.wellvia.WellviaHealth.interfaces;

public interface AuditLogInterface {
    void logEvent(String event, String username, String details, String status, String errorMessage);
} 