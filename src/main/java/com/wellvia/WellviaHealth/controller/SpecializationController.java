package com.wellvia.WellviaHealth.controller;

import com.wellvia.WellviaHealth.dto.SpecializationListingRequestDTO;
import com.wellvia.WellviaHealth.model.Specialization;
import com.wellvia.WellviaHealth.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/specializations")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = {"Authorization", "X-Device-Info"})
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @PostMapping("/list")
    public ResponseEntity<List<Specialization>> listSpecializations(
            @RequestBody(required = false) SpecializationListingRequestDTO request) {
        return specializationService.getSpecializations(request != null ? request : new SpecializationListingRequestDTO());
    }
} 