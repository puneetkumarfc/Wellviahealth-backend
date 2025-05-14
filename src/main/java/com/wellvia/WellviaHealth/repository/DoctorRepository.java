package com.wellvia.WellviaHealth.repository;

import com.wellvia.WellviaHealth.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUserId(Long userId);

    @Query("SELECT d FROM Doctor d " +
           "LEFT JOIN FETCH d.appointmentSlots a " +
           "WHERE d.doctorId = :doctorId " +
           "AND d.isDeleted = false " +
           "AND (a IS NULL OR a.isAvailable = true)")
    Optional<Doctor> findByIdWithAvailableSlots(@Param("doctorId") Long doctorId);
} 