package com.wellvia.WellviaHealth.controller;

import com.wellvia.WellviaHealth.dto.LabListingRequestDTO;
import com.wellvia.WellviaHealth.dto.LabListingResponseDTO;
import com.wellvia.WellviaHealth.service.LaboratoryService;
import com.wellvia.WellviaHealth.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/laboratories")
public class LaboratoryController {

    @Autowired
    private LaboratoryService laboratoryService;

    @Autowired
    private AuditLogService auditLogService;

    // Rate limiting configuration
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private static final int REQUESTS_PER_MINUTE = 60;

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.simple(REQUESTS_PER_MINUTE, Duration.ofMinutes(1));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private Bucket resolveBucket(String key) {
        return buckets.computeIfAbsent(key, k -> createNewBucket());
    }

    @PostMapping
    public ResponseEntity<Page<LabListingResponseDTO>> listLaboratories(
            @RequestBody LabListingRequestDTO request) {
        
        // Get current user for audit logging
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Rate limiting check
        Bucket bucket = resolveBucket(username);
        if (!bucket.tryConsume(1)) {
            auditLogService.logEvent(
                "LAB_LISTING_RATE_LIMIT_EXCEEDED",
                username,
                "Rate limit exceeded for laboratory listing",
                "FAILED",
                "Too many requests"
            );
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        try {
            Page<LabListingResponseDTO> laboratories = laboratoryService.getLaboratories(request);
            
            // Log successful request
            auditLogService.logEvent(
                "LAB_LISTING_SUCCESS",
                username,
                String.format("Retrieved %d laboratories with filters: city=%s, state=%s, ratingSort=%s, priceSort=%s",
                    laboratories.getTotalElements(),
                    request.getCity(),
                    request.getState(),
                    request.getRatingSort(),
                    request.getPriceSort()),
                "SUCCESS",
                null
            );
            
            return ResponseEntity.ok(laboratories);
        } catch (Exception e) {
            // Log error
            auditLogService.logEvent(
                "LAB_LISTING_ERROR",
                username,
                "Error retrieving laboratories",
                "FAILED",
                e.getMessage()
            );
            throw e;
        }
    }
} 