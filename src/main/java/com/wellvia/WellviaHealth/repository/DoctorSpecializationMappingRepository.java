package com.wellvia.WellviaHealth.repository;

import com.wellvia.WellviaHealth.model.DoctorSpecializationMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorSpecializationMappingRepository extends JpaRepository<DoctorSpecializationMapping, Long> {
    List<DoctorSpecializationMapping> findBySpecializationIdAndIsDeletedFalse(Long specializationId);
} 