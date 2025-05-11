package com.wellvia.WellviaHealth.interceptor;

import com.google.common.cache.LoadingCache;
import com.wellvia.WellviaHealth.dto.ApiResponse;
import com.wellvia.WellviaHealth.service.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.io.IOException;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final int MAX_REQUESTS_PER_MINUTE = 100;
    private static final int MAX_OTP_REQUESTS_PER_2_MINUTES = 10;
    private static final int MAX_SPECIALIZATION_REQUESTS_PER_MINUTE = 60;

    @Autowired
    private LoadingCache<String, Integer> requestCountsPerIp;

    @Autowired
    private LoadingCache<String, Integer> otpRequestCountsPerIp;

    @Autowired
    private LoadingCache<String, Integer> specializationRequestCountsPerIp;

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = getClientIP(request);
        String requestURI = request.getRequestURI();

        if (requestURI.contains("/api/auth/login/request-otp") || requestURI.contains("/api/auth/register")) {
            return checkOtpRateLimit(clientIp, response);
        } else if (requestURI.contains("/api/specializations")) {
            return checkSpecializationRateLimit(clientIp, response);
        } else {
            return checkGeneralRateLimit(clientIp, response);
        }
    }

    private boolean checkSpecializationRateLimit(String clientIp, HttpServletResponse response) throws ExecutionException, IOException {
        int requests = specializationRequestCountsPerIp.get(clientIp);
        if (requests >= MAX_SPECIALIZATION_REQUESTS_PER_MINUTE) {
            auditLogService.logEvent(
                "RATE_LIMIT_EXCEEDED",
                clientIp,
                "Specialization request limit exceeded",
                "ERROR",
                null
            );
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Specialization request limit exceeded. Please try again after a minute.");
            return false;
        }
        requests++;
        specializationRequestCountsPerIp.put(clientIp, requests);
        return true;
    }

    private boolean checkOtpRateLimit(String clientIp, HttpServletResponse response) throws ExecutionException, IOException {
        int requests = otpRequestCountsPerIp.get(clientIp);
        if (requests >= MAX_OTP_REQUESTS_PER_2_MINUTES) {
            auditLogService.logEvent(
                "RATE_LIMIT_EXCEEDED",
                clientIp,
                "OTP request limit exceeded",
                "ERROR",
                null
            );
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("OTP request limit exceeded. Please try again after 2 minutes.");
            return false;
        }
        requests++;
        otpRequestCountsPerIp.put(clientIp, requests);
        return true;
    }

    private boolean checkGeneralRateLimit(String clientIp, HttpServletResponse response) throws ExecutionException, IOException {
        int requests = requestCountsPerIp.get(clientIp);
        if (requests >= MAX_REQUESTS_PER_MINUTE) {
            auditLogService.logEvent(
                "RATE_LIMIT_EXCEEDED",
                clientIp,
                "Request limit exceeded",
                "ERROR",
                null
            );
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Request limit exceeded. Please try again after a minute.");
            return false;
        }
        requests++;
        requestCountsPerIp.put(clientIp, requests);
        return true;
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
} 