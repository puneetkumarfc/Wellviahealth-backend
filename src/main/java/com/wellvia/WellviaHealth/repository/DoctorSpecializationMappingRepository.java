package com.wellvia.WellviaHealth.repository;

import com.wellvia.WellviaHealth.model.DoctorSpecializationMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorSpecializationMappingRepository extends JpaRepository<DoctorSpecializationMapping, Long> {
    List<DoctorSpecializationMapping> findBySpecializationIdAndIsDeletedFalse(Long specializationId);

    @Query("SELECT dsm FROM DoctorSpecializationMapping dsm " +
           "LEFT JOIN FETCH dsm.doctor d " +
           "LEFT JOIN FETCH d.appointmentSlots " +
           "WHERE dsm.specialization.id = :specializationId " +
           "AND dsm.isDeleted = false " +
           "AND d.isDeleted = false")
    List<DoctorSpecializationMapping> findBySpecializationIdWithDoctorAndSlots(@Param("specializationId") Long specializationId);
} 