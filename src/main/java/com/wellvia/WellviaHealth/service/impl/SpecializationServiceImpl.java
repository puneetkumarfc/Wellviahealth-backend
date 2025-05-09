package com.wellvia.WellviaHealth.service.impl;

import com.wellvia.WellviaHealth.model.Specialization;
import com.wellvia.WellviaHealth.repository.SpecializationRepository;
import com.wellvia.WellviaHealth.service.SpecializationService;
import com.wellvia.WellviaHealth.mapper.SpecializationMapper;
import com.wellvia.WellviaHealth.dto.SpecializationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private SpecializationMapper specializationMapper;

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
} 