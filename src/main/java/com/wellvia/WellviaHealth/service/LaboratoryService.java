package com.wellvia.WellviaHealth.service;

import com.wellvia.WellviaHealth.interfaces.LaboratoryInterface;
import com.wellvia.WellviaHealth.dto.LabListingRequestDTO;
import com.wellvia.WellviaHealth.dto.LabListingResponseDTO;
import com.wellvia.WellviaHealth.repository.LaboratoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class LaboratoryService implements LaboratoryInterface {

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Override
    public Page<LabListingResponseDTO> getLaboratories(LabListingRequestDTO request) {
        // Create pageable object with sorting
        Pageable pageable = PageRequest.of(
            request.getPage(),
            request.getSize(),
            Sort.by(Sort.Direction.DESC, "ratings")
        );

        // Get laboratories based on filters
        return laboratoryRepository.findByCityAndStateAndIsDeletedFalse(
            request.getCity(),
            request.getState(),
            pageable
        ).map(laboratory -> {
            LabListingResponseDTO dto = new LabListingResponseDTO();
            dto.setId(laboratory.getId());
            dto.setLabName(laboratory.getLabName());
            dto.setProfileImage(laboratory.getProfileImage());
            dto.setRatings(laboratory.getRatings());
            dto.setAccreditation(laboratory.getAccreditation());
            dto.setReportTime(laboratory.getReportTime());
            dto.setPrice(laboratory.getPrice());
            return dto;
        });
    }
} 