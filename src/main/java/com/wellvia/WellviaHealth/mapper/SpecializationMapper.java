package com.wellvia.WellviaHealth.mapper;

import com.wellvia.WellviaHealth.model.Specialization;
import com.wellvia.WellviaHealth.dto.SpecializationDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpecializationMapper {
    
    @Value("${app.specialization-resources-url}")
    private String specializationResourcesUrl;
    
    public SpecializationDTO toDTO(Specialization specialization) {
        if (specialization == null) {
            return null;
        }
        
        SpecializationDTO dto = new SpecializationDTO();
        dto.setId(specialization.getId());
        dto.setName(specialization.getName());
        
        // Construct full URL if imagePath exists
        if (specialization.getImagePath() != null) {
            dto.setImagePath(specializationResourcesUrl + "/" + specialization.getImagePath());
        }
        
        return dto;
    }
} 