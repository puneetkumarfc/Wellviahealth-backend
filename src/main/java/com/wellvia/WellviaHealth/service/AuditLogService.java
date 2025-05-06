package com.wellvia.WellviaHealth.service;

import com.wellvia.WellviaHealth.model.AuditLog;
import com.wellvia.WellviaHealth.repository.AuditLogRepository;
import com.wellvia.WellviaHealth.interfaces.AuditLogInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class AuditLogService implements AuditLogInterface {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void logEvent(String event, String username, String details, String status, String errorMessage) {
        AuditLog log = new AuditLog();
        log.setEvent(event);
        log.setUsername(username);
        log.setIpAddress(getClientIpAddress());
        log.setTimestamp(LocalDateTime.now());
        log.setDetails(details);
        log.setStatus(status);
        log.setErrorMessage(errorMessage);
        auditLogRepository.save(log);
    }

    private String getClientIpAddress() {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0];
        }
        return request.getRemoteAddr();
    }
} 