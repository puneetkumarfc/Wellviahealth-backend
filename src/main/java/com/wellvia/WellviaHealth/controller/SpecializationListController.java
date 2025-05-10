package com.wellvia.WellviaHealth.controller;

import com.wellvia.WellviaHealth.dto.SpecializationDTO;
import com.wellvia.WellviaHealth.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/specialization")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*")
public class SpecializationListController {

    @Autowired
    private SpecializationService specializationService;

    @GetMapping("/list")
    public ResponseEntity<List<SpecializationDTO>> getSpecializationList() {
        return ResponseEntity.ok(specializationService.getSpecializationList());
    }
} 