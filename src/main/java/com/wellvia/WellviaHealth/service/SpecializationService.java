package com.wellvia.WellviaHealth.service;

import com.wellvia.WellviaHealth.dto.SpecializationListingRequestDTO;
import com.wellvia.WellviaHealth.model.Specialization;
import com.wellvia.WellviaHealth.dto.SpecializationDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface SpecializationService {
    List<Specialization> getAllSpecializations();
    List<SpecializationDTO> getSpecializationList();
    ResponseEntity<List<Specialization>> getSpecializations(SpecializationListingRequestDTO request);
} 