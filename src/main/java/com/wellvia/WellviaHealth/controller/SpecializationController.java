package com.wellvia.WellviaHealth.controller;

import com.wellvia.WellviaHealth.dto.SpecializationListingRequestDTO;
import com.wellvia.WellviaHealth.service.SpecializationService;
import com.wellvia.WellviaHealth.model.Specialization;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@RestController
@RequestMapping("/api/specializations")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = {"Authorization", "X-Device-Info"})
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

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
    public ResponseEntity<List<Specialization>> listSpecializations(
            @RequestBody SpecializationListingRequestDTO request) {
        
        // Get current user for rate limiting
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Rate limiting check
        Bucket bucket = resolveBucket(username);
        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        return specializationService.getSpecializations(request);
    }
} 