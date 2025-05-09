package com.wellvia.WellviaHealth.service;

import com.wellvia.WellviaHealth.model.Specialization;
import com.wellvia.WellviaHealth.dto.SpecializationDTO;
import java.util.List;

public interface SpecializationService {
    List<Specialization> getAllSpecializations();
    List<SpecializationDTO> getSpecializationList();
} 