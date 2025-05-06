package com.wellvia.WellviaHealth.interfaces;

import com.wellvia.WellviaHealth.dto.LabListingRequestDTO;
import com.wellvia.WellviaHealth.dto.LabListingResponseDTO;
import org.springframework.data.domain.Page;

public interface LaboratoryInterface {
    /**
     * Get a paginated list of laboratories based on the provided filters
     * @param request The request containing filters and pagination parameters
     * @return A page of laboratory listings
     */
    Page<LabListingResponseDTO> getLaboratories(LabListingRequestDTO request);
} 