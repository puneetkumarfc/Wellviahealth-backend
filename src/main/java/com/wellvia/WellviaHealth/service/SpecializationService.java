package com.wellvia.WellviaHealth.service;

import com.wellvia.WellviaHealth.dto.DoctorDTO;
import com.wellvia.WellviaHealth.dto.SpecializationListingRequestDTO;
import com.wellvia.WellviaHealth.mapper.DoctorMapper;
import com.wellvia.WellviaHealth.model.Specialization;
import com.wellvia.WellviaHealth.repository.DoctorRepository;
import com.wellvia.WellviaHealth.repository.DoctorSpecializationMappingRepository;
import com.wellvia.WellviaHealth.repository.SpecializationRepository;
import com.wellvia.WellviaHealth.mapper.SpecializationMapper;
import com.wellvia.WellviaHealth.interfaces.SpecializationInterface;
import com.wellvia.WellviaHealth.dto.SpecializationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import java.util.Collections;
import com.wellvia.WellviaHealth.dto.ApiResponse;

@Service
public class SpecializationService implements SpecializationInterface {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private SpecializationMapper specializationMapper;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DoctorSpecializationMappingRepository doctorSpecializationMappingRepository;

    @Override
    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAllActiveWithSearch(null);
    }

    @Override
    public ResponseEntity<ApiResponse<List<SpecializationDTO>>> getSpecializationList(SpecializationListingRequestDTO request) {
        List<SpecializationDTO> specializations = specializationRepository.findAllActiveWithSearch(
            request != null ? request.getSearch() : null)
            .stream()
            .map(specializationMapper::toDTO)
            .collect(Collectors.toList());
        
        if (specializations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, 
                    Collections.singletonList("No specializations found"), 
                    "No specializations found", 
                    null));
        }
        
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Specializations retrieved successfully", specializations));
    }

    @Override
    public ResponseEntity<List<Specialization>> getSpecializations(SpecializationListingRequestDTO request) {
        // Get current user for audit logging
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        try {
            // Get specializations with search
            List<Specialization> specializations = specializationRepository.findAllActiveWithSearch(
                request.getSearch());

            // Log successful request
            auditLogService.logEvent(
                "SPECIALIZATION_LISTING_SUCCESS",
                username,
                String.format("Retrieved %d specializations with search: %s",
                    specializations.size(),
                    request.getSearch()),
                "SUCCESS",
                null
            );

            return ResponseEntity.ok(specializations);
        } catch (Exception e) {
            // Log error
            auditLogService.logEvent(
                "SPECIALIZATION_LISTING_ERROR",
                username,
                "Error retrieving specializations",
                "FAILED",
                e.getMessage()
            );
            throw e;
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<DoctorDTO>>> getDoctorsBySpecialization(Long id) {
        List<DoctorDTO> doctors;
        
        if (id == null || id == 0) {
            // If no ID provided or ID is 0, return all doctors
            doctors = doctorRepository.findAll().stream()
                    .filter(doctor -> !doctor.getIsDeleted())
                    .map(doctorMapper::toDTO)
                    .collect(Collectors.toList());
        } else {
            // If ID provided, return doctors for that specialization
            doctors = specializationRepository.findById(id)
                    .map(specialization -> doctorSpecializationMappingRepository
                            .findBySpecializationIdAndIsDeletedFalse(id)
                            .stream()
                            .map(mapping -> doctorMapper.toDTO(mapping.getDoctor()))
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
        }

        if (doctors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false,
                            Collections.singletonList("No doctors found"),
                            "No doctors found",
                            null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, null, "Doctors retrieved successfully", doctors));
    }
} 