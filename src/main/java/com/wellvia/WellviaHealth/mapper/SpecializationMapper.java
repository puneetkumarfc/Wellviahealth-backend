package com.wellvia.WellviaHealth.mapper;

import com.wellvia.WellviaHealth.model.Specialization;
import com.wellvia.WellviaHealth.dto.SpecializationDTO;
import org.springframework.stereotype.Component;

@Component
public class SpecializationMapper {
    
    public SpecializationDTO toDTO(Specialization specialization) {
        if (specialization == null) {
            return null;
        }
        
        SpecializationDTO dto = new SpecializationDTO();
        dto.setId(specialization.getId());
        dto.setName(specialization.getName());
        dto.setImagePath(specialization.getImagePath());
        return dto;
    }
} 