package com.wellvia.WellviaHealth.service;

import com.wellvia.WellviaHealth.dto.SpecializationListingRequestDTO;
import com.wellvia.WellviaHealth.model.Specialization;
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

@Service
public class SpecializationService implements SpecializationInterface {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private SpecializationMapper specializationMapper;

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAllActiveWithSearch(null);
    }

    @Override
    public List<SpecializationDTO> getSpecializationList() {
        return specializationRepository.findAllActiveWithSearch(null)
            .stream()
            .map(specializationMapper::toDTO)
            .collect(Collectors.toList());
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
} 