package com.wellvia.WellviaHealth.interfaces;

import com.wellvia.WellviaHealth.dto.ApiResponse;
import com.wellvia.WellviaHealth.dto.SpecializationDTO;
import com.wellvia.WellviaHealth.dto.SpecializationListingRequestDTO;
import com.wellvia.WellviaHealth.model.Specialization;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SpecializationInterface {
    List<Specialization> getAllSpecializations();
    ResponseEntity<ApiResponse<List<SpecializationDTO>>> getSpecializationList(SpecializationListingRequestDTO request);
    ResponseEntity<List<Specialization>> getSpecializations(SpecializationListingRequestDTO request);
}


