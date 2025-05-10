package com.wellvia.WellviaHealth.interfaces;

import com.wellvia.WellviaHealth.dto.SpecializationListingRequestDTO;
import com.wellvia.WellviaHealth.model.Specialization;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface SpecializationInterface {
    ResponseEntity<List<Specialization>> getSpecializations(SpecializationListingRequestDTO request);
} 